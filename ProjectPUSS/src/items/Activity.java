package items;

/**
 * Denna klassen innehåller information om vilken typ av aktivitet det är, se
 * REF 2, samt hur länge den utfördes i minuter. Activity används i TimeReport.
 * Klassen används temporärt för att bifoga informationen.
 * 
 */
public class Activity {
	
	private ActivityType type;
	private int length;
	private ActivitySubType subtype;
	/**
	 * Konstruktor.
	 * 
	 * @param type
	 *            aktivitetns typ
	 * @param length
	 *            längden av aktiviten
	 */
	public Activity(ActivityType type, int length, ActivitySubType subtype) {
		this.type = type;
		this.length = length;
		this.subtype = subtype;
	}

	/**
	 * Returnerar aktivitetens typ.
	 */
	public ActivityType getType() {
		return type;
	}

	/**
	 * Returnerar aktvitetns längd i minuter.
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * Returnerar aktivitetens typ.
	 */
	public ActivitySubType getSubType() {
		return subtype;
	}

}
