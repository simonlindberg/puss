package servlets;

import items.Command;
import items.Role;
import items.TimeReport;
import items.User;

import java.beans.Statement;
import java.sql.SQLException;

import html.HTMLWriter;

import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import database.Database;

/**
 * Denna klass bygger ut ServletBase och renderar en sida, via HTMLWriter, för
 * att skapa en tidsrapport. Användaren fyller här i tidsrapporten och klassen
 * meddelar även användaren om det lyckades eller om det uppstod problem med att
 * skapa tidrapporten. Sidan är tillgänglig för användare, men ej för
 * administratörer.
 */
@WebServlet("/create")
public class CreateTimeReport extends ServletBase {

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		User user = (User) request.getSession().getAttribute(ServletBase.USER);
		String projectgroup = (String) request.getSession().getAttribute(ServletBase.PROJECT);
		TimeReport t = null;
		Role role = null;
		role = database.getRole(user.getUsername(), projectgroup);
		if(role==null){
			html.printErrorMessage("Du har en roll där du inte kan skapa tidrapporter");
		} else {
			html.printTimeReport(t, Command.create, role);
		}

	}

}
