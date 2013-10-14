package servlets;

import html.HTMLWriter;

import javax.servlet.http.HttpServletRequest;

/**
 * Denna klassen bygger ut ServletBase och renderar en sida, via HTMLWriter, där
 * alla projektgrupper listas. Det finns funktionalitet för att skapa en ny
 * projektgrupp samt ta bort befintliga projektgrupper. Om den inloggade
 * användaren inte är administratör nekas all åtkomst till klassen och dess
 * funktionalitet.
 */
public class ProjectAdmin extends ServletBase {

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		// TODO Auto-generated method stub

	}

}
