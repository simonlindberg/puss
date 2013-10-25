package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import items.Role;
import items.User;
import html.HTMLWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		Statistics.done=true;
		User user = (User) request.getSession().getAttribute(ServletBase.USER);
		String projectgroup = (String) request.getSession().getAttribute(ServletBase.PROJECT);
		Role role = Role.NoRole;
		role = database.getRole(user.getUsername(), projectgroup);
		List<User> users = database.getUsersInProject(projectgroup);
		HashMap<String, Role> userRoles = new HashMap<String, Role>();
		String updateRoles = request.getParameter(HTMLWriter.SUBMIT_UPDATE_ROLE);
		if(updateRoles!=null){
			for(User u : users){
				String roleForUser = request.getParameter(HTMLWriter.SELECT_USERROLE+u.getUsername());
				if(roleForUser!=null && role.equals(Role.Manager)){
					Role newRole = Role.valueOf(roleForUser);
					if(!newRole.equals(Role.Manager))
						database.setUserRole(u.getUsername(), projectgroup, newRole);
				}
			}
			
			html.printSuccessMessage("Uppdaterat rollerna");
		}
		for(User u : users){
			userRoles.put(u.getUsername(), database.getRole(u.getUsername(), projectgroup));
		}
		html.printUsers(users, role, userRoles);

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		doGet(request, response);
	}

}
