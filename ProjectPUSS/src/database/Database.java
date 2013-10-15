package database;

import items.TimeReport;
import items.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.management.relation.Role;

/**
 * Denna klassen innehåller länken till databasen. Klassen innehåller den
 * funktionalitet som systemet behöver för att hämta ut och lägga in information
 * i databasen. Klassen finns tillgänglig i ServletBase och därmed även i de
 * servlets som behöver den.
 */
public class Database {

	private static Database instance;

	private Connection conn = null;

	private Database() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://vm26.cs.lth.se/puss1301?"
				+ "user=puss1301&password=8jh398fs");
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
	}

	/**
	 * Hämtar singletoninstansen av Database
	 */
	public static Database getInstance() throws SQLException {
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
		return null;
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
		return null;
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
		return false;
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
		return null;
	}

	/**
	 * 
	 * Försöker hämta en lista av alla projekt i systemet från databasen
	 * 
	 */
	public List<String> getProjects() {
		return null;
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
	 * @param userName
	 *            användaren som ska läggas till. Lägger till en användare till
	 *            ett projekt.
	 * @return true om den lyckas annars false.
	 * 
	 */
	public boolean addUserToProject(String projectName, String userName) {
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
		return false;
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
	 *        	  användare i systemet.
	 * @param password
	 * 				användarlösenordet som ska läggas till i systemet.
	 * @return true om den lyckas annars false.
	 * 
	 */
	public boolean addUser(String username, String password) {
		
	    try {
	    	Statement stmt = conn.createStatement();
	    	String statement = "INSERT INTO Users (username, password) VALUES('" + username + "', '" + password + "')";
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
		return false;
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
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE username='" + username + "'");
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
		return false;
	}
	
	
	/**
	* Start a transaction to the database
	 * @throws SQLException 
	*/
	public void startTransaction() throws SQLException {
		conn.setAutoCommit(false);
	}

	/**
	* Commit the current transactions to the database
	 * @throws SQLException 
	*/
	public void commit() throws SQLException {
		conn.commit();
		conn.setAutoCommit(true);
	}

	/**
	* Rollback current transactions from the database
	 * @throws SQLException 
	*/
	public void rollback() throws SQLException {
		conn.rollback();
		conn.setAutoCommit(true);
	}

}
