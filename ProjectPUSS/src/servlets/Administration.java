package servlets;

import html.HTMLWriter;
import items.User;

import java.util.List;
import java.util.Random;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import database.Database;

/**
 * Denna klassen bygger Denna klassen bygger ut ServletBase och renderar en
 * sida, via HTMLWriter, där alla användare listas. Det finns funktionalitet för
 * att skapa en ny användare samt ta bort befintliga användare. Om den inloggade
 * användaren inte är administratör ges ej tillgång till något, det vill säga
 * listan visas ej och ingen funktionalitet som försöks användas genomförs. Med
 * funktionalitet menas http-förfrågningar som på något sätt utför operationer
 * på systemet via denna klass, till exempel lägga till och ta bort användare.
 */
@WebServlet("/administration")
public class Administration extends ServletBase {

	private static final int PASSWORD_LENGTH = 6;
	
	private boolean isAdmin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return database.getUser(Database.ADMIN).equals(session.getAttribute(USER));
	}
	
    /**
     * Checks if a username corresponds to the requirements for user names. 
     * @param name The investigated username
     * @return True if the username corresponds to the requirements
     */
    private boolean checkNewName(String name) {
    	int length = name.length();
    	boolean ok = (length>=5 && length<=10);
    	if (ok)
    		for (int i=0; i<length; i++) {
    			int ci = (int)name.charAt(i);
    			boolean thisOk = ((ci>=48 && ci<=57) || 
    					          (ci>=65 && ci<=90) ||
    					          (ci>=97 && ci<=122));
    			//String extra = (thisOk ? "OK" : "notOK");
    			//System.out.println("bokst:" + name.charAt(i) + " " + (int)name.charAt(i) + " " + extra);
    			ok = ok && thisOk;
    		}    	
    	return ok;
    }
    
    /**
     * Creates a random password.
     * @return a randomly chosen password
     */
    private String createPassword() {
    	String result = "";
    	Random r = new Random();
    	for (int i=0; i<PASSWORD_LENGTH; i++)
    		result += (char)(r.nextInt(26)+97); // 122-97+1=26
    	return result;
    }

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		if (isAdmin(request)) {

			html.printAddUserForm();
			
			

			List<User> users = database.getUsers();
			html.printAdminUserList(users);
		} else {
			// redirect to mainpage
			html.printErrorMessage("Du har inte tillgång till denna sidan");
			html.printLink("/ProjectPUSS/mainpage", "Huvudsida");
		}

	}

}
