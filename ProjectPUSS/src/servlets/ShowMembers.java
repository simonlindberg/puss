package servlets;

import html.HTMLWriter;

import javax.servlet.http.HttpServletRequest;

/**
 * Denna klass bygger ut ServletBase och renderar en sida, via HTMLWriter, där
 * användarna för en viss grupp är listade. Beroende på vilken roll man har
 * visar listan möjlighet att lägga till och uppdatera roller för användarna.
 * Sidan är tillgänglig för alla.
 */
public class ShowMembers extends ServletBase {

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		// TODO Auto-generated method stub

	}

}
