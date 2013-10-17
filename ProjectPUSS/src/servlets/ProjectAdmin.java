package servlets;

import java.io.IOException;

import html.HTMLWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Denna klassen bygger ut ServletBase och renderar en sida, via HTMLWriter, där
 * alla projektgrupper listas. Det finns funktionalitet för att skapa en ny
 * projektgrupp samt ta bort befintliga projektgrupper. Om den inloggade
 * användaren inte är administratör nekas all åtkomst till klassen och dess
 * funktionalitet.
 */
@WebServlet("/projectadmin")
public class ProjectAdmin extends ServletBase {
	
	private static final String CREATE_GROUP = "createProjectGroup";
	private static final String REMOVE_GROUP = "removeProjectGroup";
	
	
	/**
     * Checks if a project group name corresponds to the requirements for project group names. 
     * @param name The investigated project group name
     * @return True if the project group name corresponds to the requirements
     */
    private boolean checkNewName(String name) {
    	int length = name.length();
    	boolean ok = (length>=5 && length<=25);
    	if (ok)
    		for (int i=0; i<length; i++) {
    			int ci = (int)name.charAt(i);
    			boolean thisOk = ((ci>=48 && ci<=57) || 
    					          (ci>=65 && ci<=90) ||
    					          (ci>=97 && ci<=122));
    			//String extra = (thisOk ? "OK" : "notOK");
    			//System.out.println("bokst:" + name.charAt(i) + " " + (int)name.charAt(i) + " " + extra);
    			ok = ok && thisOk;
    		}    	
    	return ok;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	doGet(req, resp);
    }

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		if(isAdmin(request)) {
			String action = request.getParameter("action");
			if(CREATE_GROUP.equals(action)) {
				String projectName = request.getParameter("projectname");
				if(checkNewName(projectName)) {
					if(database.createProjectGroup(projectName)) {
						html.printSuccessMessage(projectName + " har lagts till i systemet");
					} else {
						html.printErrorMessage(projectName + " kunde inte läggas till i systemet");
					}
					
				} else {
					html.printErrorMessage(projectName + " uppfyller inte kraven för projektgruppnamn");
				}
				
			} else if(REMOVE_GROUP.equals(action)) {
				String projectName = request.getParameter("projectName");
				if(database.deleteProjectGroup(projectName)) {
					html.printSuccessMessage(projectName + " har tagit bort från systemet");
				} else {
					html.printErrorMessage(projectName + " kunde inte tas bort från systemet");
				}
				
				
			}
			
			html.printAddProjectGroupForm();
			html.printProjectGroups(database.getProjects());
			
		} else {
			// redirect to mainpage
			html.printErrorMessage("Du har inte tillgång till denna sidan");
			html.printLink("mainpage", "Huvudsida");
		}

	}

}
