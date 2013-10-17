package html;

import items.Activity;
import items.ActivityType;
import items.Command;
import items.GraphSettings;
import items.Role;
import items.TimeReport;
import items.User;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Denna klassen innehåller funktionalitet för att skriva ut all den HTML kod
 * som systemets olika delar behöver, t.ex. för listning av tidrapporter i en
 * tabell.
 * 
 */
public class HTMLWriter {

	final static public String LOGIN_USERNAME = "username";
	final static public String LOGIN_PASSWORD = "password";
	final static public String PROJECT_CHOOSER = "select";

	private PrintWriter writer;

	/**
	 * HTMLs enda konstruktor. Tar emot en PrintWriter som allt kommer att
	 * skrivas till.
	 * 
	 * @param writer
	 *            dit allting kommer att skrivas.
	 */
	public HTMLWriter(PrintWriter writer) {
		this.writer = writer;
	}

	/**
	 * 
	 Skriver ut en tidrapport. command är vilket commando som man vill utföra
	 * och role är vilken roll som den inloggade användare har i projektet där
	 * tidrapporten kommer ifrån.
	 * 
	 */
	public void printTimeReport(TimeReport timereport, Command command, Role role) {
		User user = timereport.getUser();
		int week = timereport.getWeek();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String time = sdf.format(date);

		int sdp = user.getTimeForActivity(ActivityType.SDP, week);
		int srs = user.getTimeForActivity(ActivityType.SRS, week);
		int svvs = user.getTimeForActivity(ActivityType.SVVS, week);
		int stldd = user.getTimeForActivity(ActivityType.STLDD, week);
		int svvi = user.getTimeForActivity(ActivityType.SVVI, week);
		int sddd = user.getTimeForActivity(ActivityType.SDDD, week);
		int svvr = user.getTimeForActivity(ActivityType.SVVR, week);
		int ssd = user.getTimeForActivity(ActivityType.SSD, week);
		int pfr = user.getTimeForActivity(ActivityType.PFR, week);
		int functest = user.getTimeForActivity(ActivityType.FunctionTest, week);
		int systest = user.getTimeForActivity(ActivityType.SystemTest, week);
		int regtest = user.getTimeForActivity(ActivityType.RegressionTest, week);
		int meeting = user.getTimeForActivity(ActivityType.Meeting, week);

		int sum = sdp + srs + svvs + stldd + svvi + sddd + svvr + ssd + pfr + functest + systest
				+ regtest + meeting;

		String html = "";
		html += "<form method='post' action='e_puss'>";
		html += "<input type='hidden' name='Command' value='InsertNew'>";
		html += "	<table border='1'><tbody>";
		html += "		<tr>";
		html += "			<td><b>Name:</b></td><td>" + user.getUsername() + "</td>";
		html += "			<td><b>Date:</b></td><td>" + time + "</td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<td><b>Projectgroup</b>:</td><td>" + timereport.getProjectGroup() + "</td>";
		html += "			<td><b>Week:</b></td><td><input type='text' name='week' value='"
				+ timereport.getWeek() + "' size='3'></td>";
		html += "		</tr>";
		html += "		<tr><td colspan='3' bgcolor='lightgrey'><font size='+1'><b>Del A: Total tid denna vecka (minuter)</b></font></td>";
		html += "		<td>" + sum + "</td></tr>";
		html += "		<tr><td colspan='4' bgcolor='lightgrey' nowrap=''><font size='+1'><b>Del B: Antalet minuter per aktivitet</b></font>";
		html += "		<br>(Summan av alla separata aktiviteter räknas ut automatiskt och fylls i ovan.)</td></tr>";
		html += "		<tr><th>Nummer</th><th colspan='2'>Aktivitet</th><th>Total tid</th></tr>";
		html += "		<tr>";
		html += "			<td>11</td>";
		html += "			<td colspan='2'>SDP</td>";
		html += "			<td><i><input type='text' name='SDP' value='" + sdp + "' size='3'></i></td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<td>12</td>";
		html += "			<td colspan='2'>SRS</td>";
		html += "			<td><i><input type='text' name='SRS' value='" + srs + "' size='3'></i></td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<td>13</td>";
		html += "			<td colspan='2'>SVVS</td>";
		html += "			<td><i><input type='text' name='SVVS' value='" + svvs + "' size='3'></i></td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<td>14</td>";
		html += "			<td colspan='2'>STLDD</td>";
		html += "			<td><i><input type='text' name='STLDD' value='" + stldd
				+ "' size='3'></i></td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<td>15</td>";
		html += "			<td colspan='2'>SVVI</td>";
		html += "			<td><i><input type='text' name='SVVI' value='" + svvi + "' size='3'></i></td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<td>16</td>";
		html += "			<td colspan='2'>SDDD</td>";
		html += "			<td><i><input type='text' name='SDDD' value='" + sddd + "' size='3'></i></td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<td>17</td>";
		html += "			<td colspan='2'>SVVR</td>";
		html += "			<td><i><input type='text' name='SVVR' value='" + svvr + "' size='3'></i></td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<td>18</td>";
		html += "			<td colspan='2'>SSD</td>";
		html += "			<td><i><input type='text' name='SSD' value='" + ssd + "' size='3'></i></td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<td>19</td>";
		html += "			<td colspan='2'>PFR</td>";
		html += "			<td><i><input type='text' name='PFR' value='" + pfr + "' size='3'></i></td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<td bgcolor='lightgrey' colspan='4'></td>	";
		html += "		</tr>		";
		html += "		<tr>";
		html += "			<td>21</td>";
		html += "			<td colspan='2'>Funktionstest</td>";
		html += "			<td><i><input type='text' name='Funktionstest' value='" + functest
				+ "' size='3'></i></td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<td>22</td>";
		html += "			<td colspan='2'>Systemtest</td>";
		html += "			<td><i><input type='text' name='Systemtest' value='" + systest
				+ "' size='3'></i></td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<td>23</td>";
		html += "			<td colspan='2'>Regressionstest</td>";
		html += "			<td><i><input type='text' name='Regressionstest' value='" + regtest
				+ "' size='3'></i></td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<td>30</td>";
		html += "			<td colspan='2'>Möte</td>";
		html += "			<td><i><input type='text' name='Mote' value='" + meeting
				+ "' size='3'></i></td>";
		html += "		</tr>";
		html += "		<tr><td colspan='4' bgcolor='lightgrey'><font size='+1'><b>Del C: Signatur</b></font></td></tr><tr>";
		html += "		<tr><td colspan='3'><b>Signerad av manager</b></td><td>"
				+ timereport.getSigned() + "</td></tr>";
		html += "	</tbody></table>";
		html += "	<input type='hidden' name='FormFields' value='SDP, SRS, SVVS, STLDD, SVVI, SDDD, SVVR, SSD, PFR, Funktionstest, Systemtest, Regressionstest, Mote'>";

		html += "	<input type='submit' value='Submit time report'>";
		if (role.equals(Role.Manager))
			html += "	<input type='submit' value='Sign time report'>";
		else
			html += "	<input type='submit' value='Delete time report'>";

		html += "</form>";
	}

