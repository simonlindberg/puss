package servlets;

import items.Activity;
import items.ActivityType;
import items.Command;
import items.Role;
import items.TimeReport;
import items.User;

import java.beans.Statement;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import html.HTMLWriter;

import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Database;

/**
 * Denna klass bygger ut ServletBase och renderar en sida, via HTMLWriter, för
 * att skapa en tidsrapport. Användaren fyller här i tidsrapporten och klassen
 * meddelar även användaren om det lyckades eller om det uppstod problem med att
 * skapa tidrapporten. Sidan är tillgänglig för användare, men ej för
 * administratörer.
 */
@WebServlet("/timereport")
public class DisplayTimeReport extends ServletBase {

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		User user = (User) request.getSession().getAttribute(USER);
		String projectgroup = (String) request.getSession().getAttribute(PROJECT);
		String page = (String) request.getParameter(HTMLWriter.LIST_COMMAND) == null ? "" : (String) request
				.getParameter(HTMLWriter.LIST_COMMAND);
		String id = (String) request.getParameter(HTMLWriter.ID) == null ? "" : (String) request
				.getParameter(HTMLWriter.ID);
		Role role = database.getRole(user.getUsername(), projectgroup);
		
		if(role==null){
			html.printErrorMessage("Du saknar befogenhet för att skapa eller visa en tidrapport.");
		} else {
			html.printSuccessMessage(page + "<br/>");
			// Check command
			if (page.equals(Command.create.toString())) {
				TimeReport t = new TimeReport(user, null, false, 0, -1, projectgroup);
				database.createTimeReport(t);
				html.printTimeReport(t, Command.create, role);
			} else if (page.equals(Command.update.toString())) {
				TimeReport t = database.getTimeReport(Integer.parseInt(id));
				html.printTimeReport(t, Command.update, role);
			} else if (page.equals(Command.show.toString())) {
				TimeReport t = database.getTimeReport(Integer.parseInt(id));
				html.printTimeReport(t, Command.show, role);
			} else {
				html.printErrorMessage("Nothing here to see");
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		User user = (User) request.getSession().getAttribute(USER);
		String projectgroup = (String) request.getSession().getAttribute(PROJECT);
		String page = (String) request.getParameter(HTMLWriter.LIST_COMMAND) == null ? "" : (String) request
				.getParameter(HTMLWriter.LIST_COMMAND);
		String id = (String) request.getParameter(HTMLWriter.ID) == null ? "" : (String) request
				.getParameter(HTMLWriter.ID);
		
		String submitReport = request.getParameter("submitreport");
		if (submitReport != null) {
			List<Activity> activities = Arrays.asList(
					new Activity(ActivityType.SDP, Integer.parseInt(request.getParameter(HTMLWriter.SDP))),
					new Activity(ActivityType.SRS, Integer.parseInt(request.getParameter(HTMLWriter.SRS))),
					new Activity(ActivityType.SVVS, Integer.parseInt(request.getParameter(HTMLWriter.SVVS))),
					new Activity(ActivityType.STLDD, Integer.parseInt(request.getParameter(HTMLWriter.STLDD))),
					new Activity(ActivityType.SVVI, Integer.parseInt(request.getParameter(HTMLWriter.SVVI))),
					new Activity(ActivityType.SDDD, Integer.parseInt(request.getParameter(HTMLWriter.SDDD))),
					new Activity(ActivityType.SVVR, Integer.parseInt(request.getParameter(HTMLWriter.SVVR))),
					new Activity(ActivityType.SSD, Integer.parseInt(request.getParameter(HTMLWriter.SSD))),
					new Activity(ActivityType.PFR, Integer.parseInt(request.getParameter(HTMLWriter.PFR))),
					new Activity(ActivityType.FunctionTest, Integer.parseInt(request.getParameter(HTMLWriter.FUNCTION_TEST))),
					new Activity(ActivityType.SystemTest, Integer.parseInt(request.getParameter(HTMLWriter.SYSTEM_TEST))),
					new Activity(ActivityType.RegressionTest, Integer.parseInt(request.getParameter(HTMLWriter.REG_TEST))),
					new Activity(ActivityType.Meeting, Integer.parseInt(request.getParameter(HTMLWriter.MEETING)))
					);
			TimeReport t = new TimeReport(
					user,
					activities,
					false,
					Integer.parseInt(id),
					Integer.parseInt(request.getParameter(HTMLWriter.WEEK)),
					projectgroup
					);
			// database.updateTimeReport(t)
			if (page.equals(Command.create.toString())) {
				database.createTimeReport(t);
				response.sendRedirect("mainpage");
			} else {
				database.updateTimeReport(t);
				doGet(request, response);
			}
		}	
	}

}
