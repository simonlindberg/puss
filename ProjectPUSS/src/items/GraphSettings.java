package items;

/**
 * Denna hjälpklass innehåller information för en graf eller ett diagram som ska
 * genereras och skickas till de metoder i HTMLwriter som sköter detta. Klassen
 * används temporärt för att bifoga informationen.
 */
public class GraphSettings {
	private String graphType;
	private int xType;
	private int yType;
	private String xName;
	private String yName;
	
	/**
	 * Konstruktor som skapar en instans av klassen och sätter värden till
	 * respektive variabler.
	 */
	public GraphSettings(String graphType, int xType, int yType, String xName,
			String yName) {
		this.graphType = graphType;
		this.xType = xType;
		this.yType = yType;
		this.xName = xName;
		this.yName = yName;
	}

	/**
	 * 
	 Returnerar typen av graf.
	 * 
	 */
	public String getGraphType() {
		return graphType;
	}

	/**
	 * 
	 Returnerar den typ av information som ska finnas på X-axeln.
	 * 
	 */
	public int getXType() {
		return xType;
	}

	/**
	 * 
	 Returnerar den typ av information som ska finnas på Y-axeln.
	 * 
	 */
	public int getYType() {
		return yType;
	}

	/**
	 * 
	 * 
	 Returnerar det namn som ska stå på X-axeln.
	 */
	public String getXName() {
		return xName;
	}

	/**
	 * 
	 Returnerar det namn som ska stå på Y-axeln.
	 * 
	 */
	public String getYName() {
		return yName;
	}
}
