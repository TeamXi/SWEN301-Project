package nz.ac.victoria.ecs.kpsmart.routefinder;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.sourceforge.stripes.action.After;
import nz.ac.victoria.ecs.kpsmart.GuiceServletContextListner;
import nz.ac.victoria.ecs.kpsmart.InjectOnCall;
import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.entities.state.TransportMeans;
import nz.ac.victoria.ecs.kpsmart.integration.HibernateModule;
import nz.ac.victoria.ecs.kpsmart.state.State;
import nz.ac.victoria.ecs.kpsmart.state.impl.HibernateState;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Inject;

public class DijkstraRouteFinderTest {
	@BeforeClass
	public static void setUpBeforeClass() {
		GuiceServletContextListner.initNoData();
	}
	
	@Before
	public void setUp() throws Exception {
		GuiceServletContextListner.createNewInjector(
				new HibernateState.Module(), 
				new HibernateModule("hibernate.memory.properties"));
		
		this.route = new DijkstraRouteFinder();
	}
	
	@After
	public void tearDown() {
		GuiceServletContextListner.restorePreviousInjector();
	}
	
	private DijkstraRouteFinder route;
	
	@Inject
	private State state;

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
