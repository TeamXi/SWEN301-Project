package nz.ac.victoria.ecs.kpsmart.routeduration;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.util.RouteDurationCalculator;

public class RouteDurationTest {
	private static final Date now = new Date();
	private static final long msinhour = 60 * 60 * 1000;
	
	@Test
	public void testZero() {
		List<Route> routes = new ArrayList<Route>();
		Route r1 = Route.newInstance();
		r1.setStartingTime(now);
		r1.setFrequency(10);
		r1.setDuration(10);
		routes.add(r1);
		
		assertEquals(10*msinhour, RouteDurationCalculator.calculate(routes, now));
	}
	
	@Test
	public void testDelay() {
		List<Route> routes = new ArrayList<Route>();
		Route r1 = Route.newInstance();
		r1.setStartingTime(new Date(now.getTime()+1000));
		r1.setFrequency(10);
		r1.setDuration(10);
		routes.add(r1);
		
		assertEquals(10*msinhour+1000, RouteDurationCalculator.calculate(routes, now));
	}
	
	@Test
	public void testPast() {
		List<Route> routes = new ArrayList<Route>();
		Route r1 = Route.newInstance();
		r1.setStartingTime(new Date(now.getTime()-1000));
		r1.setFrequency(10);
		r1.setDuration(10);
		routes.add(r1);
		
		assertEquals(10*msinhour+10*msinhour-1000, RouteDurationCalculator.calculate(routes, now));
	}
	
	@Test
	public void testMulti1() {
		List<Route> routes = new ArrayList<Route>();
		Route r1 = Route.newInstance();
		r1.setStartingTime(new Date(now.getTime()-1000));
		r1.setFrequency(10);
		r1.setDuration(10);
		routes.add(r1);
		Route r2 = Route.newInstance();
		r2.setStartingTime(new Date(now.getTime()));
		r2.setFrequency(1);
		r2.setDuration(3);
		routes.add(r2);
		
		assertEquals(10*msinhour+10*msinhour+3*msinhour, RouteDurationCalculator.calculate(routes, now));
	}
	
	@Test
	public void testMulti2() {
		List<Route> routes = new ArrayList<Route>();
		Route r1 = Route.newInstance();
		r1.setStartingTime(new Date(now.getTime()));
		r1.setFrequency(10);
		r1.setDuration(10);
		routes.add(r1);
		Route r2 = Route.newInstance();
		r2.setStartingTime(new Date(now.getTime()-1));
		r2.setFrequency(1);
		r2.setDuration(3);
		routes.add(r2);
		
		assertEquals(10*msinhour+3*msinhour+1*msinhour-1, RouteDurationCalculator.calculate(routes, now));
	}
}
