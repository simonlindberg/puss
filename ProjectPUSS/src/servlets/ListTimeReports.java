package servlets;

import html.HTMLWriter;
import items.Command;
import items.Role;
import items.TimeReport;
import items.User;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Denna klassen bygger ut ServletBase och renderar en sida, via HTMLWriter, där
 * alla tidrapporter för en projektgrupp listas upp. Beroende på vad man har
 * valt på MainPage visar sidan en lista för att uppdatera tidrapporterna, ta
 * bort någon tidrapport eller signera någon tidrapport. Sidan är tillgänglig
 * för alla.
 * 
 */
@WebServlet("/listreports")
public class ListTimeReports extends ServletBase {

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		User user = (User) request.getSession().getAttribute(ServletBase.USER);
		String projectGroup = (String) request.getSession().getAttribute(ServletBase.PROJECT);

		String command = request.getParameter(HTMLWriter.LIST_COMMAND);

		if (command == null) {
			html.printErrorMessage("Du har inte anget något kommando! Gå tillbaka till <a href=\"mainpage\"> huvudsidan</a> och börja om! ");
		} else {
			List<TimeReport> timereports = database.getTimeReports(user.getUsername(), projectGroup);
			Role userRole = database.getRole(user.getUsername(), projectGroup);
			html.printTimeReports(timereports, Command.valueOf(command), userRole);
		}
	}
}
