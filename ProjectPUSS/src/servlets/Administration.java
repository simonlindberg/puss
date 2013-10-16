package servlets;

import html.HTMLWriter;
import items.User;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	private static final String CREATE_USER = "createUser";
	private static final String DELETE_USER = "deleteUser";
	
	
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	doGet(req, resp);
    }

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		if (isAdmin(request)) {
			String action = request.getParameter("action");
			
			if (CREATE_USER.equals(action)) {
				String username = request.getParameter("username");
				String password = createPassword();
				if (checkNewName(username)) {
					if (database.addUser(username, password)) {
						html.printSuccessMessage(username + " blev tillagd i systemet.");
					} else {
						html.printErrorMessage(username + " gick inte att spara i systemet.");
					}
				} else {
					html.printErrorMessage(username + " uppfyllde inte kraven på användarnamn.");
				}
			} else if (DELETE_USER.equals(action)) {
				String username = request.getParameter("username");
				if (database.deleteUser(username)) {
					html.printSuccessMessage(username + " blev borttagen från systemet.");
				} else {
					html.printErrorMessage(username + " gick inte att ta bort från systemet.");
				}
			}
			
			
			html.printLink("/ProjectPUSS/projectadmin", "Administrera projektgrupper");
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
