package servlets;

import html.HTMLWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Denna klassen bygger ut ServletBase och renderar sidan för
 * projektetadministration via HTMLWriter. Denna sidan innehåller unika
 * valalternativ för administrering av en projektgrupp. Man kan lägga till, ta
 * bort medlemmar i gruppen samt göra en befintlig medlem till projektledare. Om
 * den inloggade användaren inte är administratör nekas all åtkomst till klassen
 * och dess funktionalitet.
 */
@WebServlet("/projectoverview")
public class ProjectOverView extends ServletBase {
	
	private static final String ADD_USER = "addUser";
	private static final String DELETE_USER = "deleteUser";
	private static final String PROMOTE_USER = "promoteUser";
	private static final String DEMOTE_USER = "demoteUser";

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		if (isAdmin(request)) {
			String action = request.getParameter("action");
			String groupName = request.getParameter("groupName");
			
			if (ADD_USER.equals(action)) {
				
			} else if (DELETE_USER.equals(action)) {
				
			} else if (PROMOTE_USER.equals(action)) {
				
			} else if (DEMOTE_USER.equals(action)) {
				
			}
			
			html.printAddUserToProjectGroupForm(groupName);
			html.printProjectGroupMembers(database.getUsersInProject(groupName), 
					database.getProjectManagersInProject(groupName), 
					groupName);
			

		} else {
			// redirect to mainpage
			html.printErrorMessage("Du har inte tillgång till denna sidan");
			html.printLink("mainpage", "Huvudsida");
		}

	}

}
