package nz.ac.victoria.ecs.kpsmart.state;

import java.util.List;

import com.google.inject.Inject;

import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.StateManipulator;

@InjectOnContruct
public final class DijkstraRouteFinder implements RouteFinder {
	@Inject
	private StateManipulator state;
	
	@Override
	public List<Route> calculateRoute(Priority priority, Location startPoint, Location endPoint, float weight, float volume) {
		return null;
	}

}
