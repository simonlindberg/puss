package servlets;

import html.HTMLWriter;

import javax.servlet.http.HttpServletRequest;

/**
 * Denna klass bygger ut ServletBase och renderar en sida, via HTMLWriter, för
 * att skapa en tidsrapport. Användaren fyller här i tidsrapporten och klassen
 * meddelar även användaren om det lyckades eller om det uppstod problem med att
 * skapa tidrapporten. Sidan är tillgänglig för användare, men ej för
 * administratörer.
 */
public class CreateTimeReport extends ServletBase {

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		// TODO Auto-generated method stub

	}

}
