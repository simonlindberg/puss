package html;

import items.Activity;
import items.ActivityType;
import items.Command;
import items.GraphSettings;
import items.Role;
import items.TimeReport;
import items.User;

import java.io.PrintWriter;
import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
	final static public String LIST_COMMAND = "command";
	final static public String ID = "id";
	
	final static public String SDP = "sdp";
	final static public String SRS = "srs";
	final static public String SVVS = "svvs";
	final static public String STLDD = "stldd";
	final static public String SVVI = "svvi";
	final static public String SDDD = "sddd";
	final static public String SVVR = "svvr";
	final static public String SSD = "ssd";
	final static public String PFR = "pfr";
	final static public String FUNCTION_TEST = "functest";
	final static public String SYSTEM_TEST= "systest";
	final static public String REG_TEST = "regtest";
	final static public String MEETING = "meeting";
	final static public String WEEK = "week";

	final static public String SELECT_USERROLE = "role_for_";
	final static public String SUBMIT_UPDATE_ROLE = "updateRoles";
	
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
		Date date = new Date(System.currentTimeMillis());
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
		
		String cmd = "";
		if (command.equals(Command.show))
			cmd = " readonly ";

		int sum = sdp + srs + svvs + stldd + svvi + sddd + svvr + ssd + pfr + functest + systest
				+ regtest + meeting;

		writer.print("<form method='post' action='timereport?" + HTMLWriter.LIST_COMMAND + "="
				+ command.toString() + "&id=" + timereport.getID() + "'>");
		writer.print("<table border='1'><tbody>");
		writer.print("<tr>");
		writer.print("<td><b>Name:</b></td><td>" + user.getUsername() + "</td>");
		writer.print("<td><b>Date:</b></td><td>" + time + "</td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td><b>Projectgroup</b>:</td><td>" + timereport.getProjectGroup() + "</td>");
		writer.print("<td><b>Week:</b></td><td><input" + cmd + " type='text' name='" + WEEK
				+ "' value='" + timereport.getWeek() + "' size='3'></td>");
		writer.print("</tr>");
		writer.print("<tr><td colspan='3' bgcolor='lightgrey'><font size='+1'><b>Del A: Total tid denna vecka (minuter)</b></font></td>");
		writer.print("<td>" + sum + "</td></tr>");
		writer.print("<tr><td colspan='4' bgcolor='lightgrey' nowrap=''><font size='+1'><b>Del B: Antalet minuter per aktivitet</b></font>");
		writer.print("<br>(Summan av alla separata aktiviteter räknas ut automatiskt och fylls i ovan.)</td></tr>");
		writer.print("<tr><th>Nummer</th><th colspan='2'>Aktivitet</th><th>Total tid</th></tr>");
		writer.print("<tr>");
		writer.print("<td>11</td>");
		writer.print("<td colspan='2'>SDP</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SDP + "' value='" + sdp
				+ "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>12</td>");
		writer.print("<td colspan='2'>SRS</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SRS + "' value='" + srs
				+ "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>13</td>");
		writer.print("<td colspan='2'>SVVS</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVS + "' value='" + svvs
				+ "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>14</td>");
		writer.print("<td colspan='2'>STLDD</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + STLDD + "' value='" + stldd
				+ "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>15</td>");
		writer.print("<td colspan='2'>SVVI</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVI + "' value='" + svvi
				+ "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>16</td>");
		writer.print("<td colspan='2'>SDDD</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SDDD + "' value='" + sddd
				+ "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>17</td>");
		writer.print("<td colspan='2'>SVVR</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVR + "' value='" + svvr
				+ "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>18</td>");
		writer.print("<td colspan='2'>SSD</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SSD + "' value='" + ssd
				+ "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>19</td>");
		writer.print("<td colspan='2'>PFR</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + PFR + "' value='" + pfr
				+ "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td bgcolor='lightgrey' colspan='4'></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>21</td>");
		writer.print("<td colspan='2'>Funktionstest</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + FUNCTION_TEST + "' value='"
				+ functest + "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>22</td>");
		writer.print("<td colspan='2'>Systemtest</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SYSTEM_TEST + "' value='"
				+ systest + "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>23</td>");
		writer.print("<td colspan='2'>Regressionstest</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + REG_TEST + "' value='"
				+ regtest + "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>30</td>");
		writer.print("<td colspan='2'>Möte</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + MEETING + "' value='"
				+ meeting + "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr><td colspan='4' bgcolor='lightgrey'><font size='+1'><b>Del C: Signatur</b></font></td></tr><tr>");
		writer.print("<tr><td colspan='3'><b>Signerad av manager</b></td><td>"
				+ (timereport.getSigned() ? "JA" : "NEJ") + "</td></tr>");
		writer.print("</tbody></table>");

		if (!command.equals(Command.show)) {
			writer.print("<input type='submit' name='submitreport' value='Spara tidrapport'>");
			if (command.equals(Command.update))
				writer.print("<input type='submit' name='deletereport' value='Ta bort tidrapport'>");
		}

		if (role.equals(Role.Manager))
			writer.print("<input type='submit' name='signreport' value='"
					+ (timereport.getSigned() ? "Avsignera " : "Signera") + " tidrapport'>");
		
		writer.print("</form>");

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
		if(timereports.size() == 0){
			printErrorMessage("Inga tidrapporter! Gå tillbaka till <a href=\"mainpage\">huvudsidan</a> och skapa en först.");
		} else {
			if (command.equals(Command.sign)) {
				writer.print("<form method=\"POST\" action=\"listreports\">");
			} else {
				writer.print("<form action=\"timereport\">");
			}
			writer.print("<input type=\"hidden\" value=\"" + command.toString()+ "\" name=\"" + LIST_COMMAND + "\">");
			writer.print("<table border=\"1\">");
			writer.print("<thead><tr><th>!</th><th>Användare</th><th>Senast uppdaterad</th><th>Vecka</th><th>Total tid</th><th>Signerad</th></tr></thead>");
			writer.print("<tbody>");
			
			for (TimeReport tr : timereports) {
				writer.print("<tr>");
				writer.print("<td><input type=\"radio\" name=\""+ ID +"\" value=" + tr.getID() + ">" + "</td>");
				writer.print("<td>" +tr.getUser().getUsername()+ "</td>");
				writer.print("<td>" +tr.getDate() + "</td>");
				writer.print("<td>" + tr.getWeek()+ "</td>");
				writer.print("<td>" + sum(tr) + "</td>");
				writer.print("<td>" + (tr.getSigned() ? "JA" : "NEJ") + "</td>");
				writer.print("</tr>");
			}
			
			writer.print("</tbody>");
			writer.print("</table>");

			writer.print("<input type=\"submit\" value=\"" + submitName(command) + "\">");

			writer.print("</form>");
		}
	}
	
	private String submitName(Command c){
		switch (c) {
		case delete:
			return "Ta bort";
		case show:
			return "Visa";
		case sign:
			return "Signera/Avsignera";
		case update:
			return "Uppdatera";
		default:
			return "Kör";
		}
	}
	
	private int sum(TimeReport tr){
		int sum = 0;
		for(Activity a: tr.getActivities())
			sum += a.getLength();
		return sum;
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
					submitButton = "<input type=\"submit\" value=\"Uppdatera rollerna\" name=\""+HTMLWriter.SUBMIT_UPDATE_ROLE+"\" />";
					extra =  "<select name=\"" +HTMLWriter.SELECT_USERROLE +u.getUsername()+"\">";
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
		if(user != null){
			writer.print("<p>Hejsan <b>" + user.getUsername()+ "</b>! Tryck ");
			printLink("login", "här");
			writer.print(" om du vill logga ut.</p>");
			writer.print("<p>");
			printLink("mainpage", "Startsidan");
			writer.print("</p>");
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
