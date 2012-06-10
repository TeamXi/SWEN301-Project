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
		
		final List<Route> route = this.route.calculateRoute(
				Priority.Domestic_Air, 
				this.state.getLocationForName("A"), 
				this.state.getLocationForName("B"), 
				1, 1);
		
		assertEquals(this.state.getAllRoute(), route);
	}
	
	@Test @InjectOnCall
	public void testBestRoute() {
		this.state.save(new Carrier("Foo"));
		this.state.save(new Carrier("Bar"));
		this.state.save(new Location("A", 0, 0, false));
		this.state.save(new Location("B", 0, 1, false));
		final Route routeA = new Route(
				TransportMeans.Air, 
				this.state.getLocationForName("A"), 
				this.state.getLocationForName("B"), 
				this.state.getCarrier("Foo"));
		routeA.setCarrierVolumeUnitCost(1);
		routeA.setCarrierWeightUnitCost(2);
		this.state.save(routeA);
		final Route routeB = new Route(
				TransportMeans.Air, 
				this.state.getLocationForName("A"), 
				this.state.getLocationForName("B"), 
				this.state.getCarrier("Bar"));
		routeB.setCarrierVolumeUnitCost(2);
		routeB.setCarrierWeightUnitCost(1);
		this.state.save(routeB);
		
		final List<Route> expectRouteA = this.route.calculateRoute(
				Priority.Domestic_Air, 
				this.state.getLocationForName("A"), 
				this.state.getLocationForName("B"), 
				10, 1);
		assertEquals("Bar", expectRouteA.get(0).getCarrier().getName());
		
		final List<Route> expectRouteB = this.route.calculateRoute(
				Priority.Domestic_Air, 
				this.state.getLocationForName("A"), 
				this.state.getLocationForName("B"), 
				1, 10);
		assertEquals("Foo", expectRouteB.get(0).getCarrier().getName());
		
	}
	
	@Test @InjectOnCall
	public void testBestRoute2() {
		this.state.save(new Carrier("Foo"));
		this.state.save(new Carrier("Bar"));
		this.state.save(new Location("A", 0, 0, false));
		this.state.save(new Location("B", 0, 1, false));
		this.state.save(new Location("C", 1, 1, false));
		final Route routeA = new Route(
				TransportMeans.Air, 
				this.state.getLocationForName("A"), 
				this.state.getLocationForName("B"), 
				this.state.getCarrier("Foo"));
		routeA.setCarrierVolumeUnitCost(1);
		routeA.setCarrierWeightUnitCost(4);
		this.state.save(routeA);
		final Route routeB = new Route(
				TransportMeans.Air, 
				this.state.getLocationForName("A"), 
				this.state.getLocationForName("C"), 
				this.state.getCarrier("Bar"));
		routeB.setCarrierVolumeUnitCost(2);
		routeB.setCarrierWeightUnitCost(1);
		this.state.save(routeB);
		final Route routeC = new Route(
				TransportMeans.Air, 
				this.state.getLocationForName("C"), 
				this.state.getLocationForName("B"), 
				this.state.getCarrier("Bar"));
		routeC.setCarrierVolumeUnitCost(2);
		routeC.setCarrierWeightUnitCost(1);
		this.state.save(routeC);
		
		final List<Route> expectRouteA = this.route.calculateRoute(
				Priority.Domestic_Air, 
				this.state.getLocationForName("A"), 
				this.state.getLocationForName("B"), 
				10, 1);
		assertEquals("Bar", expectRouteA.get(0).getCarrier().getName());
		
		final List<Route> expectRouteC = this.route.calculateRoute(
				Priority.Domestic_Air, 
				this.state.getLocationForName("A"), 
				this.state.getLocationForName("B"), 
				1, 10);
		assertEquals("Foo", expectRouteC.get(0).getCarrier().getName());
		
	}

}
