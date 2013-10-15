/**
 * 
 */
package test;

import static org.junit.Assert.*;
import items.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
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
	}

	@Test
	public void testGetUser() throws SQLException {
		String name = "Christian";
		String psw = "psw";
		String result = "";
		
		Statement stmt = conn.createStatement();
    	String statement = "INSERT INTO Users (username, password) VALUES('" + name + "', '" + psw + "')";
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
}
