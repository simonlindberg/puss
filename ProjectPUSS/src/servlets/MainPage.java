package servlets;

import html.HTMLWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Denna klassen bygger ut ServletBase och renderar huvudsidan, via HTMLWriter,
 * som användare hamnar på efter inloggning. På sidan kan man välja vilken
 * projektgrupp man ska använda, samt tar sig vidare till funktionalitet, tex
 * skapa en ny tidrapport, administrera över användare eller visa statistik.
 */
@WebServlet("/Main")
public class MainPage extends ServletBase {

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		// TODO Auto-generated method stub
		

	}

}
