package servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import items.Activity;
import items.ActivityType;
import items.GraphSettings;
import items.Role;
import items.TimeReport;
import items.User;
import html.HTMLWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Denna klass bygger ut ServletBase och sköter generering av grafer och diagram
 * som visar statistik, se (Software Requirements Specifikation (dok.nr:
 * PUSS134102, ver:1.). Klassen sköter generering av statistik både för vanliga
 * användare och projektledare.
 * 
 */
@WebServlet("/statistics")
public class Statistics extends ServletBase {

	private boolean done = true;

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute(ServletBase.USER);
		String projectGroup = (String) session.getAttribute(ServletBase.PROJECT);
		Role userRole = database.getRole(user.getUsername(), projectGroup);

		if (done) {
			session.setAttribute("graphChoice", "inget val");
			session.setAttribute("startWeekChoice", "inget val");
			session.setAttribute("stopWeekChoice", "inget val");
			session.setAttribute("activityChoice", "inget val");
			session.setAttribute("userChoice", "inget val");
			html.printGraphChoice(userRole);
		}

		String graphType = (String) session.getAttribute("graphChoice");

		if (graphType == null) {
			graphType = "inget val";
			done = true;
		} else {
			done = false;
		}

		GraphSettings gs = null;
		List<TimeReport> tr = null;
		String start = null;
		String stop = null;
		List<User> users = null;
		int firstWeek = 0;
		int lastWeek = 0;

		switch (graphType) {
		case "userWeekTime":

			tr = database.getTimeReports(user.getUsername(), projectGroup);
			if (tr == null || tr.isEmpty()) {
				html.printErrorMessage("Det finns inga registrerade tidsrapporter för denna användare");
				done = true;
				break;
			}

			Collections.sort(tr, new Comparator<TimeReport>() {
				@Override
				public int compare(final TimeReport object1, final TimeReport object2) {
					return object1.getWeek() - object2.getWeek();
				}
			});

			firstWeek = tr.get(0).getWeek();
			if (firstWeek < 1) {
				firstWeek = 1;
			}
			lastWeek = tr.get(tr.size() - 1).getWeek();
			if (lastWeek < 1) {
				lastWeek = 1;
			}

			start = (String) session.getAttribute("startWeekChoice");
			stop = (String) session.getAttribute("stopWeekChoice");

			if (!start.equals("inget val") && !stop.equals("inget val")) {

				int startWeek = Integer.parseInt(start);
				int stopWeek = Integer.parseInt(stop);

				if (stopWeek < startWeek) {
					html.printErrorMessage("Slutveckan måste vara större än eller lika med startveckan");
				} else {
					int startIndex = 0;
					int stopIndex = 0;
					for (int i = 0; i < tr.size() - 1; i++) {
						if (tr.get(i).getWeek() == startWeek || tr.get(i).getWeek() < startWeek) {
							startIndex = tr.indexOf(tr.get(i));
						}
						if (tr.get(i).getWeek() == stopWeek || tr.get(i).getWeek() < stopWeek) {
							stopIndex = tr.indexOf(tr.get(i + 1));
						}
					}
					html.printSuccessMessage("Startvecka: " + start + ", Slutvecka: " + stop);
					gs = new GraphSettings(graphType, null, "Veckonummer", "Arbetade minuter");
					List<TimeReport> finalTimeReports = tr.subList(startIndex, stopIndex);
					html.printGraph(finalTimeReports, gs);
				}
				done = true;
			} else {
				html.printWeekChoice(firstWeek, lastWeek);
				if (!start.equals("inget val")) {
					html.printSuccessMessage("Startvecka: " + start);
				}
				if (!stop.equals("inget val")) {
					html.printSuccessMessage("Slutvecka: " + stop);
				}
			}
			break;

		case "activityTime":

			tr = database.getTimeReports(user.getUsername(), projectGroup);
			if (tr == null || tr.isEmpty()) {
				html.printErrorMessage("Det finns inga registrerade tidsrapporter för denna användare");
				break;
			}

			gs = new GraphSettings(graphType, null, "Aktivitet", "Arbetade minuter");
			html.printGraph(tr, gs);
			done = true;

			break;

		case "plUserTime":

			users = database.getUsersInProject(projectGroup);
			if(users == null || users.isEmpty()){
				html.printErrorMessage("Det finns inga registrerade användare i projektet");
				break;
			}
			
			tr = new ArrayList<TimeReport>();

			for (User u : users) {
				tr.addAll(database.getTimeReports(u.getUsername(), projectGroup));

			}

			if (tr == null || tr.isEmpty()) {
				html.printErrorMessage("Det finns inga registrerade tidsrapporter för användarna i projektgruppen.");
				break;
			}

			gs = new GraphSettings(graphType, null, "Användare", "Arbetade minuter");
			html.printGraph(tr, gs);
			done = true;
			break;

		case "weekBurnDown":

			users = database.getUsersInProject(projectGroup);
			if(users == null || users.isEmpty()){
				html.printErrorMessage("Det finns inga registrerade användare i projektet");
				break;
			}
			
			tr = new ArrayList<TimeReport>();

			for (User u : users) {
				tr.addAll(database.getTimeReports(u.getUsername(), projectGroup));

			}

			if (tr == null || tr.isEmpty()) {
				html.printErrorMessage("Det finns inga registrerade tidsrapporter för denna projektgrupp");
				break;
			}

			Collections.sort(tr, new Comparator<TimeReport>() {
				@Override
				public int compare(final TimeReport object1, final TimeReport object2) {
					return object1.getWeek() - object2.getWeek();
				}
			});

			firstWeek = tr.get(0).getWeek();
			if (firstWeek < 1) {
				firstWeek = 1;
			}
			lastWeek = tr.get(tr.size() - 1).getWeek();
			if (lastWeek < 1) {
				lastWeek = 1;
			}

			start = (String) session.getAttribute("startWeekChoice");
			stop = (String) session.getAttribute("stopWeekChoice");

			if (!start.equals("inget val") && !stop.equals("inget val")) {

				int startWeek = Integer.parseInt(start);
				int stopWeek = Integer.parseInt(stop);

				if (stopWeek < startWeek) {
					html.printErrorMessage("Slutveckan måste vara större än eller lika startveckan");
				} else {
					int startIndex = 0;
					int stopIndex = 0;
					for (int i = 0; i < tr.size() - 1; i++) {
						if (tr.get(i).getWeek() == startWeek || tr.get(i).getWeek() < startWeek) {
							startIndex = tr.indexOf(tr.get(i));
						}
						if (tr.get(i).getWeek() == stopWeek || tr.get(i).getWeek() < stopWeek) {
							stopIndex = tr.indexOf(tr.get(i + 1));
						}
					}
					html.printSuccessMessage("Startvecka: " + start + ", Slutvecka: " + stop);
					gs = new GraphSettings(graphType, null, "Veckonummer", "Arbetade minuter");
					List<TimeReport> finalTimeReports = tr.subList(startIndex, stopIndex);
					html.printBurndownChart(finalTimeReports, gs);
				}
				done = true;
			} else {
				html.printWeekChoice(firstWeek, lastWeek);
				if (!start.equals("inget val")) {
					html.printSuccessMessage("Startvecka: " + start);
				}
				if (!stop.equals("inget val")) {
					html.printSuccessMessage("Slutvecka: " + stop);
				}

			}

			break;

		case "activityBurnDown":

			users = database.getUsersInProject(projectGroup);
			if(users == null || users.isEmpty()){
				html.printErrorMessage("Det finns inga registrerade användare i projektet");
				break;
			}
			
			tr = new ArrayList<TimeReport>();

			for (User u : users) {
				tr.addAll(database.getTimeReports(u.getUsername(), projectGroup));

			}

			if (tr == null || tr.isEmpty()) {
				html.printErrorMessage("Det finns inga registrerade tidsrapporter för denna projektgrupp");
				break;
			}

			String actChoice = (String) session.getAttribute("activityChoice");

			if (!actChoice.equals("inget val")) {
				html.printSuccessMessage("Vald aktivitet: " + actChoice);
				ActivityType activityChoice = ActivityType.valueOf(actChoice);
				gs = new GraphSettings(graphType, activityChoice, "Veckonummer", "Arbetade minuter");
				html.printBurndownChart(tr, gs);
				done = true;
			} else {
				html.printActivityChoice();

			}

			break;

		case "userBurnDown":

			users = database.getUsersInProject(projectGroup);
			if(users == null || users.isEmpty()){
				html.printErrorMessage("Det finns inga registrerade användare i projektet");
				break;
			}

			String userChoice = (String) session.getAttribute("userChoice");
			
			if (!userChoice.equals("inget val")) {
				html.printSuccessMessage("Vald användare: " + userChoice);
				tr = database.getTimeReports(userChoice, projectGroup);

				if (tr == null || tr.isEmpty()) {
					html.printErrorMessage("Det finns inga registrerade tidsrapporter för denna användare");
					done = true;
					break;
				}
				
				gs = new GraphSettings(graphType, null, "Veckonummer", "Arbetade minuter");
				html.printBurndownChart(tr, gs);
				done = true;
			} else {
				html.printUserChoice(users);
			}

			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpSession session = request.getSession();

		String graphChoice = (String) request.getParameter("graphChooser");
		String startWeekChoice = (String) request.getParameter("startWeek");
		String stopWeekChoice = (String) request.getParameter("stopWeek");
		String activityChoice = (String) request.getParameter("activityChoose");
		String userChoice = (String) request.getParameter("userChoose");

		if (graphChoice != null) {
			session.setAttribute("graphChoice", graphChoice);
		}
		if (startWeekChoice != null) {
			session.setAttribute("startWeekChoice", startWeekChoice);
		}
		if (stopWeekChoice != null) {
			session.setAttribute("stopWeekChoice", stopWeekChoice);
		}
		if (activityChoice != null) {
			session.setAttribute("activityChoice", activityChoice);
		}
		if (userChoice != null) {
			session.setAttribute("userChoice", userChoice);
		}

		doGet(request, response);
	}
}

