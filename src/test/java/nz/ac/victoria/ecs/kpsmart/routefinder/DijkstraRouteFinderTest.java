package nz.ac.victoria.ecs.kpsmart.routefinder;

import static org.junit.Assert.assertEquals;

import java.util.List;

import nz.ac.victoria.ecs.kpsmart.InjectOnCall;
import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.entities.state.TransportMeans;
import nz.ac.victoria.ecs.kpsmart.util.HibernateDataTest;

import org.junit.Before;
import org.junit.Test;


public class DijkstraRouteFinderTest extends HibernateDataTest {
	private DijkstraRouteFinder route;
	
	@Before
	public void setUpRouteFinder() {
		this.route = new DijkstraRouteFinder();
	}
	
	@Test @InjectOnCall
	public void testSingleEdge() {
		this.state.save(new Carrier("Foo"));
		this.state.save(new Location("A", 0, 0, false));
		this.state.save(new Location("B", 1, 1, false));
		this.state.save(new Route(
				TransportMeans.Air, 
				this.state.getLocationForName("A"), 
				this.state.getLocationForName("B"), 
				this.state.getCarrier("Foo")));
		
		List<Route> route = this.route.calculateRoute(
				Priority.Domestic_Air, 
				this.state.getLocationForName("A"), 
				this.state.getLocationForName("B"), 
				1, 1);
		
		assertEquals(this.state.getAllRoute(), route);
	}

}