	/**
	 * Skriver ut en lista med tidrapporter. Listan utökas med funktionalitet
	 * för borttagning, uppdatering, signering, beroende på command och role.
	 * 
	 * @param timereports
	 *            de tidrapporter som skall skrivas ut.
	 * @param command
	 *            det val man ska presentera för varje tidrapport.
	 * @param role
	 *            rollen för den användare som anropade metoden.
	 * 
	 * 
	 */
	public void printTimeReports(List<TimeReport> timereports, Command command, Role role) {
		writer.print("TEST");
	}

	/**
	 * 
	 Skriver ut en lista med användare. Listan utökas beroende på vilken typ
	 * av användare det är.
	 * 
	 * @param users
	 *            användarna som skall skrivas ut.
	 * @param role
	 *            rollen för den användare som anropade metoden.
	 * 
	 */
	public void printUsers(List<User> users, Role role, HashMap<String, Role> userRoles) {
		if (users != null && users.size() > 0) {
			writer.print("<form action=\"showmembers\" method=\"POST\">");
			writer.print("<table><tr><th>Användare</th><th>Roll</th><th></th></tr>");
			String submitButton = "";
			for (User u : users) {
				String extra = "";
				Role userRole = userRoles.get(u.getUsername());
				if(role.equals(Role.Manager) && !userRole.equals(Role.Manager) ){
					submitButton = "<input type=\"submit\" value=\"Uppdatera rollerna\" name=\"updateRoles\" />";
					extra =  "<select name=\"role_for_"+u.getUsername()+"\">";
					for(Role r : Role.values()){
						if(!r.equals(Role.Manager)){
							String selected = r.equals(userRole) ? "selected=selected" : "";
							extra += "<option value=\""+r.toString()+"\""
									+ " " + selected + ">"+r.toString()+"</option>";
						}
					}
					extra += "</select>";
							
				}
				writer.print("<tr><td>"
						+ u.getUsername()
						+ "</td><td>"
						+ userRoles.get(u.getUsername())
						+ "</td><td>"
						+ 	extra
						+ "</td></tr>");
			}
			writer.print("<tr><td colspan=\"3\" align=\"right\">"+submitButton+"</td></tr>");
			writer.print("</table>");
			writer.print("</form>");
		} else {
			printErrorMessage("Där finns inga medlemmar");
		}
	}

