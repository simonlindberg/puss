package items;

/**
 * Denna hjälpklass innehåller information för en graf eller ett diagram som ska
 * genereras och skickas till de metoder i HTMLwriter som sköter detta. Klassen
 * används temporärt för att bifoga informationen.
 */
public class GraphSettings {
	private String graphType;
	private ActivityType actType;
	private String xName;
	private String yName;

	/**
	 * Konstruktor som skapar en instans av klassen och sätter värden till
	 * respektive variabler.
	 */
	public GraphSettings(String graphType, ActivityType actType, String xName, String yName) {
		this.graphType = graphType;
		this.actType = actType;
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
	 Returnerar den satta activityType.
	 */
	public ActivityType getActivityType() {
		return actType;
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

