package servlets;

import html.HTMLWriter;

import javax.servlet.http.HttpServletRequest;

/**
 * Denna klassen bygger Denna klassen bygger ut ServletBase och renderar en
 * sida, via HTMLWriter, där alla användare listas. Det finns funktionalitet för
 * att skapa en ny användare samt ta bort befintliga användare. Om den inloggade
 * användaren inte är administratör ges ej tillgång till något, det vill säga
 * listan visas ej och ingen funktionalitet som försöks användas genomförs. Med
 * funktionalitet menas http-förfrågningar som på något sätt utför operationer
 * på systemet via denna klass, till exempel lägga till och ta bort användare.
 */
public class Administration extends ServletBase {

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		// TODO Auto-generated method stub

	}

}