	/**
	 * 
	 Skriver ut en lista med val för vilken typ av graf som ska genereras.
	 * 
	 */
	public void printGraphChoice() {
	}

	/**
	 * Genererar en graf från timeReports med de inställningar som är satta i
	 * gs.
	 * 
	 * @param timeReports
	 *            lista med tidrapporter.
	 * 
	 * @param gs
	 *            inställningar för grafen.
	 * 
	 * 
	 * 
	 */
	public void printGraph(List<TimeReport> timeReports, GraphSettings gs) {
		String xAxisName = gs.getXName();
		String yAxisName = gs.getYName();

		String title = "";

		String html = "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/jquery.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/jquery.jqplot.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.barRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.categoryAxisRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.pointLabels.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.canvasTextRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.canvasAxisLabelRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.canvasAxisTickRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.dateAxisRenderer.min.js\"></script>";
		html += "<link rel=\"stylesheet\" type=\"text/css\" href=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/jquery.jqplot.min.css\" />";

		html += "<div id=\"chart\" ></div>";

		html += "<script>";
		html += "$.jqplot.config.enablePlugins = true;";

		html += "var s1 = [";

		switch (gs.getGraphType()) {
		case "userWeekTime":
			title = "Spenderad tid per vecka";
			for (int i = 0; i < timeReports.size(); i++) {

				List<Activity> activities = timeReports.get(i).getActivities();

				int totalTime = 0;

				for (Activity a : activities) {
					totalTime += a.getLength();
				}

				html += "[" + timeReports.get(i).getWeek() + "," + totalTime + "],";
			}
			html = html.substring(0, html.length() - 1);

			break;

		case "activityTime":
			title = "Spenderad tid per aktivitet";
			HashMap<ActivityType, Integer> actTimes = new HashMap<ActivityType, Integer>();

			for (int i = 0; i < timeReports.size(); i++) {

				List<Activity> activities = timeReports.get(i).getActivities();

				for (Activity a : activities) {
					if (actTimes.containsKey(a.getType())) {
						actTimes.put(a.getType(), actTimes.get(a.getType()) + a.getLength());
					} else {
						actTimes.put(a.getType(), a.getLength());
					}
				}
			}

			Set<ActivityType> types = actTimes.keySet();

			for (ActivityType t : types) {
				html += "['" + t.toString() + "'," + actTimes.get(t).toString() + "],";
			}
			html = html.substring(0, html.length() - 1);

			break;
		case "plUserTime":
			title = "Spenderad tid per gruppmedlem";
			HashMap<User, Integer> userTimes = new HashMap<User, Integer>();

			for (int i = 0; i < timeReports.size(); i++) {

				List<Activity> activities = timeReports.get(i).getActivities();

				int totalTime = 0;

				for (Activity a : activities) {
					totalTime += a.getLength();
				}

				if (userTimes.containsKey(timeReports.get(i).getUser())) {
					userTimes.put(timeReports.get(i).getUser(),
							userTimes.get(timeReports.get(i).getUser()) + totalTime);
				} else {
					userTimes.put(timeReports.get(i).getUser(), totalTime);
				}
			}

			Set<User> users = userTimes.keySet();

			for (User u : users) {
				html += "['" + u.toString() + "'," + userTimes.get(u).toString() + "],";
			}
			html = html.substring(0, html.length() - 1);

			break;
		}

		html += "];";

		html += "plot1 = $.jqplot('chart', [s1], {";

		html += "	title:'" + title + "',";

		html += "	animate: !$.jqplot.use_excanvas,";
		html += "	seriesDefaults:{";
		html += "		renderer:$.jqplot.BarRenderer,";
		html += "		pointLabels: { show: true }";
		html += "	},";
		html += "	legend: {show:false},";
		html += "	axes: {";
		html += "		xaxis: {";
		html += "			tickOptions:{";
		html += "				labelPosition: 'middle'";
		html += "			},";
		html += "			tickRenderer:$.jqplot.CanvasAxisTickRenderer,";
		html += "			labelRenderer: $.jqplot.CanvasAxisLabelRenderer,";

		html += "			label:'" + xAxisName + "',";

		html += "			labelOptions:{";
		html += "				fontFamily:'Helvetica',";
		html += "				fontSize: '14pt'";
		html += "			},";
		html += "			renderer: $.jqplot.CategoryAxisRenderer,";
		html += "		},";
		html += "		yaxis: {";
		html += "			tickOptions:{";
		html += "				labelPosition: 'middle'";
		html += "			},";
		html += "			tickRenderer:$.jqplot.CanvasAxisTickRenderer,";
		html += "			labelRenderer: $.jqplot.CanvasAxisLabelRenderer,";

		html += "			label:'" + yAxisName + "',";

		html += "			labelOptions:{";
		html += "				fontFamily:'Helvetica',";
		html += "				fontSize: '14pt'";
		html += "			},";
		html += "		}";
		html += "	},";
		html += "	highlighter: { show: false }";
		html += "});";
		html += "</script>";

		writer.print(html);
	}

