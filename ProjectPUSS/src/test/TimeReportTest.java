package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import html.HTMLWriter;
import items.Activity;
import items.ActivityType;
import items.TimeReport;
import items.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TimeReportTest {
	
	private TimeReport tr1;
	private TimeReport tr2;
	private List<Activity> activities;
	private User user;
	
	@Before
	public void setUp() throws Exception {
		user = new User("test", "password");
		activities = new ArrayList<Activity>();
		Activity ac;
		ac = new Activity(ActivityType.SDP, 10);
		activities.add(0, ac);
		ac = new Activity(ActivityType.SRS, 20);
		activities.add(ac);
		ac = new Activity(ActivityType.SVVS, 30);
		activities.add(ac);
		ac = new Activity(ActivityType.STLDD, 400);
		activities.add(ac);
		ac = new Activity(ActivityType.SVVI, 500);
		activities.add(ac);
		ac = new Activity(ActivityType.SDDD, 600);
		activities.add(ac);
		ac = new Activity(ActivityType.SVVR, 700);
		activities.add(ac);
		ac = new Activity(ActivityType.SSD, 800);
		activities.add(ac);
		
		tr1 = new TimeReport(user, activities, true, 0, 1, "group1");
		tr2 = new TimeReport(user, activities, false, 1, 2, "group2");
	}
	
	@After
	public void tearDown() throws Exception {
		tr1 = null;
		tr2 = null;
		activities = null;
		user = null;
	}
	
	@Test
	public void testIsSigned() {
		assertEquals(true, tr1.getSigned());
	}
	@Test
	public void testIsNotSigned() {
		assertEquals(false, tr2.getSigned());
	}
	@Test
	public void testWeek() {
		assertEquals(1, tr1.getWeek());
		assertEquals(2, tr2.getWeek());
	}
	@Test
	public void testProjectGroup() {
		assertEquals("group1", tr1.getProjectGroup());
		assertEquals("group2", tr2.getProjectGroup());
	}
	@Test
	public void testUser() {
		assertEquals("test", tr1.getUser().getUsername());
	}
	@Test
	public void testReportId() {
		assertEquals(0, tr1.getID());
		assertEquals(1, tr2.getID());
	}
	@Test
	public void testActivities() {
		int i = 0;
		assertEquals(ActivityType.SDP, tr1.getActivities().get(i).getType());
		assertEquals(10, tr1.getActivities().get(i).getLength());
		
		i++;
		assertEquals(ActivityType.SRS, tr1.getActivities().get(i).getType());
		assertEquals(20, tr1.getActivities().get(i).getLength());
		
		i++;
		assertEquals(ActivityType.SVVS, tr1.getActivities().get(i).getType());
		assertEquals(30, tr1.getActivities().get(i).getLength());
		
		i++;
		assertEquals(ActivityType.STLDD, tr1.getActivities().get(i).getType());
		assertEquals(400, tr1.getActivities().get(i).getLength());
		
		i++;
		assertEquals(ActivityType.SVVI, tr1.getActivities().get(i).getType());
		assertEquals(500, tr1.getActivities().get(i).getLength());
		
		i++;
		assertEquals(ActivityType.SDDD, tr1.getActivities().get(i).getType());
		assertEquals(600, tr1.getActivities().get(i).getLength());
		
		i++;
		assertEquals(ActivityType.SVVR, tr1.getActivities().get(i).getType());
		assertEquals(700, tr1.getActivities().get(i).getLength());
		
		i++;
		assertEquals(ActivityType.SSD, tr1.getActivities().get(i).getType());
		assertEquals(800, tr1.getActivities().get(i).getLength());

	}
}
