package servlets;

import html.HTMLWriter;
import items.ActivityType;
import items.GraphSettings;
import items.Role;
import items.TimeReport;
import items.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Timer;

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

	public static boolean done = true;

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute(ServletBase.USER);
		String projectGroup = (String) session
				.getAttribute(ServletBase.PROJECT);
		Role userRole = database.getRole(user.getUsername(), projectGroup);

		if (done) {
			session.setAttribute("graphChoice", "inget val");
			session.setAttribute("startWeekChoice", "inget val");
			session.setAttribute("stopWeekChoice", "inget val");
			session.setAttribute("activityChoice", "inget val");
			session.setAttribute("userChoice", "inget val");
			html.printGraphChoice(userRole);
			if(userRole.equals(Role.Manager)){
				html.printOtherStatsChoice();
			}
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
		
		if(request.getParameter("moreStats")  != null){
			tr = database.getAllTimeReports(projectGroup);
			firstWeek = getWeeks(tr)[0];
			lastWeek = getWeeks(tr)[1];
			html.printStatPanel(database.getUsersInProject(projectGroup),firstWeek,lastWeek);
			
			Object sum = session.getAttribute("moreStats");
			
			if (sum != null){
				html.printSuccessMessage("Ditt resultat: " + (int) sum);
			}
			
			session.setAttribute("moreStats", null);
		} else {
		
		switch (graphType) {
		case "userWeekTime":

			tr = database.getTimeReports(user.getUsername(), projectGroup);
			if (tr == null || tr.isEmpty()) {
				html.printErrorMessage("Det finns inga registrerade tidsrapporter för denna användare");
				done = true;
				break;
			}
			
			firstWeek = getWeeks(tr)[0];
			lastWeek = getWeeks(tr)[1];

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
					for (int i = 0; i < tr.size(); i++) {
						if (tr.get(i).getWeek() <= startWeek) {
							startIndex = tr.indexOf(tr.get(i));
						}
						if (tr.get(i).getWeek() <= stopWeek) {
							stopIndex = tr.indexOf(tr.get(i));
						}
					}

					if (startWeek == stopWeek
							&& tr.get(stopIndex).getWeek() < stopWeek) {
						html.printErrorMessage("Det finns ingen registrerad tidsrapport för denna vecka");
					} else {

						html.printSuccessMessage("Startvecka: " + start
								+ ", Slutvecka: " + stop);
						gs = new GraphSettings(graphType, null, "Veckonummer",
								"Arbetade minuter");
						List<TimeReport> finalTimeReports = tr.subList(
								startIndex, stopIndex + 1);
						html.printGraph(finalTimeReports, gs);
					}
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

			gs = new GraphSettings(graphType, null, "Aktivitet",
					"Arbetade minuter");
			html.printGraph(tr, gs);
			done = true;

			break;

		case "plUserTime":

			users = database.getUsersInProject(projectGroup);
			if (users == null || users.isEmpty()) {
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

			gs = new GraphSettings(graphType, null, "Användare",
					"Arbetade minuter");
			html.printGraph(tr, gs);
			done = true;
			break;

		case "weekBurnDown":

			users = database.getUsersInProject(projectGroup);
			if (users == null || users.isEmpty()) {
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
				public int compare(final TimeReport object1,
						final TimeReport object2) {
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
					for (int i = 0; i < tr.size(); i++) {
						if (tr.get(i).getWeek() <= startWeek) {
							startIndex = tr.indexOf(tr.get(i));
						}
						if (tr.get(i).getWeek() <= stopWeek) {
							stopIndex = tr.indexOf(tr.get(i));
						}
					}

					html.printSuccessMessage("Startvecka: " + start
							+ ", Slutvecka: " + stop);
					gs = new GraphSettings(graphType, null, "Veckonummer",
							"Arbetade minuter");
					List<TimeReport> finalTimeReports = tr.subList(startIndex,
							stopIndex + 1);
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
			if (users == null || users.isEmpty()) {
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
				gs = new GraphSettings(graphType, activityChoice,
						"Veckonummer", "Arbetade minuter");
				html.printBurndownChart(tr, gs);
				done = true;
			} else {
				html.printActivityChoice();

			}

			break;

		case "userBurnDown":

			users = database.getUsersInProject(projectGroup);
			if (users == null || users.isEmpty()) {
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

				gs = new GraphSettings(graphType, null, "Veckonummer",
						"Arbetade minuter");
				html.printBurndownChart(tr, gs);
				done = true;
			} else {
				html.printUserChoice(users);
			}

			break;
			
			}
		}
	}

	private int[] getWeeks(List<TimeReport> tr) {
		Collections.sort(tr, new Comparator<TimeReport>() {
			@Override
			public int compare(final TimeReport object1,
					final TimeReport object2) {
				return object1.getWeek() - object2.getWeek();
			}
		});
		if(tr==null || tr.size()<1){
			return new int[]{0,0};
		}
		int firstWeek = tr.get(0).getWeek();
		if (firstWeek < 1) {
			firstWeek = 1;
		}
		int lastWeek = tr.get(tr.size() - 1).getWeek();
		if (lastWeek < 1) {
			lastWeek = 1;
		}
		return new int[]{firstWeek,lastWeek};
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();

		String graphChoice = (String) request.getParameter("graphChooser");
		String startWeekChoice = (String) request.getParameter("startWeek");
		String stopWeekChoice = (String) request.getParameter("stopWeek");
		String activityChoice = (String) request.getParameter("activityChoose");
		String userChoice = (String) request.getParameter("userChoose");
		String moreStats = (String) request.getParameter("moreStats");

		if(moreStats != null){
			List<String> users = new ArrayList<>();
			List<Role> roles = new ArrayList<>();
			List<ActivityType> activities = new ArrayList<>();
			int startWeek = 0;
			int endWeek = 0;
			
			Map<String,String[]> map = request.getParameterMap();
			
			for(String para : map.keySet()){
				if(para.startsWith("user_")){
					users.add(para.split("_")[1]);
				} else if (para.startsWith("role_")){
					roles.add(Role.valueOf(para.split("_")[1]));
				}else if (para.startsWith("activity_")){
					activities.add(ActivityType.valueOf(para.split("_")[1]));
				} else if(para.equals("startweek")){
					startWeek = Integer.parseInt(map.get(para)[0]);
				} else if (para.equals("endweek")){
					endWeek = Integer.parseInt(map.get(para)[0]);
				}
			}
			
			session.setAttribute("moreStats", database.getStatistics((String) session.getAttribute(PROJECT), users, roles, activities, startWeek, endWeek));
		}
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