	/**
	 * 
	 * Genererar en burn down chart från timeReports med de inställningar som är
	 * satta i gs.
	 * 
	 * @param timeReports
	 *            lista med tidrapporter.
	 * @param gs
	 *            inställningar för grafen.
	 * 
	 * 
	 */
	public void printBurndownChart(List<TimeReport> timeReports, GraphSettings gs) {

		String xAxisName = gs.getXName();
		String yAxisName = gs.getYName();

		String title = "";

		String html = "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/jquery.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/jquery.jqplot.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.highlighter.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.cursor.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.dateAxisRenderer.min.js\"></script>";

		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.canvasTextRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.canvasAxisLabelRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.canvasAxisTickRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.dateAxisRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/plugins/jqplot.categoryAxisRenderer.min.js\"></script>";

		html += "<link rel=\"stylesheet\" type=\"text/css\" href=\"/h/d6/w/dt09jn4/Desktop/PUSskit/dist/jquery.jqplot.min.css\" />";

		html += "<div id=\"chart\" ></div>";

		html += "<script>";

		// html +=
		// "var line=[['23-May-08', 578.55], ['20-Jun-08', 566.5], ['25-Jul-08', 480.88], ['22-Aug-08', 509.84],";
		// html +=
		// "	['26-Sep-08', 454.13], ['24-Oct-08', 379.75], ['21-Nov-08', 303], ['26-Dec-08', 308.56],";
		// html +=
		// "	['23-Jan-09', 299.14], ['20-Feb-09', 346.51], ['20-Mar-09', 325.99], ['24-Apr-09', 386.15]];";

		html += "var line = [";

		int totalTime;

		switch (gs.getGraphType()) {
		case "weekBurnDown":
			title = "Burn down chart för tid mellan specifierade veckor";
			totalTime = 0;

			Collections.sort(timeReports, new Comparator<TimeReport>() {
				@Override
				public int compare(final TimeReport object1, final TimeReport object2) {
					return object1.getWeek() - object2.getWeek();
				}
			});

			for (int i = 0; i < timeReports.size(); i++) {

				List<Activity> activities = timeReports.get(i).getActivities();

				for (Activity a : activities) {
					totalTime += a.getLength();
				}

			}

			for (int i = 0; i < timeReports.size(); i++) {
				int weekTime = 0;

				List<Activity> activities = timeReports.get(i).getActivities();

				for (Activity a : activities) {
					weekTime += a.getLength();
				}

				html += "[" + timeReports.get(i).getWeek() + "," + totalTime + "],";

				totalTime = totalTime - weekTime;
			}

			html = html.substring(0, html.length() - 1);

			break;
		case "activityBurnDown":

			title = "Burn down chart för specifierad aktivitet";
			totalTime = 0;
			for (int i = 0; i < timeReports.size(); i++) {
				List<Activity> activities = timeReports.get(i).getActivities();

				Activity act = activities.get(gs.getYType());

				totalTime += act.getLength();
			}

			for (int i = 0; i < timeReports.size(); i++) {
				List<Activity> activities = timeReports.get(i).getActivities();

				Activity act = activities.get(gs.getYType());

				html += "[" + timeReports.get(i).getWeek() + "," + totalTime + "],";

				totalTime = totalTime - act.getLength();
			}

			html = html.substring(0, html.length() - 1);

			break;
		case "userBurnDown":

			title = "Burn down chart för specifierad användare";
			for (int i = 0; i < timeReports.size(); i++) {

			}
			break;
		}

		html += "];";

		html += "var plot1 = $.jqplot('chart', [line], {";
		html += "	animate: !$.jqplot.use_excanvas,";

		html += "	title:'" + title + "',";

		html += "	axes:{";
		html += "		xaxis:{";
		html += "			renderer:$.jqplot.DateAxisRenderer,";
		html += "			tickOptions:{";
		html += "				labelPosition: 'middle'";
		html += "			},";
		html += "			tickRenderer:$.jqplot.CanvasAxisTickRenderer,";
		html += "			labelRenderer: $.jqplot.CanvasAxisLabelRenderer,";

		html += "				label:'" + xAxisName + "',";

		html += "			labelOptions:{";
		html += "				fontFamily:'Helvetica',";
		html += "				fontSize: '14pt'";
		html += "			},";
		html += "			renderer: $.jqplot.CategoryAxisRenderer,";
		html += "		},";
		html += "		yaxis:{";
		html += "			tickOptions:{";
		html += "				labelPosition: 'middle'";
		html += "			},";
		html += "			tickRenderer:$.jqplot.CanvasAxisTickRenderer,";
		html += "			labelRenderer: $.jqplot.CanvasAxisLabelRenderer,";

		html += "            	label:'" + yAxisName + "',";

		html += "			labelOptions:{";
		html += "				fontFamily:'Helvetica',";
		html += "				fontSize: '14pt'";
		html += "			},";
		html += "		}";
		html += "	},";
		html += "	highlighter: {";
		html += "		show: true,";
		html += "		sizeAdjust: 7.5";
		html += "	},";
		html += "	cursor: {";
		html += "		show: false";
		html += "	}";
		html += "});";
		html += "</script>";

		writer.print(html);
	}

