package servlets;

import html.HTMLWriter;

import javax.servlet.http.HttpServletRequest;

/**
 * Denna klassen bygger ut ServletBase och renderar sidan för
 * projektetadministration via HTMLWriter. Denna sidan innehåller unika
 * valalternativ för administrering av en projektgrupp. Man kan lägga till, ta
 * bort medlemmar i gruppen samt göra en befintlig medlem till projektledare. Om
 * den inloggade användaren inte är administratör nekas all åtkomst till klassen
 * och dess funktionalitet.
 */
public class ProjectOverView extends ServletBase {

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		// TODO Auto-generated method stub

	}

}
