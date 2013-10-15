package test;

import static org.junit.Assert.*;
import html.HTMLWriter;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HTMLWriterTest {

	private StringWriter stringwriter;
	private HTMLWriter htmlwriter;
	
	@Before
	public void setUp() throws Exception {
		stringwriter = new StringWriter();
		htmlwriter = new HTMLWriter(new PrintWriter(stringwriter));
	}
	
	@After
	public void tearDown() throws Exception {
		stringwriter = null;
		htmlwriter = null;
	}
	
	@Test
	public void testPrintHead() {
		htmlwriter.printHead(null);
		assertEquals("<html><head><title>E-PUSS 1301</title></head><body><h1>E-PUSS 1301</h1>", stringwriter.toString());
	}
	
	@Test
	public void testPrintFoot() {
		htmlwriter.printFoot();
		assertEquals("</body></html>", stringwriter.toString());
	}
	
	


}
