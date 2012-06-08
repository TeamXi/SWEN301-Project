package nz.ac.victoria.ecs.kpsmart.routefinder;

import java.util.List;

import nz.ac.victoria.ecs.kpsmart.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.entities.state.Route;

/**
 * Defines an interface that will find a route for mail.
 * 
 * @author hodderdani
 *
 */
public interface RouteFinder {
	/**
	 * Calculate a route between two points with a given priority.
	 * 
	 * @param proprity	The priority to search for first.
	 * @param startPoint	The point to start from
	 * @param endPoint	The point to end at
	 * @param weight	The weight of the package
	 * @param volume	The volume of the package
	 * @return	A list of routes that is the lowest cost between the given locations, or null if there is no route
	 */
	List<Route> calculateRoute(Priority priority, Location startPoint, Location endPoint, float weight, float volume);
}
