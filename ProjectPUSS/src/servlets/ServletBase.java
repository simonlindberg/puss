package servlets;

import html.HTMLWriter;
import items.User;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Den abstrakta superklassen som utökas av servlets.
 * 
 */
public abstract class ServletBase extends HttpServlet {

	final private String USER = "user";
	final private String LOGGEDIN = "loggedin";
	final private String PROJECT = "project";

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
		boolean loggedIn = (boolean) session.getAttribute(LOGGEDIN);

		return user != null && loggedIn;
	}

	/**
	 * Kallas när servern får ett <code>GET</code> anrop.
	 * 
	 * @param request
	 *            information om anropet
	 * @param response
	 *            samling av datan som ska skickas tillbaka.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		HTMLWriter writer;
		try {
			writer = new HTMLWriter(response.getWriter());
			// TODO: Get the user from session variable
			writer.printHead(null);
			doWork(request, writer);
			writer.printFoot();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
