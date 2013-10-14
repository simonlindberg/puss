package servlets;

import html.HTMLWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Denna klassen bygger ut ServletBase och renderar inloggningssidan, via
 * HTMLWriter, där man fyller i användarnamn och lösenord. Den innehåller
 * dessutom funktionaliteten för att logga in och ut ur system och används av
 * alla som försöker ansluta till systemet.
 */
public class LogIn extends ServletBase {

	/**
	 * Anropas när /login får ett <code>POST</code> anrop.
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		// TODO Auto-generated method stub

	}

}
