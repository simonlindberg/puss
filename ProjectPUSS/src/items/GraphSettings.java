package items;

/**
 * Denna hjälpklass innehåller information för en graf eller ett diagram som ska
 * genereras och skickas till de metoder i HTMLwriter som sköter detta. Klassen
 * används temporärt för att bifoga informationen.
 */
public class GraphSettings {
	/**
	 * Konstruktor som skapar en instans av klassen och sätter värden till
	 * respektive variabler.
	 */
	public GraphSettings(String graphType, int xType, int yType, String xName,
			String yName) {
	}

	/**
	 * 
	 Returnerar typen av graf.
	 * 
	 */
	public String getGraphType() {
		return null;
	}

	/**
	 * 
	 Returnerar den typ av information som ska finnas på X-axeln.
	 * 
	 */
	public int getXType() {
		return 0;
	}

	/**
	 * 
	 Returnerar den typ av information som ska finnas på Y-axeln.
	 * 
	 */
	public int getYType() {
		return 0;
	}

	/**
	 * 
	 * 
	 Returnerar det namn som ska stå på X-axeln.
	 */
	public String getXName() {
		return null;
	}

	/**
	 * 
	 Returnerar det namn som ska stå på Y-axeln.
	 * 
	 */
	public String getYName() {
		return null;
	}
}
