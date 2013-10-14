package items;

import java.util.List;

/**
 * Denna klassen innehåller information om en tidrapport som t.ex. vem som
 * skapade tidrapporten, vilket veckonummer det gäller, vilka aktiviteter man
 * utfört, om tidrapporten är signerad mm. För mer information om vad en
 * tidrapport skall innehålla, se (Software Requirements Specifikation (dok.nr:
 * PUSS134102, ver:1.1)). TimeReport används för att bifoga informationen till
 * olika delar i systemet. Klassen används temporärt för att bifoga
 * informationen.
 */
public class TimeReport {
	/**
	 * 
	 Tidrapportens konstruktor.
	 * 
	 */
	public TimeReport(User user, List<Activity> activities, boolean signed,
			int ID, int week, String projectgroup) {
	}

	/**
	 * 
	 Returnerar vilken vecka som rapporten är associerad med.
	 * 
	 */
	public int getWeek() {
		return 0;
	}

	/**
	 * 
	 * 
	 Returnerar tidrapportens ID
	 */
	public int getID() {
		return 0;
	}

	/**
	 * 
	 * 
	 Returner true om tidrapporten är signerad. Annars false.
	 */
	public boolean getSigned() {
		return false;
	}

	/**
	 * 
	 * 
	 Returnerar alla aktiviteter som är associerade med denna tidrapport
	 */
	public List<Activity> getActivities() {
		return null;
	}

	/**
	 * 
	 * 
	 Hämtar användaren.
	 */
	public User getUser() {
		return null;
	}

	/**
	 * 
	 * 
	 Hämtar projektgruppens namn
	 */
	public String getProjectGroup() {
		return null;
	}
}
