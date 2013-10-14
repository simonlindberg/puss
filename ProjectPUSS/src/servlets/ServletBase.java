package servlets;

import html.HTMLWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  Den abstrakta superklassen som utökas av servlets.
 *
 */
public abstract class ServletBase extends HttpServlet {

	/**
	 * Kollar om man är inloggad.
	 * 
	 * @param request
	 * @return <code>true</code> om användaren är inloggad, annars
	 *         <code>false</code>.
	 */
	protected boolean loggedIn(HttpServletRequest request) {
		return true;
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
