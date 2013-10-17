package items;

import java.sql.SQLException;

import database.Database;

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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return username.equals(((User) obj).username);
		}
		return false;
	}
	
	public int getTimeForActivity(ActivityType type, int week) {
		try {
			return Database.getInstance().getTimeForActivity(this, type, week);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