	/**
	 * 
	 Skriver ut sidhuvudet.
	 * 
	 * @param user
	 *            den inloggade användaren.
	 * 
	 */
	public void printHead(User user) {
		writer.print("<html><head><meta charset=\"latin1\"><title>E-PUSS 1301</title></head><body><h1>E-PUSS 1301</h1>");
		if (user != null) {
			writer.print("<p>Hejsan " + user.getUsername() + "! Tryck ");
			printLink("login", "här");
			writer.print(" om du vill logga ut.</p>");
		}
	}

	/**
	 * 
	 Skriver ut sidfoten
	 * 
	 */
	public void printFoot() {
		writer.print("</body></html>");
	}

	/**
	 * 
	 Skriver ut en dropdown där användaren får välja projektgrupp.
	 * 
	 * @param i
	 *            är nuvarande projektgrupp.
	 * @param projects
	 *            är samtliga tillgängliga projekt.
	 * 
	 */
	public void printProjectChooser(int n, List<String> projects) {
		n = n < 0 ? 0 : n;
		writer.print("<form method=\"POST\" action=\"mainpage\" name=\"chooser\" id = \"chooser\">");
		writer.print("<select name=\"" + PROJECT_CHOOSER + "\" id=\"" + PROJECT_CHOOSER
				+ "\"  onchange=\"this.form.submit()\" >");
		for (int i = 0; i < projects.size(); i++) {
			writer.print("<option value = " + projects.get(i));
			if (i == n) {
				writer.print(" selected");
			}
			writer.print(" > " + projects.get(i));
			writer.print("</option>");
		}
		writer.print("</select>");
		writer.print("</form>");
	}

