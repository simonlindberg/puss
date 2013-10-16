/**
 * 
 */
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import items.Activity;
import items.ActivityType;
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
		while (rs.next()) {
			result = rs.getString("username");
		}
		stmt.close();

		assertEquals("", result);
	}

	@Test
	public void testDeleteUserDoesntExist() throws SQLException {
		String name = "_Christian";	// Underscore to ensure user is outside SRS scope		
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
	public void testCreateTimereport() throws SQLException {
		int id = 0;
		String groupname = "Testgroup";
		String username = "Tester";
		int weeknumber = 1;
		int time = 60;
		int signed = 0;
		String actualGroupname = "";
		String actualUsername = ""; 
		int actualWeeknumber = 0; 
		int actualTime = 0;
		int actualSigned = 0;
		String actualActivityname = "";
		
		List<Activity> activity = new ArrayList<Activity>();
		activity.add(new Activity(ActivityType.SRS, time));

		TimeReport report = new TimeReport(new User(username, ""), activity, false, id, weeknumber,
				groupname);

		db.addUser(username, "");
		db.createTimeReport(report);

		// Test values in Table:TimeReports
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM TimeReports WHERE Id='" + id + "'");
		while (rs.next()) {
			actualGroupname = rs.getString("groupname");
			actualUsername = rs.getString("username");
			actualWeeknumber = rs.getInt("weeknumber");
			actualSigned = rs.getInt("signed");
		}
		assertEquals(actualGroupname, groupname);
		assertEquals(actualUsername, username);
		assertEquals(actualWeeknumber, weeknumber);
		assertTrue(actualSigned == signed);
		rs.close();
		stmt.close();

		// Test values in Table:Activities
		stmt = conn.createStatement();
		rs = stmt.executeQuery("SELECT * FROM Activity WHERE Id='" + id + "'");
		while (rs.next()) {
			actualActivityname = rs.getString("activityname");
			actualTime = rs.getInt("minutesworked");
		}
		assertEquals(actualActivityname, ActivityType.SRS.toString());
		assertEquals(actualTime, time);
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM ProjectGroups WHERE GroupName='" + projectName + "'");
			rs.next();
			assertEquals(projectName, rs.getString(0));
		} catch (SQLException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
