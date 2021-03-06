/**
 * 
 */
package test;

import static org.junit.Assert.*;
import items.Activity;
import items.ActivitySubType;
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
		String name = "_Christian";
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
		String name = "_Christian";
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
		String username = "_testing";
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
		String name = "_Christian";
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
		String name = "_Christian";
		String psw = "123";
		String result = "";

		List<Activity> activity = new ArrayList<Activity>();
		activity.add(new Activity(ActivityType.SRS, 60, ActivitySubType.I));

		TimeReport report = new TimeReport(new User(name, psw), activity, false, 0, 1,
				"_testgroup");

		db.createProjectGroup("_testgroup");
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
		List<String> expected = Arrays.asList("_Christian", "_Oskar");
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
		String username = "_test1234";

	}

	@Test
	public void testCreateTimereport() throws SQLException {

		List<Activity> activity = new ArrayList<Activity>();
		activity.add(new Activity(ActivityType.SRS, 60, ActivitySubType.I));
		activity.add(new Activity(ActivityType.SRS, 60, ActivitySubType.F));

		TimeReport report = new TimeReport(new User("_Oskar", ""), activity, false, 0, 1,
				"_testgroup");

		db.createProjectGroup("_testgroup");
		db.addUser("_Oskar", "");
		db.createTimeReport(report);

		// Test values in Table:TimeReports
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt
				.executeQuery("SELECT * FROM TimeReports WHERE Username='_Oskar' AND WeekNumber=1");
		assertTrue(rs.next());
		assertEquals(rs.getString("GroupName"), "_testgroup");
		assertEquals(rs.getInt("Signed"), 0);

		rs.close();
		stmt.close();

		// Test values in Table:Activity
		stmt = conn.createStatement();
		rs = stmt
				.executeQuery("SELECT Id FROM TimeReports WHERE Username='_Oskar' AND WeekNumber=1");
		rs.next();
		int id = rs.getInt("Id");
		rs.close();
		stmt.close();

		stmt = conn.createStatement();
		rs = stmt.executeQuery("SELECT * FROM Activity WHERE Id='" + id+ "' ORDER BY Id DESC, ActivityName DESC, Type DESC ");
		int i=0;
		while(rs.next()){
			assertEquals(rs.getString("ActivityName"), activity.get(i).getType().toString());
			assertEquals(rs.getString("Type"), activity.get(i).getSubType().toString());
			assertEquals(rs.getInt("MinutesWorked"), 60);
			i++;
		}
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
		activity.add(new Activity(ActivityType.SRS, 60, ActivitySubType.I));
		TimeReport report = new TimeReport(new User("_Oskar", ""), activity, false, 0, 1,
				"_testgroup");

		db.createProjectGroup("_testgroup");
		db.addUser("_Oskar", "");
		db.createTimeReport(report);

		// Collect id of TimeReport
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT Id FROM TimeReports WHERE Username='_Oskar' AND WeekNumber=1");
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
		String project = "_prjct";
		String wrongProject = "_NOTprjct";

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
		String username = "_Oskar";
		String groupname = "_testgroup";
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
		String username = "_asdqwdqwd";
		List<Activity> activity = new ArrayList<Activity>();
		activity.add(new Activity(ActivityType.SRS, 60, ActivitySubType.I));
		String projectgroup = "_testgroup";
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
		activity.add(new Activity(ActivityType.SRS, 60, ActivitySubType.I));

		TimeReport report = new TimeReport(new User("_Oskar", ""), activity, false, 0, 1,
				"_testgroup");

		db.createProjectGroup("_testgroup");
		db.addUser("_Oskar", "");
		db.createTimeReport(report);
		int id = 0;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT Id FROM TimeReports ORDER BY Id DESC limit 1");
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
		activity.add(new Activity(ActivityType.SRS, 60, ActivitySubType.I));
		db.createProjectGroup("_testgroup");
		User u = new User("_Oskar", "");
		TimeReport report1 = new TimeReport(u, activity, false, 0, 1, "_testgroup");

		TimeReport report2 = new TimeReport(u, activity, false, 0, 2, "_testgroup");

		db.addUser("_Oskar", "");
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

		List<TimeReport> timereports = db.getTimeReports(u.getUsername(), "_testgroup");
		assertEquals(timereports.get(0).getID(), (int) id.get(1));
		assertEquals(timereports.get(1).getID(), (int) id.get(0));
	}
	
	@Test
	public void testUpdateTimeReport() throws SQLException {
		List<Activity> activity = new ArrayList<Activity>();
		activity.add(new Activity(ActivityType.SRS, 60, ActivitySubType.I));
		db.createProjectGroup("_testgroup");
		User u = new User("_Oskar", "");
		TimeReport report1 = new TimeReport(u, activity, false, 0, 1, "_testgroup");
		db.addUser("_Oskar", "");
		db.createTimeReport(report1);
		ArrayList<Integer> id = new ArrayList<Integer>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT Id FROM TimeReports ORDER BY Id DESC LIMIT 1");
		while (rs.next()) {
			id.add(rs.getInt("Id"));
		}
		rs.close();
		stmt.close();
		activity = new ArrayList<Activity>();
		activity.add(new Activity(ActivityType.SRS, 10, ActivitySubType.I));
		activity.add(new Activity(ActivityType.SDDD, 20, ActivitySubType.I));
		activity.add(new Activity(ActivityType.SDDD, 40, ActivitySubType.F));
		
		TimeReport newTimeReport = new TimeReport(u, activity, false, id.get(0), 1, "_testgroup");
		db.updateTimeReport(newTimeReport);
		TimeReport updatedTimereport = db.getTimeReport(id.get(0));
		assertEquals((int)id.get(0), updatedTimereport.getID());
		assertEquals(report1.getWeek(), updatedTimereport.getWeek());
		assertEquals(report1.getUser().getUsername(), updatedTimereport.getUser().getUsername());
		assertEquals(report1.getProjectGroup(), updatedTimereport.getProjectGroup());
		assertEquals(activity.size(), updatedTimereport.getActivities().size());
		assertEquals(ActivityType.SDDD, updatedTimereport.getActivities().get(0).getType());
		assertEquals(ActivitySubType.F, updatedTimereport.getActivities().get(0).getSubType());
		assertEquals(40, updatedTimereport.getActivities().get(0).getLength());
		assertEquals(ActivityType.SDDD, updatedTimereport.getActivities().get(1).getType());
		assertEquals(ActivitySubType.I, updatedTimereport.getActivities().get(1).getSubType());
		assertEquals(20, updatedTimereport.getActivities().get(1).getLength());
		assertEquals(ActivityType.SRS, updatedTimereport.getActivities().get(2).getType());
		assertEquals(ActivitySubType.I, updatedTimereport.getActivities().get(2).getSubType());
		assertEquals(10, updatedTimereport.getActivities().get(2).getLength());
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
		String username = "_user1";
		String projectname = "_group1";
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
		String username = "_user1";
		String projectname = "_group1";
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
	public void testGetProjectManagersInProjectGroup() throws SQLException {
		List<String> expectedUsers = Arrays.asList("_user1", "_user2");
		List<String> actualUsers = new ArrayList<String>();
		String projectname = "_project1";
		db.createProjectGroup(projectname);
		for (String s: expectedUsers) {
			db.addUser(s, "");
			db.addUserToProject(projectname, s);
			db.setUserRole(s, projectname, Role.Manager);
		}
		db.addUser("_user3", "");
		db.addUserToProject(projectname, "_user3");
		db.setUserRole("_user3", projectname, null);
		
		ResultSet rs = conn.createStatement().executeQuery("SELECT Username FROM Memberships WHERE Role='" + Role.Manager.toString() + "'");
		while (rs.next()) {
			actualUsers.add(rs.getString("Username"));
		}
		
		assertTrue(!actualUsers.contains("user3"));
		for (int i = 0; i < expectedUsers.size(); i++) {
			Assert.assertTrue(actualUsers.contains(expectedUsers.get(i)));
		}
		rs.close();
	}
		
	@Test
	public void testGetRole() throws SQLException {
		String username = "_user1";
		String projectname = "_group1";
		db.addUser(username, "");
		db.createProjectGroup(projectname);
		db.addUserToProject(projectname, username);
		db.setUserRole(username, projectname, Role.Architect);
		
		Role r = db.getRole(username, projectname);
		assertEquals(r, Role.Architect);
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
	
	@Test
	public void testGetTimeForActivity() throws SQLException {
		String user = "_user1";
		String group = "_group1";
		db.addUser(user, "");
		db.createProjectGroup(group);
		User u = db.getUser(user);
		Activity a1 = new Activity(ActivityType.FunctionTest, 65, ActivitySubType.noSubType);
		List<Activity> activities = new ArrayList<Activity>();
		activities.add(a1);
		TimeReport tr = new TimeReport(u, activities, false, 0, 1, group);
		db.createTimeReport(tr);
		
		int time = u.getTimeForActivity(ActivityType.FunctionTest, 1);
		assertEquals(65, time);
	}
	
	@Test
	public void testGetTimeForActivityMultiple() throws SQLException {
		String user = "_user1";
		String group = "_group1";
		db.addUser(user, "");
		db.createProjectGroup(group);
		User u = db.getUser(user);
		Activity a1 = new Activity(ActivityType.FunctionTest, 65, ActivitySubType.noSubType);
		Activity a2 = new Activity(ActivityType.Meeting, Integer.MAX_VALUE, ActivitySubType.noSubType);
		List<Activity> activities = new ArrayList<Activity>();
		activities.add(a1);
		activities.add(a2);
		TimeReport tr = new TimeReport(u, activities, false, 0, 1, group);
		db.createTimeReport(tr);
		
		int time = u.getTimeForActivity(ActivityType.FunctionTest, 1);
		assertEquals(65, time);
		
		assertEquals(0, u.getTimeForActivity(ActivityType.SDP, 1));
		
		assertEquals(Integer.MAX_VALUE, u.getTimeForActivity(ActivityType.Meeting, 1));
		
		assertEquals(0, u.getTimeForActivity(ActivityType.Meeting, 2));
	}
	
	@Test
	public void testGetStatistics() {
		
	}
}
