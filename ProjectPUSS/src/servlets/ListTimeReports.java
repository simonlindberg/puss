package servlets;

import html.HTMLWriter;

import javax.servlet.http.HttpServletRequest;

/**
 * Denna klassen bygger ut ServletBase och renderar en sida, via HTMLWriter, där
 * alla tidrapporter för en projektgrupp listas upp. Beroende på vad man har
 * valt på MainPage visar sidan en lista för att uppdatera tidrapporterna, ta
 * bort någon tidrapport eller signera någon tidrapport. Sidan är tillgänglig
 * för alla.
 * 
 */
public class ListTimeReports extends ServletBase {

	@Override
	protected void doWork(HttpServletRequest request, HTMLWriter html) {
		// TODO Auto-generated method stub

	}

}
