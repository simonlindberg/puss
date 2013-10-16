package database;

import items.Activity;
import items.ActivityType;
import items.TimeReport;
import items.User;
import items.Role;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Denna klassen innehåller länken till databasen. Klassen innehåller den
 * funktionalitet som systemet behöver för att hämta ut och lägga in information
 * i databasen. Klassen finns tillgänglig i ServletBase och därmed även i de
 * servlets som behöver den.
 */
public class Database {

	public static final String ADMIN = "admin";
	public static final String ADMIN_PW = "adminpw";

	private static Database instance;

	private Connection conn = null;

	private Database() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://vm26.cs.lth.se/puss1301?"
				+ "user=puss1301&password=8jh398fs");
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
	}

	/**
	 * Hämtar singletoninstansen av Database
	 * 
	 * @throws ClassNotFoundException
	 */
	public static Database getInstance() throws SQLException, ClassNotFoundException {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}

	/**
	 * 
	 * Hämtar tidrapporten med det specifika id från databasen.
	 * 
	 * @return null om den inte hittar någon.
	 * 
	 */
	public TimeReport getTimeReport(int id) {
		Statement stmt;
		TimeReport tr = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM TimeReports WHERE Id='" + id + "'");
//			ResultSet rs = stmt.executeQuery("SELECT * FROM TimeReports");
//			System.out.println("rs: " +rs.next());
//			System.out.println(rs.getInt("Id"));
		    while (rs.next()) {
			    User u = getUser(rs.getString("Username"));
			    boolean signed = rs.getBoolean("Signed");
			    String projectGroup = rs.getString("GroupName");
			    List<Activity> activities = new ArrayList<Activity>();
			    Statement stmt2 = conn.createStatement();
			    ResultSet rs2 = stmt2.executeQuery("SELECT * FROM Activity WHERE Id='" + id + "'");
			    while (rs2.next()) {
			    	ActivityType tp = ActivityType.valueOf(rs2.getString("ActivityName"));
			    	int worked = rs2.getInt("MinutesWorked");
			    	activities.add(new Activity(tp, worked));
			    }
			    
			    tr = new TimeReport(u, activities, signed, rs.getInt("Id"), rs.getInt("WeekNumber") , projectGroup);
			    rs2.close();
			    stmt2.close();
		    }
		    rs.close();
		    stmt.close();
		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		return tr;
	}

	/**
	 * 
	 * @param userID
	 *            anv��ndarens id som tidrapporterna ��r kopplade till.
	 * @param projectGroup
	 *            projektgruppen som tidrapporterna är kopplade till.
	 * @return en lista med tidrepporter eller null om något går fel.
	 */
	public List<TimeReport> getTimeReports(String userID, String projectGroup) {
		List<TimeReport> reports = new ArrayList<TimeReport>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Timereports WHERE " + "Username='"
					+ userID + "' AND GroupName='" + projectGroup + "'");
			while (rs.next()) {
				int id = rs.getInt("id");
				reports.add(getTimeReport(id));
			}
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return reports;
	}

	/**
	 * Försöker skapa en ny tidrapport.
	 * 
	 * @param timereport
	 *            Tidrapporten som ska skapas. Skapar en tidrapport.
	 * @return true om det lyckas, annars false.
	 * 
	 */
	public boolean createTimeReport(TimeReport timereport) {
		// Check if the time report already exists
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM TimeReports WHERE WeekNumber="
					+ timereport.getWeek() + " AND Username='" + timereport.getUser().getUsername()
					+ "' AND Groupname='" + timereport.getProjectGroup() + "'");
			if (rs.next()) {
				return false;
			}
				
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		// Extract and save into table:TimeReports
		try {
			Statement stmt = conn.createStatement();
			String statement = "INSERT INTO TimeReports (Username, Groupname, WeekNumber, Date, Signed) VALUES('"

					+ timereport.getUser().getUsername()
					+ "','"
					+ timereport.getProjectGroup()
					+ "',"
					+ timereport.getWeek()
					+ ", NOW(),"
					+ (timereport.getSigned() ? 1 : 0)
					+ ")";
			stmt.executeUpdate(statement);
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}

		// Extract and save into table:Activity
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Id FROM TimeReports WHERE WeekNumber="
					+ timereport.getWeek() + " AND Username='" + timereport.getUser().getUsername()
					+ "'");
			rs.next();
			int id = rs.getInt("Id");
			stmt.close();
			for (Activity a : timereport.getActivities()) {
				stmt = conn.createStatement();
				String statement = "INSERT INTO Activity (Id, ActivityName, ActivityNumber, MinutesWorked, Type) VALUES("
						+ id
						+ ",'"
						+ a.getType().toString()
						+ "', 0,"
						+ a.getLength()
						+ ",'...')";
				stmt.executeUpdate(statement);
				stmt.close();
			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}

		return true;
	}

	/**
	 * 
	 * Försöker ta bort en tidrapport.
	 * 
	 * @param id
	 *            id för tidrapport att ta bort. Tar bort en tidrapport.
	 * @return true om det lyckas, annars false.
	 * 
	 */
	public boolean deleteTimeReport(int id) {
		return false;
	}

	/**
	 * Försöker att uppdatera en tidrapport.
	 * 
	 * @param timereport
	 *            tidrapporten att uppdatera. Uppdaterar en tidrapport.
	 * 
	 * @return true om det lyckas, annars false.
	 */
	public boolean updateTimeReport(TimeReport timereport) {
		return false;
	}

	/**
	 * 
	 * Försöker hämta en lista av medlemmar i en projektgrupp från databasen.
	 * 
	 * @param projectGroup
	 *            projektgruppen vars medlemmar ska hämtas.
	 * 
	 */
	public List<User> getUsers(String projectGroup) {
		return null;
	}

	/**
	 * Sätter en användares roll för ett visst projekt.
	 * 
	 * @param userID
	 *            användaren vars roll ska manipuleras.
	 * @param project
	 *            projektnamnet att ändra i.
	 * @param role
	 *            rollen som ska sättas på användaren.
	 * 
	 */
	public void setUserRole(String userID, String project, Role role) {
	}

	/**
	 * 
	 * Signerar en tidsrapport
	 * 
	 * @param timereport
	 *            tidrapporten att signera. Signerar en tidrapport.
	 * 
	 */
	public void signTimeReport(TimeReport timereport) {
	}

	/**
	 * Avsignerar en tidrapport.
	 * 
	 * @param timereport
	 *            tidrapporten att avsignera. Avsignerar en tidrapport.
	 * 
	 */
	public void unsignTimeReport(TimeReport timereport) {
	}

	/**
	 * 
	 * Försöker hämta en lista av alla användare i systemet från databasen
	 * 
	 */
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();

		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users");
			while (rs.next()) {
				String name = rs.getString("username");
				String password = rs.getString("password");
				users.add(new User(name, password));
			}
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return users;
	}

	/**
	 * 
	 * Försöker hämta en lista av alla projekt i systemet från databasen
	 * 
	 */
	public List<String> getProjects() {
		List<String> list = new ArrayList<>();
		ResultSet rs;
		try {
			rs = conn.createStatement().executeQuery("select * from ProjectGroups;");
			while (rs.next()) {
				list.add(rs.getString("Groupname"));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * Försöker hämta alla användare från ett projekt.
	 * 
	 * @param projectName
	 *            projektet att hämta ifrån. Hämtar en lista av alla användare i
	 *            ett projekt
	 * 
	 * @return Returnerar null om projektet inte finns.
	 * 
	 * 
	 */
	public List<User> getUsersInProject(String projectName) {
		return null;
	}

	/**
	 * 
	 * Försöker hämta alla projektledare från ett projekt.
	 * 
	 * @param projectName
	 *            projektet att hämta ifrån. Hämtar en lista av alla användare i
	 *            ett projekt som är projektledare.
	 * 
	 * @return Returnerar null om projektet inte finns.
	 * 
	 * 
	 */
	public List<User> getProjectManagersInProject(String projectName) {
		return null;
	}

	/**
	 * Försöker lägga till en användare till ett projekt.
	 * 
	 * @param projectName
	 *            projektet att lägga till användaren till.
	 * @param username
	 *            användaren som ska läggas till. Lägger till en användare till
	 *            ett projekt.
	 * @return true om den lyckas annars false.
	 * 
	 */
	public boolean addUserToProject(String projectName, String username) {
		try {
			return conn.createStatement().execute(
					"insert into Memberships (Username, Groupname) values ('" + username + "', '"
							+ projectName + "')");
		} catch (SQLException e) {
			System.out.println("");
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * Försöker ta bort en användare från ett projekt.
	 * 
	 * @param projectName
	 *            projektet att ta bort användaren från.
	 * @param userName
	 *            användaren som ska tas bort. Tar bort en användare från ett
	 *            projekt.
	 * @return true om den lyckas annars false.
	 * 
	 */
	public boolean deleteUserFromProject(String projectName, String userName) {
		return false;
	}

	/**
	 * Försöker göra en användare till projektledare.
	 * 
	 * @param projectName
	 *            projektet att manipulera.
	 * @param userName
	 *            användaren som ska bli projektledare. Gör en användare i ett
	 *            projekt till projektledare.
	 * @return true om den lyckas annars false.
	 * 
	 */
	public boolean makeUserProjectManager(String projectName, String userName) {
		return false;
	}

	/**
	 * Försöker ta bort projektledar status från en användare i ett projekt.
	 * 
	 * @param projectName
	 *            projektet som ska manipuleras.
	 * @param userName
	 *            användaren som ska bli en vanlig användare från projektledare.
	 *            Gör en projektledare i ett projekt till vanlig användare.
	 * @return true om den lyckas annars false.
	 * 
	 */
	public boolean demoteProjectManager(String projectName, String userName) {
		return false;
	}

	/**
	 * Försöker skapa en projektgrupp.
	 * 
	 * @param projectName
	 *            namn på projektgruppen som ska skapas. Skapar en projektgrupp.
	 * 
	 @return true om den lyckas annars false.
	 */
	public boolean createProjectGroup(String projectName) {
		try {
			Statement stmt = conn.createStatement();
			String statement = "INSERT INTO ProjectGroups (Groupname) VALUES('" + projectName
					+ "')";
			stmt.executeUpdate(statement);
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}
		return true;
	}

	/**
	 * Försöker ta bort en projektgrupp.
	 * 
	 * @param projectName
	 *            name på projektgruppen som ska tas bort. Tar bort en
	 *            projektgrupp.
	 * @return true om den lyckas annars false.
	 * 
	 */
	public boolean deleteProjectGroup(String projectName) {
		return false;
	}

	/**
	 * Försöker lägga till en användare.
	 * 
	 * @param username
	 *            användarnamnet som ska läggas till i systemet. Lägger till en
	 *            användare i systemet.
	 * @param password
	 *            användarlösenordet som ska läggas till i systemet.
	 * @return true om den lyckas annars false.
	 * 
	 */
	public boolean addUser(String username, String password) {

		try {
			Statement stmt = conn.createStatement();
			String statement = "INSERT INTO Users (username, password) VALUES('" + username
					+ "', '" + password + "')";
			stmt.executeUpdate(statement);
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}

		return true;
	}

	/**
	 * Försöker ta bort en användare.
	 * 
	 * @param username
	 *            användarnamnet som ska tas bort ur systemet. Tar bort en
	 *            användare ur systemet.
	 * @return true om den lyckas annars false.
	 * 
	 */
	public boolean deleteUser(String username) {
		int result = 0;
		try {
			Statement stmt = conn.createStatement();
			String statement = "DELETE FROM Users WHERE username='" + username + "'";
			result = stmt.executeUpdate(statement);
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}
		return result == 1;
	}

	/**
	 * Hämtar en användare
	 * 
	 * @param username
	 *            anvädarnamnet som ska hämtas från databasen. Hämtar en
	 *            användare från databasen.
	 * @return ett User-objekt om användare finns annars null?
	 * 
	 */
	public User getUser(String username) {
		User user = null;

		Statement stmt;
		try {
			if (username.equals(ADMIN)) {
				return new User(ADMIN, ADMIN_PW);
			}

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE username='" + username
					+ "'");
			while (rs.next()) {
				String name = rs.getString("username");
				String password = rs.getString("password");
				user = new User(name, password);
			}
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return user;
	}

	/**
	 * Försöker logga in i systemet.
	 * 
	 * @param username
	 *            användarnamnet som man försöker logga in som.
	 * @param password
	 *            lösenordet för användaren. Försöker logga in med användarnamn
	 *            och lösenord.
	 * 
	 * @return true om inloggningen lyckades annars false.
	 * 
	 */
	public boolean login(String username, String password) {
		if (ADMIN.equals(username) && password != null) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT Password FROM Administrator");
				rs.next();
				String adminPass = rs.getString("Password");
				return password.equals(adminPass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		User user = getUser(username);
		return user != null && user.getPassword().equals(password);
	}

	/**
	 * Start a transaction to the database
	 * 
	 * @throws SQLException
	 */
	public void startTransaction() throws SQLException {
		conn.setAutoCommit(false);
	}

	/**
	 * Commit the current transactions to the database
	 * 
	 * @throws SQLException
	 */
	public void commit() throws SQLException {
		conn.commit();
		conn.setAutoCommit(true);
	}

	/**
	 * Rollback current transactions from the database
	 * 
	 * @throws SQLException
	 */
	public void rollback() throws SQLException {
		conn.rollback();
		conn.setAutoCommit(true);
	}

	public Role getRole(User user) {
		Role r = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			// kod
		} catch (SQLException e) {
			System.out.println("fel i getRole() i Database.java");
			e.printStackTrace();
		}
		return r;
	}

	/**
	 * Returns the role of the given username in the given project.
	 * 
	 * @param username
	 * @param projectgroup
	 * @return
	 */
	public Role getRole(String username, String projectgroup) {
		String role = "";
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Memberships WHERE username='"
					+ username + "' AND groupname='" + projectgroup + "'");
			while (rs.next()) {
				role = rs.getString("role");
			}
		} catch (SQLException e) {
			System.out.println("fel i getRole() i Database.java");
			e.printStackTrace();
		}
		try {
			return Role.valueOf(role);
		} catch (IllegalArgumentException e) {
			return null;
		}

	}

	/**
	 * Hämtar alla projekt som den specifiserade användaren är medlem i.
	 * 
	 * @param user
	 *            en snäll söt liten användare
	 * @return en lista med projektnamn
	 */
	public List<String> getProjects(User user) throws NullPointerException {
		List<String> list = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select Groupname from Memberships where Username = '"
					+ user.getUsername() + "';");
			while(rs.next()){
				list.add(rs.getString("Groupname"));
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
