package items;

/**
 * Denna klassen beskriver en användare och innehåller information om
 * användarnamn och lösenord och används för att bifoga denna information till
 * olika delar i systemet. Klassen används temporärt för att bifoga
 * informationen.
 */
public class User {
	
	private String username;
	private String password;
	/**
	 * 
	 Användarens konstruktor
	 * 
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * 
	 * 
	 Returnerar användarens namn.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * 
	 Returnerar användarens lösenord
	 */
	public String getPassword() {
		return password;
	}
}
