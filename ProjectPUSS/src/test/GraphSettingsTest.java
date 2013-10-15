package test;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.GraphSettings;

public class GraphSettingsTest {
	
	private GraphSettings gs;
	
	@Before
	public void setup() {
		gs = new GraphSettings("Type", 5, 10, "xAxis", "yAxis");
	}
	
	@After
	public void teardown() {
		gs = null;
	}
	
	@Test
	public void testGetGraphType() {
		assertEquals("Type", gs.getGraphType());
	}
	
	@Test
	public void testGetXType() {
		assertEquals(5, gs.getXType());
	}
	
	@Test
	public void testGetYType() {
		assertEquals(10, gs.getYType());
	}
	
	@Test
	public void testGetXName() {
		assertEquals("xAxis", gs.getXName());
	}
	
	@Test
	public void testGetYName() {
		assertEquals("yAxis", gs.getYName());
	}

}
