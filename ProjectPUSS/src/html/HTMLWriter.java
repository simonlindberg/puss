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
	public void printBurndownChart(List<TimeReport> timeReports, GraphSettings gs) {
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
		writer.print("...");
	}

	/**
	 * 
	 Skriver ut sidfoten
	 * 
	 */
	public void printFoot() {
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
	public void printProjectChooser(String currentProjectGroup, List<String> projects) {
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
	}

	/**
	 * 
	 Skriver ut ett felmeddelande.
	 * 
	 * @param message
	 *            det meddelande som skall skrivas ut.
	 * 
	 */
	public void printFailureMessage(String message) {
	}

	/**
	 * 
	 @param users
	 *            lista över användare i systemet. Skriver ut en lista över alla
	 *            användare i systemet.
	 * 
	 */
	public void printAdminUserList(List<User> users) {
	}

	/**
	 * 
	 Skriver ut ett formulär för att lägga till en användare i systemet.
	 * 
	 */
	public void printAddUserForm() {
	}

	/**
	 * 
	 @param groups
	 *            lista med grupper. Skriver ut listan över grupper.
	 * 
	 */
	public void printProjectGroups(List<String> groups) {
	}

	/**
	 * 
	 Skriver ut ett formulär för att lägga till en projektgrupp i systemet.
	 * 
	 */
	public void printAddProjectGroupForm() {
	}

	/**
	 * 
	 @param users
	 *            lista över medlemmar i en projektgrupp. Skriver ut en lista
	 *            över en projektgrupps medlemmar.
	 * 
	 */
	public void printProjectGroupMembers(List<User> users) {
	}

	/**
	 * 
	 Skriver ut ett formulär för att lägga till en användare i en grupp.
	 * 
	 */
	public void printAddUserToProjectGroupForm() {
	}

	/**
	 * 
	 @param message
	 *            felmeddelande. Skriver ut ett felmeddelande.
	 * 
	 */
	public void printErrorMessage(String message) {
	}

}
