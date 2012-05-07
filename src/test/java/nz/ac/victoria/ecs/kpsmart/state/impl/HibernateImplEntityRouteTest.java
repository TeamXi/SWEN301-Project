package nz.ac.victoria.ecs.kpsmart.state.impl;

import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.entities.state.EntityID;
import nz.ac.victoria.ecs.kpsmart.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.entities.state.TransportMeans;
import nz.ac.victoria.ecs.kpsmart.state.DuplicateEntityException;
import nz.ac.victoria.ecs.kpsmart.util.HibernateDataTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HibernateImplEntityRouteTest extends HibernateDataTest {
	private Location start, end;
	private Carrier carrier;
	
	@Before
	public void setUpLocations() {
		this.state.save(new Location("foo", 1, 1, false));
		this.start = this.state.getLocationForName("foo");
		this.state.save(new Location("bar", 2, 2, true));
		this.end = this.state.getLocationForName("bar");
		
		this.state.save(new Carrier("a"));
		this.carrier = this.state.getCarrier("a");
	}
	
	@Test
	public void testAddRoute() {
		Route r = new Route(TransportMeans.Air, start, end, carrier);
		
		this.state.save(r);
		
		assertEquals(r, this.state.getAllRoute().get(0));
		assertEquals(r, this.state.getRouteByID(1));
	}
	
	@Test
	public void testDeleteAndReCreate() {
		this.state.save(new Route(TransportMeans.Air, start, end, carrier));
		this.state.delete(this.state.getRouteByID(1));
		
		assertEquals(0, this.state.getAllRoute().size());
		assertNull(this.state.getRouteByID(0));
	}
	
	@Test
	public void testDeleteAndRecreate() {
		Route r = new Route(TransportMeans.Air, start, end, carrier);
		r.setUid(new EntityID(2));
		
		this.state.save(new Route(TransportMeans.Air, start, end, carrier));
		this.state.delete(this.state.getRouteByID(1));
		this.state.save(new Route(TransportMeans.Air, start, end, carrier));
		
		assertEquals(r, this.state.getAllRoute().get(0));
		assertEquals(r, this.state.getRouteByID(2));
	}
	
	@Test(expected=DuplicateEntityException.class)
	public void testAddTwiceFails() {
		this.state.save(new Route(TransportMeans.Air, start, end, carrier));
		this.state.save(new Route(TransportMeans.Air, start, end, carrier));
	}
	
	@Test
	public void testPrimaryKeyDoesNotConflict() {
		this.state.save(new Route(TransportMeans.Air, start, end, carrier));
		this.state.save(new Route(TransportMeans.Land, start, end, carrier));
		this.state.save(new Route(TransportMeans.Sea, start, end, carrier));
	}
}
