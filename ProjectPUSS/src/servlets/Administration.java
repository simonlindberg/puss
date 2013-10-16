package servlets;

import html.HTMLWriter;
import items.User;

import java.util.List;

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

	private boolean isAdmin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return database.getUser(Database.ADMIN).equals(session.getAttribute(USER));
	}

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		if (isAdmin(request)) {

			html.printAddUserForm();

			List<User> users = database.getUsers();
			html.printAdminUserList(users);
		}

	}

}
