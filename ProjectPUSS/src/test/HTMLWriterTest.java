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
	public void setup() {
		stringwriter = new StringWriter();
		htmlwriter = new HTMLWriter(new PrintWriter(stringwriter));
	}
	
	@After
	public void teardown() {
		stringwriter = null;
		htmlwriter = null;
	}
	
	@Test
	public void testPrintHead() {
		// TODO: write proper test..
		htmlwriter.printHead(null);
		assertEquals("", stringwriter.toString());
	}


}
