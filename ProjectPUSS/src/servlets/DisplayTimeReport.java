package servlets;

import items.Activity;
import items.ActivitySubType;
import items.ActivityType;
import items.Command;
import items.Role;
import items.TimeReport;
import items.User;

import java.beans.Statement;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
		Statistics.done=true;
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
			// Check command
			String submitReport = request.getParameter("submitreport");
			if (page.equals(Command.create.toString()) && submitReport != null) {
				List<Activity> activities = createActivityListFromRequest(request);
				int week = Integer.parseInt(request.getParameter(HTMLWriter.WEEK));
				TimeReport t = new TimeReport(user, activities, false, 0, week, projectgroup);
				html.printErrorMessage("Din inputdata är fel");
				html.printTimeReport(t, Command.create, role);
			} else if (page.equals(Command.create.toString())) {
				TimeReport t = new TimeReport(user, new ArrayList<Activity>(), false, 0, -1, projectgroup);
				html.printTimeReport(t, Command.create, role);
			} else if (page.equals(Command.update.toString())) {
				TimeReport t = database.getTimeReport(Integer.parseInt(id));
				if(t.getSigned()){
					html.printErrorMessage("Du kan inte uppdatera en rapport som är signerad!");
					html.printTimeReport(t, Command.show, role);
				} else {
					html.printTimeReport(t, Command.update, role);
				}
			} else if (page.equals(Command.show.toString())) {
				TimeReport t = database.getTimeReport(Integer.parseInt(id));
				System.out.println(t.getActivities());
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
			List<Activity> activities = createActivityListFromRequest(request);
			if (page.equals(Command.create.toString())) {
				TimeReport t = new TimeReport(
						user,
						activities,
						false,
						0,
						Integer.parseInt(request.getParameter(HTMLWriter.WEEK)),
						projectgroup
						);
				if(database.createTimeReport(t)){
					response.sendRedirect("listreports?command=show&message=success_create");
				} else {
					doGet(request, response);
				}
				
			} else {
				System.out.println("TEMP");
				TimeReport t = new TimeReport(
						user,
						activities,
						false,
						Integer.parseInt(id),
						Integer.parseInt(request.getParameter(HTMLWriter.WEEK)),
						projectgroup
						);
				database.updateTimeReport(t);
				doGet(request, response);
			}
		}
		
		String deleteReport = request.getParameter("deletereport");
		if (deleteReport != null) {
			database.deleteTimeReport(Integer.parseInt(id));
			response.sendRedirect("mainpage");
		}
		
		String signReport = request.getParameter("signreport");
		if (signReport != null) {
			TimeReport t = database.getTimeReport(Integer.parseInt(id));
			if (t.getSigned()){
				database.unsignTimeReport(t);
			}else{
				database.signTimeReport(t);
			}
			doGet(request, response);
		}
	}
	
	private List<Activity> createActivityListFromRequest(HttpServletRequest request){
		List<Activity> activities = Arrays.asList(
				new Activity(ActivityType.SDP, Integer.parseInt(request.getParameter(HTMLWriter.SDP_F)), ActivitySubType.F),
				new Activity(ActivityType.SDP, Integer.parseInt(request.getParameter(HTMLWriter.SDP_I)), ActivitySubType.I),
				new Activity(ActivityType.SDP, Integer.parseInt(request.getParameter(HTMLWriter.SDP_O)), ActivitySubType.O),
				new Activity(ActivityType.SDP, Integer.parseInt(request.getParameter(HTMLWriter.SDP_U)), ActivitySubType.U),
				
				new Activity(ActivityType.SRS, Integer.parseInt(request.getParameter(HTMLWriter.SRS_F)), ActivitySubType.F),
				new Activity(ActivityType.SRS, Integer.parseInt(request.getParameter(HTMLWriter.SRS_I)), ActivitySubType.I),
				new Activity(ActivityType.SRS, Integer.parseInt(request.getParameter(HTMLWriter.SRS_O)), ActivitySubType.O),
				new Activity(ActivityType.SRS, Integer.parseInt(request.getParameter(HTMLWriter.SRS_U)), ActivitySubType.U),
				
				new Activity(ActivityType.SVVS, Integer.parseInt(request.getParameter(HTMLWriter.SVVS_F)), ActivitySubType.F),
				new Activity(ActivityType.SVVS, Integer.parseInt(request.getParameter(HTMLWriter.SVVS_I)), ActivitySubType.I),
				new Activity(ActivityType.SVVS, Integer.parseInt(request.getParameter(HTMLWriter.SVVS_O)), ActivitySubType.O),
				new Activity(ActivityType.SVVS, Integer.parseInt(request.getParameter(HTMLWriter.SVVS_U)), ActivitySubType.U),
				
				new Activity(ActivityType.STLDD, Integer.parseInt(request.getParameter(HTMLWriter.STLDD_F)), ActivitySubType.F),
				new Activity(ActivityType.STLDD, Integer.parseInt(request.getParameter(HTMLWriter.STLDD_I)), ActivitySubType.I),
				new Activity(ActivityType.STLDD, Integer.parseInt(request.getParameter(HTMLWriter.STLDD_O)), ActivitySubType.O),
				new Activity(ActivityType.STLDD, Integer.parseInt(request.getParameter(HTMLWriter.STLDD_U)), ActivitySubType.U),
				
				new Activity(ActivityType.SVVI, Integer.parseInt(request.getParameter(HTMLWriter.SVVI_F)), ActivitySubType.F),
				new Activity(ActivityType.SVVI, Integer.parseInt(request.getParameter(HTMLWriter.SVVI_I)), ActivitySubType.I),
				new Activity(ActivityType.SVVI, Integer.parseInt(request.getParameter(HTMLWriter.SVVI_O)), ActivitySubType.O),
				new Activity(ActivityType.SVVI, Integer.parseInt(request.getParameter(HTMLWriter.SVVI_U)), ActivitySubType.U),
				
				new Activity(ActivityType.SDDD, Integer.parseInt(request.getParameter(HTMLWriter.SDDD_F)), ActivitySubType.F),
				new Activity(ActivityType.SDDD, Integer.parseInt(request.getParameter(HTMLWriter.SDDD_I)), ActivitySubType.I),
				new Activity(ActivityType.SDDD, Integer.parseInt(request.getParameter(HTMLWriter.SDDD_O)), ActivitySubType.O),
				new Activity(ActivityType.SDDD, Integer.parseInt(request.getParameter(HTMLWriter.SDDD_U)), ActivitySubType.U),
				
				new Activity(ActivityType.SVVR, Integer.parseInt(request.getParameter(HTMLWriter.SVVR_F)), ActivitySubType.F),
				new Activity(ActivityType.SVVR, Integer.parseInt(request.getParameter(HTMLWriter.SVVR_I)), ActivitySubType.I),
				new Activity(ActivityType.SVVR, Integer.parseInt(request.getParameter(HTMLWriter.SVVR_O)), ActivitySubType.O),
				new Activity(ActivityType.SVVR, Integer.parseInt(request.getParameter(HTMLWriter.SVVR_U)), ActivitySubType.U),
				
				new Activity(ActivityType.SSD, Integer.parseInt(request.getParameter(HTMLWriter.SSD_F)), ActivitySubType.F),
				new Activity(ActivityType.SSD, Integer.parseInt(request.getParameter(HTMLWriter.SSD_I)), ActivitySubType.I),
				new Activity(ActivityType.SSD, Integer.parseInt(request.getParameter(HTMLWriter.SSD_O)), ActivitySubType.O),
				new Activity(ActivityType.SSD, Integer.parseInt(request.getParameter(HTMLWriter.SSD_U)), ActivitySubType.U),
				
				new Activity(ActivityType.PFR, Integer.parseInt(request.getParameter(HTMLWriter.PFR_F)), ActivitySubType.F),
				new Activity(ActivityType.PFR, Integer.parseInt(request.getParameter(HTMLWriter.PFR_I)), ActivitySubType.I),
				new Activity(ActivityType.PFR, Integer.parseInt(request.getParameter(HTMLWriter.PFR_O)), ActivitySubType.O),
				new Activity(ActivityType.PFR, Integer.parseInt(request.getParameter(HTMLWriter.PFR_U)), ActivitySubType.U),
				
				new Activity(ActivityType.FunctionTest, Integer.parseInt(request.getParameter(HTMLWriter.FUNCTION_TEST)), ActivitySubType.noSubType),
				new Activity(ActivityType.SystemTest, Integer.parseInt(request.getParameter(HTMLWriter.SYSTEM_TEST)), ActivitySubType.noSubType),
				new Activity(ActivityType.RegressionTest, Integer.parseInt(request.getParameter(HTMLWriter.REG_TEST)), ActivitySubType.noSubType),
				new Activity(ActivityType.Meeting, Integer.parseInt(request.getParameter(HTMLWriter.MEETING)), ActivitySubType.noSubType)
				);
		return activities;
	}

}
