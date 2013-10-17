package servlets;

import html.HTMLWriter;
import items.Command;
import items.Role;
import items.TimeReport;
import items.User;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	@Override 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		User user = (User) request.getSession().getAttribute(ServletBase.USER);
		String projectGroup = (String) request.getSession().getAttribute(ServletBase.PROJECT);
		
		Role role = database.getRole(user.getUsername(), projectGroup);

		String id = request.getParameter(HTMLWriter.ID);
		Command command = Command.valueOf(request.getParameter(HTMLWriter.LIST_COMMAND));
		
		if (Command.sign.equals(command) && id != null && Role.Manager.equals(role)) {
			TimeReport tr = database.getTimeReport(Integer.parseInt(id));
			if (tr.getSigned()){
				database.unsignTimeReport(tr);
			}else{
				database.signTimeReport(tr);
			}
		}
		doGet(request, response);
	}
}
