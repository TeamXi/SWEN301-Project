package nz.ac.victoria.ecs.kpsmart.util;

import java.util.Date;
import java.util.List;

import nz.ac.victoria.ecs.kpsmart.entities.state.Route;

public class RouteDurationCalculator {
	private static final long msinhour = 60 * 60 * 1000;
	
	public static long calculate(List<Route> routes, Date startTime) {
		long zero = startTime.getTime();
		long duration = 0;
		
		for(Route route : routes) {
			long rstart = route.getStartingTime().getTime() - zero;
			long diff = duration-rstart;
			if(diff <= 0) { // route doesn't start till future
				// can go when route created
				long depart = -diff;
				duration += depart;
			}
			else { // route has already started
				long freq = route.getFrequency() * msinhour;
				long n = diff/freq + 1;
				long depart = n*freq - diff;
				duration += depart;
			}
			
			duration += route.getDuration()*msinhour;
		}
		
		return duration;
	}
}
