package test;

import static org.junit.Assert.assertEquals;
import html.HTMLWriter;
import items.User;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HTMLWriterTest {

	private StringWriter sw;
	private HTMLWriter hw;

	@Before
	public void setUp() throws Exception {
		sw = new StringWriter();
		hw = new HTMLWriter(new PrintWriter(sw));
	}

	@After
	public void tearDown() throws Exception {
		sw = null;
		hw = null;
	}

	@Test
	public void testPrintHead() {
		hw.printHead(null);
		assertEquals(
				"<html><head><title>E-PUSS 1301</title></head><body><h1>E-PUSS 1301</h1>",
				sw.toString());
	}

	@Test
	public void testPrintFoot() {
		hw.printFoot();
		assertEquals("</body></html>", sw.toString());
	}

	@Test
	public void testPrintAddUserForm() {
		hw.printAddUserForm();
		assertEquals(
				"<form method=\"POST\" action=\"/Administration?action=createUser\">"
						+ "<label>Användarnamn</label><input name=\"username\" type=\"text\" />"
						+ "<input type=\"submit\" value=\"Skapa\" />"
						+ "</form>", sw.toString());
	}

	@Test
	public void testPrintAddProjectGroupForm() {
		hw.printAddProjectGroupForm();
		assertEquals(
				"<form method=\"POST\" action=\"/ProjectAdmin?action=createProjectGroup\">"
				+ "<label>Projekgruppnamn</label><input name=\"projectname\" type=\"text\" />"
				+ "<input type=\"submit\" value=\"Skapa\" />"
						+ "</form>", sw.toString());
	}

	@Test
	public void testPrintAdminUserListEmptyList() {
		ArrayList<User> users = new ArrayList<User>();
		hw.printAdminUserList(users);
		assertEquals("", sw.toString());
	}

	@Test
	public void testPrintAdminUserListOneUser() {
		ArrayList<User> users = new ArrayList<User>();
		users.add(new User("abcde0", "password"));
		hw.printAdminUserList(users);
		assertEquals(
				"<table><tr><th>Användare</th><th>Passwords</th><th></th></tr><tr><td>abcde0</td><td>password</td><td><a href=\"/Administration?action=deleteUser&username=abcde0\">Ta bort</a></td></tr></table>",
				sw.toString());
	}

	@Test
	public void testPrintAdminUserListMultipleUsers() {
		ArrayList<User> users = new ArrayList<User>();
		users.add(new User("abcde0", "password0"));
		users.add(new User("abcde1", "password1"));
		users.add(new User("abcde2", "password2"));
		hw.printAdminUserList(users);
		assertEquals("<table><tr><th>Användare</th><th>Passwords</th><th></th></tr>"
				+ "<tr><td>abcde0</td><td>password0</td><td><a href=\"/Administration?action=deleteUser&username=abcde0\">Ta bort</a></td></tr>"
				+ "<tr><td>abcde1</td><td>password1</td><td><a href=\"/Administration?action=deleteUser&username=abcde1\">Ta bort</a></td></tr>"
				+ "<tr><td>abcde2</td><td>password2</td><td><a href=\"/Administration?action=deleteUser&username=abcde2\">Ta bort</a></td></tr>" 
				+ "</table>",
				sw.toString());
	}

}
