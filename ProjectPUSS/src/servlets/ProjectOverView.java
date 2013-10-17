package servlets;

import java.io.IOException;

import html.HTMLWriter;
import items.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		if (isAdmin(request)) {
			String action = request.getParameter("action");
			String projectName = request.getParameter("project");
			String username = request.getParameter("username");

			if (username != null) {

				if (ADD_USER.equals(action)) {
					if (database.addUserToProject(projectName, username)) {
						html.printSuccessMessage(username + " har lagts till i " + projectName + ".");
					} else {
						html.printErrorMessage(username + " kunde inte läggas till i " + projectName
								+ ".");
					}

				} else if (DELETE_USER.equals(action)) {
					if (database.deleteUserFromProject(projectName, username)) {
						html.printSuccessMessage(username + " har tagits bort från " + projectName + ".");
					} else {
						html.printErrorMessage(username + " kunde inte tas bort från " + projectName
								+ ".");
					}

				} else if (PROMOTE_USER.equals(action)) {
					database.setUserRole(username, projectName, Role.Manager);
					html.printSuccessMessage(username + " har blivit projektledare.");
				} else if (DEMOTE_USER.equals(action)) {
					database.setUserRole(username, projectName, null);
					html.printSuccessMessage(username + " är inte längre projektledare.");
				}
			}
			
			html.printLink("projectadmin", "Återvänd till Administrera projektgrupper");
			html.printAddUserToProjectGroupForm(projectName);
			html.printProjectGroupMembers(database.getUsersInProject(projectName),
					database.getProjectManagersInProject(projectName), projectName);

		} else {
			// redirect to mainpage
			html.printErrorMessage("Du har inte tillgång till denna sidan");
			html.printLink("mainpage", "Huvudsida");
		}

	}

}
