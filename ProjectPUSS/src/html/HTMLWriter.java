package html;

import items.Command;
import items.GraphSettings;
import items.Role;
import items.TimeReport;
import items.User;

import java.io.PrintWriter;
import java.util.List;

/**
 * Denna klassen innehåller funktionalitet för att skriva ut all den HTML kod
 * som systemets olika delar behöver, t.ex. för listning av tidrapporter i en
 * tabell.
 * 
 */
public class HTMLWriter {


	final static public String LOGIN_USERNAME = "username";
	final static public String LOGIN_PASSWORD = "password";


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
	public void printTimeReport(TimeReport timereport, Command command,
			Role role) {
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
	public void printTimeReports(List<TimeReport> timereports, Command command,
			Role role) {
		
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
	public void printUsers(List<User> users, Role role) {
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
	public void printBurndownChart(List<TimeReport> timeReports,
			GraphSettings gs) {
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
		writer.print("<html><head><title>E-PUSS 1301</title></head><body><h1>E-PUSS 1301</h1>");
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
	 * @param currentProjectGroup
	 *            är nuvarande projektgrupp.
	 * @param projects
	 *            är samtliga tillgängliga projekt.
	 * 
	 */
	public void printProjectChooser(String currentProjectGroup,
			List<String> projects) {
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
				writer.print("<tr><td>"
						+ u.getUsername()
						+ "</td><td>"
						+ u.getPassword()
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
				writer.print("<tr><td>"
						+ s
						+ "</td><td><a href=\"/ProjectPUSS/projectadmin?action=removeProjectGroup&projectName="
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
	public void printProjectGroupMembers(List<User> users,
			List<User> projectManagers, String projectName) {
		if (!(users.size() == 0 && projectManagers.size() == 0)) {
			writer.print("<table><tr><th>Användarnamn</th><th></th><th></th></tr>");
			for (User u : projectManagers) {
				writer.print("<tr><td>"
						+ u.getUsername()
						+ "</td><td><a href=\"/ProjectPUSS/projectoverview?action=makeUser&project="
						+ projectName
						+ "&username="
						+ u.getUsername()
						+ "\">Gör till användare</a></td><td>"
						+ "<a href=\"/ProjectPUSS/projectoverview?action=deleteUser&project="
						+ projectName + "&username=" + u.getUsername()
						+ "\">Ta bort</a>" + "</td></tr>");
			}
			for (User u : users) {
				if (!projectManagers.contains(u)) {
					writer.print("<tr><td>"
							+ u.getUsername()
							+ "</td><td><a href=\"/ProjectPUSS/projectoverview?action=makeManager&project="
							+ projectName
							+ "&username="
							+ u.getUsername()
							+ "\">Gör till projektledare</a></td><td>"
							+ "<a href=\"/ProjectPUSS/projectoverview?action=deleteUser&project="
							+ projectName + "&username=" + u.getUsername()
							+ "\">Ta bort</a>" + "</td></tr>");
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
	 * 		the url to use for the link
	 * @param text
	 * 		the text to display
	 * 
	 * @return HTML code for the link
	 */
	public void printLink(String url, String text) {
		writer.println("<a href=\"" + url + "\">" + text + "</a>");
	}

}
