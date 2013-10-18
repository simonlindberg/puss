package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.ActivityType;
import items.GraphSettings;

public class GraphSettingsTest {

	private GraphSettings gs;

	@Before
	public void setup() {
		gs = new GraphSettings("Type", ActivityType.SRS, "xAxis", "yAxis");
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
	public void testGetActivityType() {
		assertEquals(ActivityType.SRS, gs.getActivityType());
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

