package nz.ac.victoria.ecs.kpsmart.state.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
import java.util.Calendar;

import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.entities.state.Direction;
import nz.ac.victoria.ecs.kpsmart.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.entities.state.TransportMeans;
import nz.ac.victoria.ecs.kpsmart.state.DuplicateEntityException;
import nz.ac.victoria.ecs.kpsmart.util.HibernateDataTest;

import org.junit.Before;
import org.junit.Test;

public class HibernateImplSaveMailDeliveryTests extends HibernateDataTest{
	
	private Route route;
	
	@Before
	public void setUpRoute(){
		this.state.save(new Location("a", 1, 1, true));
		this.state.save(new Location("b", 2, 2, false));
		this.state.save(new Carrier("foo"));
		
		this.state.save(new CustomerPrice(this.state.getLocationForName("a"), Direction.From, Priority.International_Air));
		
		this.state.save(new Route(
				TransportMeans.Air, 
				this.state.getLocationForName("a"), 
				this.state.getLocationForName("b"), 
				this.state.getCarrier("foo")));
		this.route = this.state.getAllRoute().get(0);
		
		Date stat = Calendar.getInstance().getTime();
		stat.setTime(0);
		this.route.setStartingTime(stat);
		this.route.setFrequency(1);
	}
	
	@Test
	public void testSaveMailDelivery() {
		
		MailDelivery m = new MailDelivery(Arrays.asList(route), Priority.International_Air,1,1,Calendar.getInstance().getTime());
		
		this.state.save(m);
		
		assertEquals(Arrays.asList(m), this.state.getAllMailDeliveries());
	}
	
	@Test
	public void testSaveTwoMailDeliveries() {
		MailDelivery m = new MailDelivery(new ArrayList<Route>(Arrays.asList(route)),Priority.International_Air,1,1,Calendar.getInstance().getTime());
		
		this.state.save(m);
		MailDelivery newM = this.state.getAllMailDeliveries().iterator().next();
		assertEquals(m, newM);
		
		this.state.delete(newM);
		
		MailDelivery m2 = new MailDelivery(new ArrayList<Route>(Arrays.asList(route)),Priority.International_Air,1,1,Calendar.getInstance().getTime());
		
		this.state.save(m2);
		MailDelivery newM2 = this.state.getAllMailDeliveries().iterator().next();
		assertEquals(m2, newM2);
		assertEquals(1, this.state.getAllMailDeliveries().size());
	}
}
