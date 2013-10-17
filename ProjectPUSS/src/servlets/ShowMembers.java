package servlets;

import java.util.HashMap;
import java.util.List;

import items.Role;
import items.User;
import html.HTMLWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Denna klass bygger ut ServletBase och renderar en sida, via HTMLWriter, där
 * användarna för en viss grupp är listade. Beroende på vilken roll man har
 * visar listan möjlighet att lägga till och uppdatera roller för användarna.
 * Sidan är tillgänglig för alla.
 */
@WebServlet("/showmembers")
public class ShowMembers extends ServletBase {

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		User user = (User) request.getSession().getAttribute(ServletBase.USER);
		String projectgroup = (String) request.getSession().getAttribute(ServletBase.PROJECT);
		List<User> users = database.getUsersInProject(projectgroup);
		HashMap<String, Role> userRoles = new HashMap<String, Role>();
		for(User u : users){
			userRoles.put(u.getUsername(), database.getRole(u.getUsername(), projectgroup));
		}
		Role role = null;
		role = database.getRole(user.getUsername(), projectgroup);
		html.printUsers(users, role, userRoles);

	}

}
