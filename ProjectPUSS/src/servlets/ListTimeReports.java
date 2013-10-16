package servlets;

import items.Command;
import items.Role;
import items.TimeReport;
import items.User;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import html.HTMLWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import database.Database;

/**
 * Denna klassen bygger ut ServletBase och renderar en sida, via HTMLWriter, där
 * alla tidrapporter för en projektgrupp listas upp. Beroende på vad man har
 * valt på MainPage visar sidan en lista för att uppdatera tidrapporterna, ta
 * bort någon tidrapport eller signera någon tidrapport. Sidan är tillgänglig
 * för alla.
 * 
 */
@WebServlet("/TimeReports")
public class ListTimeReports extends ServletBase {

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		User user = (User) request.getSession().getAttribute(ServletBase.USER);
		String projectGroup = (String) request.getSession().getAttribute(ServletBase.PROJECT);

		// Role userRole = Database.getInstance().getRole(user);
		if (request.getAttribute("page") == "delete") {
			List<TimeReport> timereports;
			timereports = database.getTimeReports(user.getUsername(), projectGroup);
			// /####GLÖM INTE ATT ROLE SKA HÄMTAS FRÅN SESSION!#####
			html.printTimeReports(timereports, Command.delete, Role.Developer);
		}

	}

}