	/**
	 * 
	 Skriver ut ett meddelande för att meddela rätt resultat.
	 * 
	 * @param message
	 *            det meddelande som skall skrivas ut.
	 * 
	 */
	public void printSuccessMessage(String message) {
		writer.print("<p style=\"color:green;\">" + message + "</p>");
	}

	/**
	 * 
	 @param users
	 *            lista över användare i systemet. Skriver ut en lista över alla
	 *            användare i systemet.
	 * 
	 */
	public void printAdminUserList(List<User> users) {
		if (users != null && users.size() > 0) {
			writer.print("<table><tr><th>Användare</th><th>Passwords</th><th></th></tr>");
			for (User u : users) {
				writer.print("<tr><td>" + u.getUsername() + "</td><td>" + u.getPassword()
						+ "</td><td>"
						+ "<a href=\"/ProjectPUSS/administration?action=deleteUser&username="
						+ u.getUsername() + "\">Ta bort</a></td></tr>");
			}
			writer.print("</table>");
		}
	}

	/**
	 * 
	 Skriver ut ett formulär för att lägga till en användare i systemet.
	 * 
	 */
	public void printAddUserForm() {
		writer.print("<form method=\"POST\" action=\"/ProjectPUSS/administration?action=createUser\">"
				+ "<label>Användarnamn</label><input name=\"username\" type=\"text\" />"
				+ "<input type=\"submit\" value=\"Skapa\" />" + "</form>");
	}

	/**
	 * 
	 @param groups
	 *            lista med grupper. Skriver ut listan över grupper.
	 * 
	 */
	public void printProjectGroups(List<String> groups) {
		if (groups != null && groups.size() > 0) {
			writer.print("<table><tr><th>Projektgrupper</th><th></th></tr>");
			for (String s : groups) {
				writer.print("<tr><td>" + "<a href=\"projectoverview?project=" + s + "\">" + s
						+ "</a>"
						+ "</td><td><a href=\"projectadmin?action=removeProjectGroup&projectName="
						+ s + "\">Ta bort</a></td></tr>");
			}

			writer.print("</table>");
		}
	}

