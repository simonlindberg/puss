package servlets;

import html.HTMLWriter;
import items.User;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Denna klassen bygger ut ServletBase och renderar huvudsidan, via HTMLWriter,
 * som användare hamnar på efter inloggning. På sidan kan man välja vilken
 * projektgrupp man ska använda, samt tar sig vidare till funktionalitet, tex
 * skapa en ny tidrapport, administrera över användare eller visa statistik.
 */
@WebServlet("/mainpage")
public class MainPage extends ServletBase {

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		HttpSession session = request.getSession();

		String chosen = (String) request.getParameter(HTMLWriter.PROJECT_CHOOSER);
		
		System.out.println("chosen "  + chosen);
		if(chosen != null){
			session.setAttribute(PROJECT, chosen);
		}

		User user = (User) session.getAttribute(USER);
		List<String> projects = database.getProjects(user);

		String currentProjectGroup = (String) session.getAttribute(PROJECT);
		System.out.println(currentProjectGroup);
		currentProjectGroup = currentProjectGroup == null ? projects.get(0) : currentProjectGroup;
		
		html.printProjectChooser(projects.indexOf(currentProjectGroup), projects);
	}

}
