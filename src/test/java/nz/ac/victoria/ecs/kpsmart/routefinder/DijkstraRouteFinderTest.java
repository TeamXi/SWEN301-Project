package nz.ac.victoria.ecs.kpsmart.routefinder;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.sourceforge.stripes.action.After;
import nz.ac.victoria.ecs.kpsmart.GuiceServletContextListner;
import nz.ac.victoria.ecs.kpsmart.InjectOnCall;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.TransportMeans;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.InMemoryStateManipulationModule;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.StateManipulator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class DijkstraRouteFinderTest {
	@BeforeClass
	public static void setUpBeforeClass() {
		GuiceServletContextListner.init();
	}
	
	@Before
	public void setUp() throws Exception {
		GuiceServletContextListner.overloadModules(new InMemoryStateManipulationModule(true));
		
		this.route = new DijkstraRouteFinder();
	}
	
	@After
	public void tearDown() {
		GuiceServletContextListner.restorePreviousInjector();
	}
	
	private DijkstraRouteFinder route;
	
	@Inject @Named("memory")
	private StateManipulator state;

	@Test @InjectOnCall
	public void testSingleEdge() {
		this.state.save(new Carrier("Foo"));
		this.state.save(new Location("A", 0, 0, false));
		this.state.save(new Location("B", 0, 0, false));
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
