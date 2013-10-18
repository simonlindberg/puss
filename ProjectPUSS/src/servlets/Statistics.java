package servlets;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import items.ActivityType;
import items.GraphSettings;
import items.Role;
import items.TimeReport;
import items.User;
import html.HTMLWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Denna klass bygger ut ServletBase och sköter generering av grafer och diagram
 * som visar statistik, se (Software Requirements Specifikation (dok.nr:
 * PUSS134102, ver:1.). Klassen sköter generering av statistik både för vanliga
 * användare och projektledare.
 * 
 */
@WebServlet("/statistics")
public class Statistics extends ServletBase {

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {

		User user = (User) request.getSession().getAttribute(ServletBase.USER);
		String projectGroup = (String) request.getSession().getAttribute(ServletBase.PROJECT);
		Role userRole = database.getRole(user.getUsername(), projectGroup);

		html.printGraphChoice(userRole);
		String graphType = request.getParameter("graphChooser");

		GraphSettings gs = null;
		List<TimeReport> tr = null;

		switch (graphType) {
		case "userWeekTime":

			tr = database.getTimeReports(user.getUsername(), projectGroup);
			if (tr == null) {
				html.printErrorMessage("Det finns inga registrerade tidsrapporter för denna användare");
				break;
			}
			html.printGraphChoice(userRole);

			Collections.sort(tr, new Comparator<TimeReport>() {
				@Override
				public int compare(final TimeReport object1, final TimeReport object2) {
					return object1.getWeek() - object2.getWeek();
				}
			});

			int firstWeek = tr.get(0).getWeek();
			int lastWeek = tr.get(tr.size() - 1).getWeek();

			html.printWeekChoice(firstWeek, lastWeek);

			int startWeek = Integer.parseInt(request.getParameter("startweek"));
			int stopWeek = Integer.parseInt(request.getParameter("lastweek"));

			if (stopWeek <= startWeek) {
				html.printErrorMessage("Slutveckan måste vara större än startveckan");
			} else {
				gs = new GraphSettings(graphType, null, "Veckonummer", "Arbetade timmar");
				List<TimeReport> finalTimeReports = tr.subList(startWeek, stopWeek);
				html.printGraph(finalTimeReports, gs);
			}

			break;

		case "ActivityTime":

			tr = database.getTimeReports(user.getUsername(), projectGroup);
			if (tr == null) {
				html.printErrorMessage("Det finns inga registrerade tidsrapporter för denna användare");
				break;
			}
			html.printActivityChoice();
			ActivityType activityChoice = ActivityType.valueOf(request
					.getParameter("activityChoice"));
			gs = new GraphSettings(graphType, activityChoice, "Aktivitet", "Arbetade timmar");
			html.printGraph(tr, gs);

			break;

		case "PlUserTime":

			List<User> users = database.getUsersInProject(projectGroup);
			html.printUserChoice(users);
			User finalUser = null;
			String userChoice = request.getParameter("userChoice");

			for (User u : users) {
				if (u.getUsername().equals(userChoice)) {
					finalUser = u;
				}
			}

			tr = database.getTimeReports(finalUser.getUsername(), projectGroup);

			if (tr == null) {
				html.printErrorMessage("Det finns inga registrerade tidsrapporter för denna användare");
				break;
			}

			gs = new GraphSettings(graphType, null, "Veckonummer", "Arbetade timmar");
			html.printGraph(tr, gs);

			break;

		case "weekBurnDown":

			tr = database.getAllTimeReports(projectGroup);

			if (tr == null) {
				html.printErrorMessage("Det finns inga registrerade tidsrapporter för denna projektgrupp");
				break;
			}
			html.printGraphChoice(userRole);

			Collections.sort(tr, new Comparator<TimeReport>() {
				@Override
				public int compare(final TimeReport object1, final TimeReport object2) {
					return object1.getWeek() - object2.getWeek();
				}
			});

			int firstWeek2 = tr.get(0).getWeek();
			int lastWeek2 = tr.get(tr.size() - 1).getWeek();

			html.printWeekChoice(firstWeek2, lastWeek2);

			int startWeek2 = Integer.parseInt(request.getParameter("startweek"));
			int stopWeek2 = Integer.parseInt(request.getParameter("lastweek"));

			if (stopWeek2 <= startWeek2) {
				html.printErrorMessage("Slutveckan måste vara större än startveckan");
			} else {
				gs = new GraphSettings(graphType, null, "Veckonummer", "Återstående timmar");
				List<TimeReport> finalTimeReports = tr.subList(startWeek2, stopWeek2);
				html.printBurndownChart(finalTimeReports, gs);
			}

			break;

		case "activityBurnDown":

			tr = database.getAllTimeReports(projectGroup);

			if (tr == null) {
				html.printErrorMessage("Det finns inga registrerade tidsrapporter för denna projektgrupp");
				break;
			}

			html.printActivityChoice();
			ActivityType activityChoice2 = ActivityType.valueOf(request
					.getParameter("activityChoice"));
			gs = new GraphSettings(graphType, activityChoice2, "Veckonummer", "Återstående timmar");
			html.printBurndownChart(tr, gs);

			break;

		case "userBurnDown":

			List<User> users2 = database.getUsersInProject(projectGroup);
			html.printUserChoice(users2);
			User finalUser2 = null;
			String userChoice2 = request.getParameter("userChoice");

			for (User u : users2) {
				if (u.getUsername().equals(userChoice2)) {
					finalUser2 = u;
				}
			}
			tr = database.getTimeReports(finalUser2.getUsername(), projectGroup);

			if (tr == null) {
				html.printErrorMessage("Det finns inga registrerade tidsrapporter för denna användare");
				break;
			}

			gs = new GraphSettings(graphType, null, "Veckonummer", "Återstående timmar");
			html.printBurndownChart(tr, gs);

			break;

		}

	}
}

