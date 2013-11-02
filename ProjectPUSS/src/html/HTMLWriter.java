package html;

import items.Activity;
import items.ActivitySubType;
import items.ActivityType;
import items.Command;
import items.GraphSettings;
import items.Role;
import items.TimeReport;
import items.User;

import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import database.Database;

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
	
	final static public String SDP_F = "sdp_f";
	final static public String SDP_I = "sdp_i";
	final static public String SDP_O = "sdp_o";
	final static public String SDP_U = "sdp_u";
	
	final static public String SRS_F = "srs_f";
	final static public String SRS_I = "srs_i";
	final static public String SRS_O = "srs_o";
	final static public String SRS_U = "srs_u";
	
	final static public String SVVS_F = "svvs_f";
	final static public String SVVS_I = "svvs_i";
	final static public String SVVS_O = "svvs_o";
	final static public String SVVS_U = "svvs_u";
	
	final static public String STLDD_F = "stldd_f";
	final static public String STLDD_I = "stldd_i";
	final static public String STLDD_O = "stldd_o";
	final static public String STLDD_U = "stldd_u";
	
	final static public String SVVI_F = "svvi_f";
	final static public String SVVI_I = "svvi_i";
	final static public String SVVI_O = "svvi_o";
	final static public String SVVI_U = "svvi_u";
	
	final static public String SDDD_F = "sddd_f";
	final static public String SDDD_I = "sddd_i";
	final static public String SDDD_O = "sddd_o";
	final static public String SDDD_U = "sddd_u";
	
	final static public String SVVR_F = "svvr_f";
	final static public String SVVR_I = "svvr_i";
	final static public String SVVR_O = "svvr_o";
	final static public String SVVR_U = "svvr_u";
	
	final static public String SSD_F = "ssd_f";
	final static public String SSD_I = "ssd_i";
	final static public String SSD_O = "ssd_o";
	final static public String SSD_U = "ssd_u";
	
	final static public String PFR_F = "pfr_f";
	final static public String PFR_I = "pfr_i";
	final static public String PFR_O = "pfr_o";
	final static public String PFR_U = "pfr_u";
	
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
		List<Activity> activities = timereport.getActivities();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		String time = sdf.format(date);
		
		int sdp_f = getFromActivityList(activities, ActivityType.SDP, ActivitySubType.F);
		int sdp_i = getFromActivityList(activities, ActivityType.SDP, ActivitySubType.I);
		int sdp_o = getFromActivityList(activities, ActivityType.SDP, ActivitySubType.O);
		int sdp_u = getFromActivityList(activities, ActivityType.SDP, ActivitySubType.U);
		
		int srs_f = getFromActivityList(activities, ActivityType.SRS, ActivitySubType.F);
		int srs_i = getFromActivityList(activities, ActivityType.SRS, ActivitySubType.I);
		int srs_o = getFromActivityList(activities, ActivityType.SRS, ActivitySubType.O);
		int srs_u = getFromActivityList(activities, ActivityType.SRS, ActivitySubType.U);
		
		int svvs_f = getFromActivityList(activities, ActivityType.SVVS, ActivitySubType.F);
		int svvs_i = getFromActivityList(activities, ActivityType.SVVS, ActivitySubType.I);
		int svvs_o = getFromActivityList(activities, ActivityType.SVVS, ActivitySubType.O);
		int svvs_u = getFromActivityList(activities, ActivityType.SVVS, ActivitySubType.U);
		
		int stldd_f = getFromActivityList(activities, ActivityType.STLDD, ActivitySubType.F);
		int stldd_i = getFromActivityList(activities, ActivityType.STLDD, ActivitySubType.I);
		int stldd_o = getFromActivityList(activities, ActivityType.STLDD, ActivitySubType.O);
		int stldd_u = getFromActivityList(activities, ActivityType.STLDD, ActivitySubType.U);
		
		int svvi_f = getFromActivityList(activities, ActivityType.SVVI, ActivitySubType.F);
		int svvi_i = getFromActivityList(activities, ActivityType.SVVI, ActivitySubType.I);
		int svvi_o = getFromActivityList(activities, ActivityType.SVVI, ActivitySubType.O);
		int svvi_u = getFromActivityList(activities, ActivityType.SVVI, ActivitySubType.U);
		
		int sddd_f = getFromActivityList(activities, ActivityType.SDDD, ActivitySubType.F);
		int sddd_i = getFromActivityList(activities, ActivityType.SDDD, ActivitySubType.I);
		int sddd_o = getFromActivityList(activities, ActivityType.SDDD, ActivitySubType.O);
		int sddd_u = getFromActivityList(activities, ActivityType.SDDD, ActivitySubType.U);
		
		int svvr_f = getFromActivityList(activities, ActivityType.SVVR, ActivitySubType.F);
		int svvr_i = getFromActivityList(activities, ActivityType.SVVR, ActivitySubType.I);
		int svvr_o = getFromActivityList(activities, ActivityType.SVVR, ActivitySubType.O);
		int svvr_u = getFromActivityList(activities, ActivityType.SVVR, ActivitySubType.U);
		
		int ssd_f = getFromActivityList(activities, ActivityType.SSD, ActivitySubType.F);
		int ssd_i = getFromActivityList(activities, ActivityType.SSD, ActivitySubType.I);
		int ssd_o = getFromActivityList(activities, ActivityType.SSD, ActivitySubType.O);
		int ssd_u = getFromActivityList(activities, ActivityType.SSD, ActivitySubType.U);
		
		int pfr_f = getFromActivityList(activities, ActivityType.PFR, ActivitySubType.F);
		int pfr_i = getFromActivityList(activities, ActivityType.PFR, ActivitySubType.I);
		int pfr_o = getFromActivityList(activities, ActivityType.PFR, ActivitySubType.O);
		int pfr_u = getFromActivityList(activities, ActivityType.PFR, ActivitySubType.U);
		
		int functest = getFromActivityList(activities, ActivityType.FunctionTest, ActivitySubType.noSubType);
		int systest = getFromActivityList(activities, ActivityType.SystemTest, ActivitySubType.noSubType);
		int regtest = getFromActivityList(activities, ActivityType.RegressionTest, ActivitySubType.noSubType);
		int meeting = getFromActivityList(activities, ActivityType.Meeting, ActivitySubType.noSubType);
		
		/*
		int sdp_f = user.getTimeForActivity(ActivityType.SDP, week);
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
		int meeting = user.getTimeForActivity(ActivityType.Meeting, week);*/
		
		String cmd = "";
		if (command.equals(Command.show))
			cmd = " readonly ";

		int sum = sdp_f + sdp_i + sdp_o + sdp_u + 
					srs_f +  srs_i +  srs_o +  srs_u + 
					svvs_f + svvs_i + svvs_o + svvs_u +
					stldd_f + stldd_i + stldd_o + stldd_u +
					svvi_f + svvi_i + svvi_o + svvi_u +
					sddd_f + sddd_i + sddd_o + sddd_u +
					svvr_f + svvr_i + svvr_o + svvr_u +
					ssd_f + ssd_i + ssd_o + ssd_u +
					pfr_f + pfr_i + pfr_o + pfr_u +
					functest + systest + regtest + meeting;

		writer.print("<form method='post' action='timereport?" + HTMLWriter.LIST_COMMAND + "="
				+ command.toString() + "&id=" + timereport.getID() + "'>");
		writer.print("<table border='1'><tbody>");
		writer.print("<tr>");
		writer.print("<td colspan='2'><b>Name:</b></td><td colspan='2'>" + user.getUsername() + "</td>");
		writer.print("<td colspan='2'><b>Date:</b></td><td>" + time + "</td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td colspan='2'><b>Projectgroup</b>:</td><td colspan='2'>" + timereport.getProjectGroup() + "</td>");
		writer.print("<td colspan='2'><b>Week:</b></td><td><input" + cmd + " type='text' name='" + WEEK
				+ "' value='" + timereport.getWeek() + "' size='3'></td>");
		writer.print("</tr>");
		writer.print("<tr><td colspan='6' bgcolor='lightgrey'><font size='+1'><b>Del A: Total tid denna vecka (minuter)</b></font></td>");
		writer.print("<td>" + sum + "</td></tr>");
		writer.print("<tr><td colspan='7' bgcolor='lightgrey' nowrap=''><font size='+1'><b>Del B: Antalet minuter per aktivitet</b></font>");
		writer.print("<br>(Summan av alla separata aktiviteter räknas ut automatiskt och fylls i ovan.)</td></tr>");
		writer.print("<tr><th>Nummer</th><th>Aktivitet</th><th>U</th><th>I</th><th>F</th><th>O</th><th>Total tid</th></tr>");
		writer.print("<tr>");
		writer.print("<td>11</td>");
		writer.print("<td>SDP</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SDP_U + "' value='" + sdp_u
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SDP_I + "' value='" + sdp_i
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SDP_F + "' value='" + sdp_f
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SDP_O + "' value='" + sdp_o
				+ "' size='3'></i></td>");
		writer.print("<td><i> " + (sdp_u + sdp_i + sdp_f + sdp_o) + "</i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>12</td>");
		writer.print("<td>SRS</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SRS_U + "' value='" + srs_u
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SRS_I + "' value='" + srs_i
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SRS_F + "' value='" + srs_f
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SRS_O + "' value='" + srs_o
				+ "' size='3'></i></td>");
		writer.print("<td><i> " + (srs_u + srs_i + srs_f + srs_o) + "</i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>13</td>");
		writer.print("<td>SVVS</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVS_U + "' value='" + svvs_u
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVS_I + "' value='" + svvs_i
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVS_F + "' value='" + svvs_f
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVS_O + "' value='" + svvs_o
				+ "' size='3'></i></td>");
		writer.print("<td><i> " + (svvs_u + svvs_i + svvs_f + svvs_o) + "</i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>14</td>");
		writer.print("<td>STLDD</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + STLDD_U + "' value='" + stldd_u
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + STLDD_I + "' value='" + stldd_i
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + STLDD_F + "' value='" + stldd_f
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + STLDD_O + "' value='" + stldd_o
				+ "' size='3'></i></td>");
		writer.print("<td><i> " + (stldd_u + stldd_i + stldd_f + stldd_o) + "</i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>15</td>");
		writer.print("<td>SVVI</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVI_U + "' value='" + svvi_u
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVI_I + "' value='" + svvi_i
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVI_F + "' value='" + svvi_f
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVI_O + "' value='" + svvi_o
				+ "' size='3'></i></td>");
		writer.print("<td><i> " + (svvi_u + svvi_i + svvi_f + svvi_o) + "</i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>16</td>");
		writer.print("<td>SDDD</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SDDD_U + "' value='" + sddd_u
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SDDD_I + "' value='" + sddd_i
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SDDD_F + "' value='" + sddd_f
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SDDD_O + "' value='" + sddd_o
				+ "' size='3'></i></td>");
		writer.print("<td><i> " + (sddd_u + sddd_i + sddd_f + sddd_o) + "</i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>17</td>");
		writer.print("<td>SVVR</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVR_U + "' value='" + svvr_u
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVR_I + "' value='" + svvr_i
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVR_F + "' value='" + svvr_f
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SVVR_O + "' value='" + svvr_o
				+ "' size='3'></i></td>");
		writer.print("<td><i> " + (svvr_u + svvr_i + svvr_f + svvr_o) + "</i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>18</td>");
		writer.print("<td>SSD</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SSD_U + "' value='" + ssd_u
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SSD_I + "' value='" + ssd_i
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SSD_F + "' value='" + ssd_f
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SSD_O + "' value='" + ssd_o
				+ "' size='3'></i></td>");
		writer.print("<td><i> " + (ssd_u + ssd_i + ssd_f + ssd_o) + "</i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>19</td>");
		writer.print("<td>PFR</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + PFR_U + "' value='" + pfr_u
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + PFR_I + "' value='" + pfr_i
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + PFR_F + "' value='" + pfr_f
				+ "' size='3'></i></td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + PFR_O + "' value='" + pfr_o
				+ "' size='3'></i></td>");
		writer.print("<td><i> " + (pfr_u + pfr_i + pfr_f + pfr_o) + "</i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td bgcolor='lightgrey' colspan='7'></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>21</td>");
		writer.print("<td colspan='5'>Funktionstest</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + FUNCTION_TEST + "' value='"
				+ functest + "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>22</td>");
		writer.print("<td colspan='5'>Systemtest</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + SYSTEM_TEST + "' value='"
				+ systest + "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>23</td>");
		writer.print("<td colspan='5'>Regressionstest</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + REG_TEST + "' value='"
				+ regtest + "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr>");
		writer.print("<td>30</td>");
		writer.print("<td colspan='5'>Möte</td>");
		writer.print("<td><i><input" + cmd + " type='text' name='" + MEETING + "' value='"
				+ meeting + "' size='3'></i></td>");
		writer.print("</tr>");
		writer.print("<tr><td colspan='7' bgcolor='lightgrey'><font size='+1'><b>Del C: Signatur</b></font></td></tr><tr>");
		writer.print("<tr><td colspan='6'><b>Signerad av manager</b></td><td>"
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
	
	
	private int getFromActivityList(List<Activity> activities, ActivityType at, ActivitySubType ast) {
		for(Activity a : activities){
			if(a.getType().equals(at) && a.getSubType().equals(ast)){
				return a.getLength();
			}
		}
		return 0;
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
			if (command.equals(Command.sign) || command.equals(Command.delete)) {
				writer.print("<form method=\"POST\" action=\"listreports\">");
			} else {
				writer.print("<form method=\"GET\" action=\"timereport\">");
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

			writer.print("<input type=\"submit\" "+onclick(command) + " value=\"" + submitName(command) + "\">");

			writer.print("</form>");
		}
	}

	private String onclick(Command command) {
		if (Command.delete.equals(command)) {
			return "onclick = \"return confirm('Vill du ta bort denna tidrapporten?')\"";
		}
		return "";
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
			if (!Database.ADMIN.equals(user.getUsername())) {
				writer.print("<p>");
				printLink("mainpage", "Startsidan");
				writer.print("</p>");
			}
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
						+ "</td><td>");
				printLinkWithConfirm("/ProjectPUSS/administration?action=deleteUser&username="
						+ u.getUsername(), 
						"Ta bort", 
						"Är du säker på att du vill ta bort " + u.getUsername() + "?");
				writer.print("</td></tr>");
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
						+ "</a></td><td>");
				printLinkWithConfirm("projectadmin?action=removeProjectGroup&projectName="
						+ s, 
						"Ta bort",
						"Är du säker på att du vill ta bort " + s + "?");
				writer.print("</td></tr>");
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
						+ "\">Gör till användare</a></td><td>");
				printLinkWithConfirm("projectoverview?action=deleteUser&project=" + projectName
						+ "&username=" + u.getUsername(), 
						"Ta bort", "Är du säker på att du vill ta bort " + u.getUsername() + "?");
				writer.print("</td></tr>");
			}
			writer.print("<tr><th>Användare</th><th></th><th></th></tr>");
			for (User u : users) {
				if (!projectManagers.contains(u)) {
					writer.print("<tr><td>" + u.getUsername()
							+ "</td><td><a href=\"projectoverview?action=promoteUser&project="
							+ projectName + "&username=" + u.getUsername()
							+ "\">Gör till projektledare</a></td><td>");
					printLinkWithConfirm("projectoverview?action=deleteUser&project=" + projectName
							+ "&username=" + u.getUsername(), 
							"Ta bort", "Är du säker på att du vill ta bort " + u.getUsername() + "?");
					writer.print("</td></tr>");
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
	 */
	public void printLink(String url, String text) {
		writer.println("<a href=\"" + url + "\">" + text + "</a>");
	}
	
	/**
	 * Generates a link that requires the user to confirm the action.
	 * 
	 * @param url
	 *            the url to use for the link
	 * @param text
	 *            the text to display
	 * @param message
	 * 			  the message to display in the confirm dialog            
	 * 
	 */
	public void printLinkWithConfirm(String url, String text, String confirmMessage) {
		writer.print("<a href=\"" + url + "\" onclick=\"return confirm('" + confirmMessage + "')\">" + text + "</a>");
	}

	/**
	 * Skriver ut en lista med val för vilken typ av graf som ska genereras.
	 * 
	 * @param userRole
	 *            Användarens roll.
	 */
	public void printGraphChoice(Role userRole) {

		writer.print("<p> Välj typ av graf </p>");
		writer.print("<form method=\"POST\" action=\"statistics\" name=\"graphchooser\" id = \"graphchooser\">");
		writer.print("<select name=\"graphChooser\" id=\"graphChooser\"  onchange=\"this.form.submit()\" >");

		writer.print("<option selected disabled hidden value=''></option>");
		writer.print("<option value=\"userWeekTime\">Arbetstid per vecka</option>");
		writer.print("<option value=\"activityTime\">Arbetstid per aktivitet</option>");

		if (userRole == Role.Manager) {
		writer.print("<option value=\"plUserTime\">Arbetstid per användare</option>");
		writer.print("<option value=\"weekBurnDown\">Burn down chart för arbetstid per vecka</option>");
		writer.print("<option value=\"activityBurnDown\">Burn down chart för specifik aktivitet</option>");
		writer.print("<option value=\"userBurnDown\">Burn down chart för specifik användare</option>");
		}

		writer.print("</select>");
		writer.print("</form>");

	}

	/**
	 * Skriver ut två drop down listor där användaren kan välja två
	 * veckornummer.
	 * 
	 * @param startWeek
	 *            Första veckan användaren kan välja.
	 * @param lastWeek
	 *            Sista veckan användaren kan välja.
	 */
	public void printWeekChoice(int startWeek, int stopWeek) {

		writer.print("<p> Välj startvecka </p>");
		writer.print("<form method=\"POST\" action=\"statistics\" name=\"startweek\" id = \"startweek\">");
		writer.print("<select name=\"startWeek\" id=\"startWeek\"  onchange=\"this.form.submit()\" >");

		writer.print("<option selected disabled hidden value=''></option>");
		for (int i = startWeek; i <= stopWeek; i++) {
			writer.print("<option value=\"" + i + "\">Vecka " + i + "</option>");
		}

		writer.print("</select>");
		writer.print("</form>");

		writer.print("<p> Välj slutvecka </p>");
		writer.print("<form method=\"POST\" action=\"statistics\" name=\"stopweek\" id = \"stopweek\">");
		writer.print("<select name=\"stopWeek\" id=\"stopWeek\"  onchange=\"this.form.submit()\" >");

		writer.print("<option selected disabled hidden value=''></option>");
		for (int i = startWeek; i <= stopWeek; i++) {
			writer.print("<option value=\"" + i + "\">Vecka " + i + "</option>");
		}

		writer.print("</select>");
		writer.print("</form>");

	}

	/**
	 * Skriver ut en drop down lista med alla de olika typer av aktiviteter
	 * användaren kan välja.
	 */
	public void printActivityChoice() {

		writer.print("<p> Välj typ av aktivitet </p>");
		writer.print("<form method=\"POST\" action=\"statistics\" name=\"activityChoice\" id = \"activityChoice\">");
		writer.print("<select name=\"activityChoose\" id=\"activityChoose\"  onchange=\"this.form.submit()\" >");

		writer.print("<option selected disabled hidden value=''></option>");
		for (int i = 0; i < ActivityType.values().length; i++) {
			writer.print("<option value=\"" + (ActivityType.values())[i] + "\">"
					+ (ActivityType.values())[i] + "</option>");

		}

		writer.print("</select>");
		writer.print("</form>");

	}

	/**
	 * Skriver ut en drop down lista med användare som användaren kan välja.
	 * 
	 * @param userList
	 *            Lista med användare.
	 */
	public void printUserChoice(List<User> userList) {

		writer.print("<p> Välj användare </p>");
		writer.print("<form method=\"POST\" action=\"statistics\" name=\"userChoice\" id = \"userChoice\">");
		writer.print("<select name=\"userChoose\" id=\"userChoose\"  onchange=\"this.form.submit()\" >");

		writer.print("<option selected disabled hidden value=''></option>");
		for (int i = 0; i < userList.size(); i++) {
			writer.print("<option value=\"" + userList.get(i).getUsername() + "\">"
					+ userList.get(i).getUsername() + "</option>");

		}

		writer.print("</select>");
		writer.print("</form>");

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
	 */
	public void printGraph(List<TimeReport> timeReports, GraphSettings gs) {
		String xAxisName = gs.getXName();
		String yAxisName = gs.getYName();

		String title = "";

		String html = "<script type=\"text/javascript\" src=\"jquery.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jquery.jqplot.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jqplot.barRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jqplot.categoryAxisRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jqplot.pointLabels.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jqplot.canvasTextRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jqplot.canvasAxisLabelRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jqplot.canvasAxisTickRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jqplot.dateAxisRenderer.min.js\"></script>";
		html += "<link rel=\"stylesheet\" type=\"text/css\" href=\"jquery.jqplot.min.css\" />";

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
			html += html.substring(0, html.length() - 1);

			break;

		case "activityTime":
			title = "Spenderad tid per aktivitet";
			HashMap<ActivityType, Integer> actTimes = new HashMap<ActivityType, Integer>();

			for (int i = 0; i < timeReports.size(); i++) {

				List<Activity> activities = timeReports.get(i).getActivities();

				for (Activity a : activities) {
					if (actTimes.containsKey(a.getType())) {
						Integer time = actTimes.remove(a.getType());
						actTimes.put(a.getType(), time + a.getLength());
					} else {
						actTimes.put(a.getType(), a.getLength());
					}
				}
			}

			if (!actTimes.isEmpty()) {

				Set<ActivityType> types = actTimes.keySet();

				for (ActivityType t : types) {
					html += "['" + t.toString() + "'," + actTimes.get(t).toString() + "],";
				}
				html += html.substring(0, html.length() - 1);
			} else {
				printErrorMessage("Det finns ingen arbetstid registrerad för användaren");
			}
			break;
		case "plUserTime":
			title = "Spenderad tid per gruppmedlem";
			HashMap<String, Integer> userTimes = new HashMap<String, Integer>();

			for (int i = 0; i < timeReports.size(); i++) {

				List<Activity> activities = timeReports.get(i).getActivities();

				int totalTime = 0;

				for (Activity a : activities) {
					totalTime += a.getLength();
				}

				if (userTimes.containsKey(timeReports.get(i).getUser().getUsername())) {
					Integer time = userTimes.remove(timeReports.get(i).getUser().getUsername());
					totalTime += time;
					userTimes.put(timeReports.get(i).getUser().getUsername(), totalTime);
				} else {
					userTimes.put(timeReports.get(i).getUser().getUsername(), totalTime);
				}
			}

			Set<String> users = userTimes.keySet();

			for (String u : users) {
				html += "['" + u + "'," + userTimes.get(u).toString() + "],";
				System.out.println(u + " " + userTimes.get(u).toString());
			}
			html += html.substring(0, html.length() - 1);

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

		String html = "<script type=\"text/javascript\" src=\"jquery.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jquery.jqplot.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jqplot.highlighter.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jqplot.cursor.min.js\"></script>";

		html += "<script type=\"text/javascript\" src=\"jqplot.canvasTextRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jqplot.canvasAxisLabelRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jqplot.canvasAxisTickRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jqplot.dateAxisRenderer.min.js\"></script>";
		html += "<script type=\"text/javascript\" src=\"jqplot.categoryAxisRenderer.min.js\"></script>";

		html += "<link rel=\"stylesheet\" type=\"text/css\" href=\"jquery.jqplot.min.css\" />";

		html += "<div id=\"chart\" ></div>";

		html += "<script>";

		html += "var line = [";

		int totalTime = 0;
		HashMap<Integer, Integer> weekTime = null;

		Collections.sort(timeReports, new Comparator<TimeReport>() {
			@Override
			public int compare(final TimeReport object1,
					final TimeReport object2) {
				return object1.getWeek() - object2.getWeek();
			}
		});

		switch (gs.getGraphType()) {
		case "weekBurnDown":
			title = "Burn down chart för tid mellan specifierade veckor";

			weekTime = new HashMap<Integer, Integer>();

			for (int i = 0; i < timeReports.size(); i++) {
				List<Activity> activities = timeReports.get(i).getActivities();

				for (Activity a : activities) {

					totalTime += a.getLength();

					if (weekTime.containsKey(timeReports.get(i).getWeek())) {
						weekTime.put(
								timeReports.get(i).getWeek(),
								weekTime.get(timeReports.get(i).getWeek())
										+ a.getLength());
					} else {
						weekTime.put(timeReports.get(i).getWeek(),
								a.getLength());
					}

				}
			}

			if (!weekTime.isEmpty()) {
				Object[] keys = weekTime.keySet().toArray();
				Arrays.sort(keys);

				for (int i = 0; i < keys.length; i++) {
					html += "[" + keys[i] + "," + totalTime + "],";

					totalTime = totalTime - weekTime.get(keys[i]);
				}
				html = html.substring(0, html.length() - 1);
			} else {
				printErrorMessage("Det finns ingen arbetstid registrerad för projektgruppen");
			}

			break;

		case "activityBurnDown":
			title = "Burn down chart för specifierad aktivitet";
			weekTime = new HashMap<Integer, Integer>();

			for (int i = 0; i < timeReports.size(); i++) {
				List<Activity> activities = timeReports.get(i).getActivities();

				for (Activity a : activities) {
					if (a.getType() == gs.getActivityType()) {
						totalTime += a.getLength();

						if (weekTime.containsKey(timeReports.get(i).getWeek())) {
							weekTime.put(timeReports.get(i).getWeek(),
									weekTime.get(timeReports.get(i).getWeek())
											+ a.getLength());
						} else {
							weekTime.put(timeReports.get(i).getWeek(),
									a.getLength());
						}
					}
				}
			}

			if (!weekTime.isEmpty()) {

				Object[] keys = weekTime.keySet().toArray();
				Arrays.sort(keys);

				for (int i = 0; i < keys.length; i++) {
					html += "[" + keys[i] + "," + totalTime + "],";

					totalTime = totalTime - weekTime.get(keys[i]);
				}

				html = html.substring(0, html.length() - 1);

			} else {
				printErrorMessage("Det finns ingen arbetstid registrerad för vald aktivitet");
			}

			break;
		case "userBurnDown":
			title = "Burn down chart för specifierad användare";
			totalTime = 0;

			HashMap<Integer, Integer> userWeekTime = new HashMap<Integer, Integer>();

			for (int i = 0; i < timeReports.size(); i++) {
				List<Activity> activities = timeReports.get(i).getActivities();

				for (Activity a : activities) {
					totalTime += a.getLength();

					if (userWeekTime.containsKey(timeReports.get(i).getWeek())) {
						int time = userWeekTime.remove(timeReports.get(i)
								.getWeek());
						time += a.getLength();
						userWeekTime.put(timeReports.get(i).getWeek(), time);
					} else {
						userWeekTime.put(timeReports.get(i).getWeek(),
								a.getLength());
					}
				}
			}

			if (!userWeekTime.isEmpty()) {
				Object[] keys = userWeekTime.keySet().toArray();
				Arrays.sort(keys);

				System.out.println(keys.length);

				for (int i = 0; i < keys.length; i++) {
					html += "[" + keys[i] + "," + totalTime + "],";

					totalTime = totalTime - userWeekTime.get(keys[i]);
				}
				html = html.substring(0, html.length() - 1);
			} else {
				printErrorMessage("Det finns ingen arbetstid registrerad för användaren");
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

	public void printOtherStatsChoice() {
		writer.print("<a href='statistics?moreStats'>Mer statistik</a>");
	}

	public void printStatPanel(List<User> usersInProject, int firstWeek, int lastWeek) {
		writer.print("<form action='' method='post'>"
				+ "<table border=1 valign='top' style='vertical-align:top'><tr><td valign='top'>"
				+ "<table><tr><td><b>Användare</b></td></tr>");
					for(User u : usersInProject){
						writer.print("<tr><td>"
								+ "<input type='checkbox' checked name='user_"
								+ u.getUsername() + "' />"
								+ u.getUsername()
								+ "</td></tr>");
					}
		writer.print("</table></td>"
				+ "<td valign='top'><table><tr><td><b>Roller</b></td></tr>");
				for(Role r : Role.values()){
					writer.print("<tr><td>"
							+ "<input type='checkbox' checked name='role_"
							+ r.name() + "' />"
							+ r.name()
							+ "</td></tr>");
				}
		writer.print("</table></td>");
		
		writer.print("<td valign='top'><table><tr><td><b>Aktiviteter</b></td></tr>");
				for(ActivityType a : ActivityType.values()){
					writer.print("<tr><td>"
							+ "<input type='checkbox' checked name='activity_"
							+ a.name() + "' />"
							+ a.name()
							+ "</td></tr>");
				}
		writer.print("</table></td>");
		
		writer.print("<td valign='top'><table><tr><td><b>Period</b></td></tr>");
		writer.print("<tr><td>Startvecka<br/><select name='startweek'>");
				for(int i = firstWeek; i<=lastWeek; i++){
					writer.print("<option value='" + i + "'>vecka " + i + "</option>");
				}
		writer.print("</select>");
		writer.print("<tr><td>Slutvecka<br/><select name='endweek'>");
		for(int i = firstWeek; i<=lastWeek; i++){
			writer.print("<option value='" + i + "'>vecka " + i + "</option>");
		}
		writer.print("</select>");
		writer.print("</table></td></tr>");
		

		writer.print("<tr><td align='right' colspan='4'><input type='submit' value='Visa statistik' name='moreStats' /></td></tr>");
		
		writer.print("</table></form>");
		
	}


}