	/**
	 * 
	 Skriver ut ett formulär för att lägga till en projektgrupp i systemet.
	 * 
	 */
	public void printAddProjectGroupForm() {
		writer.print("<form method=\"POST\" action=\"/ProjectPUSS/projectadmin?action=createProjectGroup\">"
				+ "<label>Projekgruppnamn</label><input name=\"projectname\" type=\"text\" />"
				+ "<input type=\"submit\" value=\"Skapa\" />" + "</form>");
	}

	/**
	 * Skriver ut en lista över en projektgrupps medlemmar.
	 * 
	 * @param users
	 *            lista över medlemmar i en projektgrupp.
	 * 
	 * @param projectManagers
	 *            lista över projektledare i en projektgrupp.
	 * 
	 */
	public void printProjectGroupMembers(List<User> users, List<User> projectManagers,
			String projectName) {
		if (!(users.size() == 0 && projectManagers.size() == 0)) {
			writer.print("<table><tr><th>Projektledare</th><th></th><th></th></tr>");
			for (User u : projectManagers) {
				writer.print("<tr><td>" + u.getUsername()
						+ "</td><td><a href=\"projectoverview?action=demoteUser&project="
						+ projectName + "&username=" + u.getUsername()
						+ "\">Gör till användare</a></td><td>"
						+ "<a href=\"projectoverview?action=deleteUser&project=" + projectName
						+ "&username=" + u.getUsername() + "\">Ta bort</a>" + "</td></tr>");
			}
			writer.print("<tr><th>Användare</th><th></th><th></th></tr>");
			for (User u : users) {
				if (!projectManagers.contains(u)) {
					writer.print("<tr><td>" + u.getUsername()
							+ "</td><td><a href=\"projectoverview?action=promoteUser&project="
							+ projectName + "&username=" + u.getUsername()
							+ "\">Gör till projektledare</a></td><td>"
							+ "<a href=\"projectoverview?action=deleteUser&project=" + projectName
							+ "&username=" + u.getUsername() + "\">Ta bort</a>" + "</td></tr>");
				}
			}
			writer.print("</table>");
		}
	}

	/**
	 * 
	 Skriver ut ett formulär för att lägga till en användare i en grupp.
	 * 
	 * @param projectName
	 * 
	 */
	public void printAddUserToProjectGroupForm(String projectName) {
		writer.print("<form method=\"POST\" action=\"/ProjectPUSS/projectoverview?action=addUser&project="
				+ projectName
				+ "\">"
				+ "<label>Användarnamn</label><input name=\"username\" type=\"text\" />"
				+ "<input type=\"submit\" value=\"Lägg till\" />" + "</form>");
	}

	/**
	 * 
	 @param message
	 *            felmeddelande. Skriver ut ett felmeddelande.
	 * 
	 */
	public void printErrorMessage(String message) {
		writer.print("<p style=\"color:red;\">" + message + "</p>");
	}

	/**
	 * Generates a form for login.
	 * 
	 * @return HTML code for the form
	 */
	public void printLoginRequestForm() {
		writer.println("<p>Användarnamn och Lösenord, tack!</p>");
		writer.println("<form method=\"POST\" action=\"login\">");
		writer.println("<p>Namn: <input type=\"text\" name = " + LOGIN_USERNAME + "></p>");
		writer.println("<p>Lösenord: <input type=\"password\" name = " + LOGIN_PASSWORD + "></p>");
		writer.println("<input type=\"submit\" value=\"Skicka\">");
		writer.println("</form>");

	}

	/**
	 * Generates a link.
	 * 
	 * @param url
	 *            the url to use for the link
	 * @param text
	 *            the text to display
	 * 
	 * @return HTML code for the link
	 */
	public void printLink(String url, String text) {
		writer.println("<a href=\"" + url + "\">" + text + "</a>");
	}

}
