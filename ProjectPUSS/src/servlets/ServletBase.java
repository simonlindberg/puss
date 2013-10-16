package servlets;

import html.HTMLWriter;
import items.User;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Database;

/**
 * Den abstrakta superklassen som utökas av servlets.
 * 
 */
public abstract class ServletBase extends HttpServlet {

	final static protected String USER = "user";
	final static protected String LOGGEDIN = "loggedin";
	final static protected String PROJECT = "project";

	protected Database database;

	public ServletBase() {
		try {
			database = Database.getInstance();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Kollar om man är inloggad.
	 * 
	 * @param request
	 * @return <code>true</code> om användaren är inloggad, annars
	 *         <code>false</code>.
	 */
	protected boolean loggedIn(HttpServletRequest request) {
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute(USER);
		Object loggedIn = session.getAttribute(LOGGEDIN);

		return user != null && loggedIn != null && (boolean) loggedIn;
	}

	/**
	 * Kallas när servern får ett <code>GET</code> anrop.
	 * 
	 * @param request
	 *            information om anropet
	 * @param response
	 *            samling av datan som ska skickas tillbaka.
	 * @throws IOException 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		
		if (loggedIn(request)) {
			response.sendRedirect("/login");
			System.out.println("hello!");
		}
		
		HTMLWriter writer = new HTMLWriter(response.getWriter());
		writer.printHead((User) request.getSession().getAttribute(USER));
		doWork(request, writer);
		writer.printFoot();
	}

	/**
	 * Används för att delegar ut arbete till subklasserna.
	 * 
	 * @param request
	 *            information om anropet.
	 * @param html
	 *            används för att skriva html kod till responset.
	 */
	protected abstract void doWork(HttpServletRequest request, HTMLWriter html);
}
