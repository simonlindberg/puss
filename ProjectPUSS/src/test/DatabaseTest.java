/**
 * 
 */
package test;

import static org.junit.Assert.*;
import items.Activity;
import items.ActivityType;
import items.Role;
import items.TimeReport;
import items.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import database.Database;

/**
 * @author ericottosson
 * 
 */
public class DatabaseTest {

	private Database db;
	private Connection conn = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		conn = DriverManager.getConnection("jdbc:mysql://vm26.cs.lth.se/puss1301?"
				+ "user=puss1301&password=8jh398fs");
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		conn.setAutoCommit(false);

		db = Database.getInstance();
		db.startTransaction();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		db.rollback();
		conn.rollback();
		conn.close();
	}

	@Test
	public void testGetUser() throws SQLException {
		String name = "Christian";
		String psw = "psw";
		String result = "";

		Statement stmt = conn.createStatement();
		String statement = "INSERT INTO Users (username, password) VALUES('" + name + "', '" + psw
				+ "')";
		stmt.executeUpdate(statement);
		stmt.close();

		User user = db.getUser(name);
		result = user.getUsername();
		assertEquals(name, result);
	}

	@Test
	public void testAddUser() throws SQLException {
		String name = "Christian";
		String psw = "psw";
		String result = "";

		db.addUser(name, psw);

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE username='" + name + "'");
		while (rs.next()) {
			result = rs.getString("username");
		}
		stmt.close();

		assertEquals(name, result);
	}

	@Test
	public void testLogin() throws Exception {
		String username = "testing";
		String password = "test123";

		db.addUser(username, password);

		Assert.assertTrue(db.login(username, password));
		Assert.assertFalse(db.login("", ""));
	}

	@Test
	public void testLoginAdmin() throws Exception {
		Assert.assertTrue(db.login("admin", "adminpw"));
		Assert.assertFalse(db.login("admin", "adminpwpwpw"));
		Assert.assertFalse(db.login("admin", ""));
	}

	@Test
	public void testDeleteUser() throws SQLException {
		String name = "Christian";
		String psw = "123";
		String result = "";

		db.addUser(name, psw);
		db.deleteUser(name);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE username='" + name + "'");
		assertTrue(!rs.next());
	}
	
	@Test
	public void testDeleteUserWithTimeReport() throws SQLException {
		String name = "Christian";
		String psw = "123";
		String result = "";

		List<Activity> activity = new ArrayList<Activity>();
		activity.add(new Activity(ActivityType.SRS, 60));

		TimeReport report = new TimeReport(new User(name, psw), activity, false, 0, 1,
				"testgroup");

		db.createProjectGroup("testgroup");
		db.addUser(name, psw);
		db.createTimeReport(report);
		db.deleteUser(name);

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE username='" + name + "'");
		while (rs.next()) {
			result = rs.getString("username");
		}
		stmt.close();

		assertEquals("", result);
		
		// Test if all TimeReports for the user were deleted.
		stmt = conn.createStatement();
		rs = stmt.executeQuery("SELECT * FROM TimeReports WHERE Username='" + name + "'");
		assertTrue(!rs.next());
		rs.close();
		stmt.close();
	}

	@Test
	public void testDeleteUserDoesntExist() throws SQLException {
		String name = "_Christian"; // Underscore to ensure user is outside SRS
									// scope
		boolean success = db.deleteUser(name);
		Assert.assertFalse(success);
	}

	@Test
	public void testGetUsers() throws SQLException {
		List<String> expected = Arrays.asList("Christian", "Oskar");
		List<String> actual = new ArrayList<String>();

		for (String s : expected)
			db.addUser(s, "123");

		List<User> result = db.getUsers();

		for (User u : result)
			actual.add(u.getUsername());

		for (String s : expected)
			assertTrue(actual.contains(s));

	}

	@Test
	public void testGetProjects() throws Exception {
		List<String> expectedProjects = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
		List<String> users = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i");

		for (int i = 0; i < users.size(); i++) {
			db.addUser(users.get(i), "");
			db.createProjectGroup(expectedProjects.get(i));
			db.addUserToProject(expectedProjects.get(i), users.get(i));
		}

		List<String> pr = db.getProjects();
		for (int i = 0; i < expectedProjects.size(); i++) {
			Assert.assertTrue(pr.contains(expectedProjects.get(i)));
		}
	}

	@Test
	public void testGetProjectsForUser() throws Exception {
		List<String> expectedProjects = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
		String username = "test1234";

	}

	@Test
	public void testCreateTimereport() throws SQLException {

		List<Activity> activity = new ArrayList<Activity>();
		activity.add(new Activity(ActivityType.SRS, 60));

		TimeReport report = new TimeReport(new User("Oskar", ""), activity, false, 0, 1,
				"testgroup");

		db.createProjectGroup("testgroup");
		db.addUser("Oskar", "");
		db.createTimeReport(report);

		// Test values in Table:TimeReports
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt
				.executeQuery("SELECT * FROM TimeReports WHERE Username='Oskar' AND WeekNumber=1");
		assertTrue(rs.next());
		assertEquals(rs.getString("GroupName"), "testgroup");
		assertEquals(rs.getInt("Signed"), 0);

		rs.close();
		stmt.close();

		// Test values in Table:Activity
		stmt = conn.createStatement();
		rs = stmt
				.executeQuery("SELECT Id FROM TimeReports WHERE Username='Oskar' AND WeekNumber=1");
		rs.next();
		int id = rs.getInt("Id");
		rs.close();
		stmt.close();

		stmt = conn.createStatement();
		rs = stmt.executeQuery("SELECT * FROM Activity WHERE Id=" + id);
		assertTrue(rs.next());
		assertEquals(rs.getString("ActivityName"), ActivityType.SRS.toString());
		assertEquals(rs.getInt("MinutesWorked"), 60);
		rs.close();
		stmt.close();
	}

	@Test
	public void testCreateProjectGroup() {
		String projectName = "_projectName";
		Assert.assertTrue(db.createProjectGroup(projectName));
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT Groupname FROM ProjectGroups WHERE Groupname='"
							+ projectName + "'");
			rs.next();
			assertEquals(projectName, rs.getString("Groupname"));
		} catch (SQLException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testDeleteTimeReport() throws SQLException {
		List<Activity> activity = new ArrayList<Activity>();
		activity.add(new Activity(ActivityType.SRS, 60));
		TimeReport report = new TimeReport(new User("Oskar", ""), activity, false, 0, 1,
				"testgroup");

		db.createProjectGroup("testgroup");
		db.addUser("Oskar", "");
		db.createTimeReport(report);

		// Collect id of TimeReport
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT Id FROM TimeReports WHERE Username='Oskar' AND WeekNumber=1");
		assertTrue(rs.next());
		int id = rs.getInt("Id");
		rs.close();
		stmt.close();

		db.deleteTimeReport(id);

		// Retrieve deleted TimeReport
		stmt = conn.createStatement();
		rs = stmt.executeQuery("SELECT * FROM TimeReports WHERE Id=" + id);
		assertTrue(!rs.next());
		rs.close();
		stmt.close();
		
		// Retrieve deleted Activity
		stmt = conn.createStatement();
		rs = stmt.executeQuery("SELECT * FROM Activity WHERE Id=" + id);
		assertTrue(!rs.next());
		rs.close();
		stmt.close();
	}
	
	@Test
	public void testGetUsersInProject() throws Exception {
		List<String> expectedUsername = Arrays.asList("a", "b", "c", "d", "e", "f");
		List<String> unexpectedUsername = Arrays.asList("wrong", "error");
		String project = "prjct";
		String wrongProject = "NOTprjct";

		db.createProjectGroup(project);
		for(String a:expectedUsername){
			db.addUser(a, "");
			db.addUserToProject(project, a);
		}
		
		db.createProjectGroup(wrongProject);
		for (String a : unexpectedUsername) {
			db.addUser(a, "");
			db.addUserToProject(wrongProject, a);
		}
		
		List<User> users = db.getUsersInProject(project);
		
		for (User u : users) {
			Assert.assertTrue(expectedUsername.contains(u.getUsername()));
		}
		
		Assert.assertEquals(users.size(), expectedUsername.size());
	}
		
	@Test
	public void testDeleteUserFromProjectGroup() throws SQLException {
		String username = "Oskar";
		String groupname = "testgroup";
		db.createProjectGroup(groupname);
		db.addUser(username, "");
		db.addUserToProject(groupname, username);
		db.deleteUserFromProject(groupname, username);
		
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Memberships WHERE Username='" + username + "'");
		assertTrue(!rs.next());
		rs.close();
	}
	
	@Test
	public void testSignUsign() throws Exception{
		String username = "asdqwdqwd";
		List<Activity> activity = new ArrayList<Activity>();
		activity.add(new Activity(ActivityType.SRS, 60));
		String projectgroup = "testgroup";
		TimeReport report = new TimeReport(new User(username, ""), activity, false, 0, 1,
				projectgroup);

		db.createProjectGroup(projectgroup);
		db.addUser(username, "");
		db.createTimeReport(report);
		
		TimeReport timereport = db.getTimeReports(username, projectgroup).get(0);
		Assert.assertTrue(db.signTimeReport(timereport));
		
		timereport = db.getTimeReports(username, projectgroup).get(0);
		Assert.assertTrue(timereport.getSigned());
		
		
		Assert.assertTrue(db.unsignTimeReport(timereport));

		timereport = db.getTimeReports(username, projectgroup).get(0);
		
		Assert.assertFalse(timereport.getSigned());
	}
	
	@Test
	public void testGetTimeReport() throws SQLException {
		List<Activity> activity = new ArrayList<Activity>();
		activity.add(new Activity(ActivityType.SRS, 60));

		TimeReport report = new TimeReport(new User("Oskar", ""), activity, false, 0, 1,
				"testgroup");

		db.createProjectGroup("testgroup");
		db.addUser("Oskar", "");
		db.createTimeReport(report);
		int id = 0;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT Id FROM TimeReports limit 1");
		rs.next();
		id = rs.getInt("Id");
		rs.close();
		stmt.close();

		TimeReport tr = db.getTimeReport(id);
		assertEquals(tr.getID(), id);
		assertEquals(tr.getProjectGroup(), report.getProjectGroup());
		assertEquals(tr.getActivities().get(0).getType(), report.getActivities().get(0).getType());
		assertEquals(tr.getActivities().get(0).getLength(), report.getActivities().get(0)
				.getLength());
		assertEquals(tr.getSigned(), report.getSigned());
		assertEquals(tr.getUser().getUsername(), report.getUser().getUsername());
		assertEquals(tr.getWeek(), report.getWeek());
	}

	@Test
	public void testGetTimeReports() throws SQLException {
		List<Activity> activity = new ArrayList<Activity>();
		activity.add(new Activity(ActivityType.SRS, 60));
		db.createProjectGroup("testgroup");
		User u = new User("Oskar", "");
		TimeReport report1 = new TimeReport(u, activity, false, 0, 1, "testgroup");

		TimeReport report2 = new TimeReport(u, activity, false, 0, 2, "testgroup");

		db.addUser("Oskar", "");
		db.createTimeReport(report1);
		db.createTimeReport(report2);
		ArrayList<Integer> id = new ArrayList<Integer>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT Id FROM TimeReports ORDER BY Id DESC LIMIT 2");
		while (rs.next()) {
			id.add(rs.getInt("Id"));
		}
		rs.close();
		stmt.close();

		List<TimeReport> timereports = db.getTimeReports(u.getUsername(), "testgroup");
		assertEquals(timereports.get(0).getID(), (int) id.get(1));
		assertEquals(timereports.get(1).getID(), (int) id.get(0));
	}
	
	@Test
	public void testDeleteProjectGroup() throws SQLException {
		String groupname = "_testgroup";
		db.createProjectGroup(groupname);
		db.deleteProjectGroup(groupname);
		
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Memberships WHERE Groupname='" + groupname + "'");
		assertFalse(rs.next());
		rs.close();
		
		rs = conn.createStatement().executeQuery("SELECT * FROM ProjectGroups WHERE Groupname='" + groupname + "'");
		assertFalse(rs.next());
		rs.close();
	}
	
	@Test
	public void testMakeUserPRojectManager() throws SQLException {
		String username = "user1";
		String projectname = "group1";
		db.addUser(username, "");
		db.createProjectGroup(projectname);
		db.addUserToProject(projectname, username);
		db.setUserRole(username, projectname, Role.Manager);
		
		ResultSet rs = conn.createStatement().executeQuery("SELECT Role FROM Memberships WHERE Username='" + username + "'");
		assertTrue(rs.next());
		assertEquals(rs.getString("Role"), Role.Manager.toString());
		rs.close();
	}
	
	@Test
	public void testDemoteUserProjectManager() throws SQLException {
		String username = "user1";
		String projectname = "group1";
		db.addUser(username, "");
		db.createProjectGroup(projectname);
		db.addUserToProject(projectname, username);
		db.setUserRole(username, projectname, Role.Manager);
		db.setUserRole(username, projectname, null);
		
		ResultSet rs = conn.createStatement().executeQuery("SELECT Role FROM Memberships WHERE Username='" + username + "'");
		assertTrue(rs.next());
		assertEquals(rs.getString("Role"), "null");
		rs.close();
	}
	
	@Test
	public void testGetProjectManagers() throws SQLException {
		String manager = "_user1";
		String user = "_user2";
		String projectname = "_group1";
		db.addUser(manager, "");
		db.addUser(user, "");
		db.createProjectGroup(projectname);
		db.addUserToProject(projectname, manager);
		db.addUserToProject(projectname, user);
		db.setUserRole(manager, projectname, Role.Manager);
		
		List<User> managers = db.getProjectManagersInProject(projectname);
		assertEquals(1, managers.size());
		assertEquals(manager, managers.get(0).getUsername());
		
		
	}
}
