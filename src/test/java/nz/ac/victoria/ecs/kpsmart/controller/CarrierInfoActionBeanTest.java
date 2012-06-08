package nz.ac.victoria.ecs.kpsmart.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import net.sourceforge.stripes.mock.MockRoundtrip;
import nz.ac.victoria.ecs.kpsmart.entities.logging.CarrierDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.CarrierUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.util.StripesActionBeanTest;

import org.junit.Test;


public class CarrierInfoActionBeanTest extends StripesActionBeanTest {
	@Test
	public void testNewCarrier() throws Exception {
		MockRoundtrip trip = new MockRoundtrip(this.context, CarrierInfoActionBean.class);
		
		trip.setParameter("name", "a");
		
		trip.execute("new");
		
		assertTrue(trip.getResponse().getOutputString().contains("\"status\":true"));
		verify(this.manager).performEvent(new CarrierUpdateEvent(new Carrier("a")));
	}
	
	@Test
	public void testRejectNewEmptyNameCarrier() throws Exception {
		MockRoundtrip trip = new MockRoundtrip(this.context, CarrierInfoActionBean.class);
		
		trip.setParameter("name", "");
		
		trip.execute("new");
		
		assertTrue(trip.getResponse().getOutputString().contains("\"status\":false"));
		verify(this.manager, never()).performEvent(new CarrierUpdateEvent(new Carrier("a")));
	}
	
	@Test
	public void testRejectCreatingIdenticalCarriers() throws Exception {
		when(this.state.getCarrier("a")).thenReturn(null, new Carrier("a"));
		
		MockRoundtrip trip = new MockRoundtrip(this.context, CarrierInfoActionBean.class);
		trip.setParameter("name", "a");
		trip.execute("new");
		assertTrue(trip.getResponse().getOutputString().contains("\"status\":true"));
		
		MockRoundtrip trip2 = new MockRoundtrip(this.context, CarrierInfoActionBean.class);
		trip2.setParameter("name", "a");
		trip2.execute("new");
		assertTrue(trip2.getResponse().getOutputString().contains("\"status\":false"));
		
		verify(this.manager, times(1)).performEvent(new CarrierUpdateEvent(new Carrier("a")));
	}
	
	@Test
	public void testUpdateCarrier() throws Exception {
		Carrier c = new Carrier("a");
		c.setId(1);
		when(this.state.getCarrier(1)).thenReturn(c);
		
		MockRoundtrip trip = new MockRoundtrip(this.context, CarrierInfoActionBean.class);
		trip.setParameter("carrierId", "1");
		trip.setParameter("name", "b");
		trip.execute("update");
		assertTrue(trip.getResponse().getOutputString().contains("\"status\":true"));
		
		Carrier newCarrier = new Carrier("b");
		newCarrier.setId(1);
		verify(this.state).getCarrier(1);
		verify(this.manager).performEvent(new CarrierUpdateEvent(newCarrier));
	}
	
	@Test
	public void testUpdateCarrierWithEmptyNameIsRejected() throws Exception {
		Carrier c = new Carrier("a");
		c.setId(1);
		when(this.state.getCarrier(1)).thenReturn(c);
		
		MockRoundtrip trip = new MockRoundtrip(this.context, CarrierInfoActionBean.class);
		trip.setParameter("carrierId", "1");
		trip.setParameter("name", "");
		trip.execute("update");
		assertTrue(trip.getResponse().getOutputString().contains("\"status\":false"));
		
		Carrier newCarrier = new Carrier("b");
		newCarrier.setId(1);
		verify(this.manager, never()).performEvent(new CarrierUpdateEvent(newCarrier));
	}
	
	@Test
	public void testUpdateCarrierWithNonExistentIDFals() throws Exception {
		MockRoundtrip trip = new MockRoundtrip(this.context, CarrierInfoActionBean.class);
		trip.setParameter("carrierId", "1");
		trip.setParameter("name", "b");
		trip.execute("update");
		assertTrue(trip.getResponse().getOutputString().contains("\"status\":false"));
		
		Carrier newCarrier = new Carrier("b");
		verify(this.state).getCarrier(1);
		verify(this.manager, never()).performEvent(new CarrierUpdateEvent(newCarrier));
	}
	
	@Test
	public void testDelete() throws Exception {
		Carrier c = new Carrier("a");
		c.setId(1);
		when(this.state.getCarrier(1)).thenReturn(c);
		
		MockRoundtrip trip = new MockRoundtrip(this.context, CarrierInfoActionBean.class);
		trip.setParameter("carrierId", "1");
		trip.execute("delete");
		assertTrue(trip.getResponse().getOutputString().contains("\"status\":true"));
		
		verify(this.state).getCarrier(1);
		verify(this.manager, times(1)).performEvent(new CarrierDeleteEvent(c));
	}
	
	@Test
	public void testDeleteOfInvalidCarrierReturnsFalse() throws Exception {
		MockRoundtrip trip = new MockRoundtrip(this.context, CarrierInfoActionBean.class);
		trip.setParameter("carrierId", "1");
		trip.setParameter("name", "b");
		trip.execute("delete");
		assertTrue(trip.getResponse().getOutputString().contains("\"status\":false"));
		
		verify(this.state).getCarrier(1);
		verify(this.manager, never()).performEvent(new CarrierDeleteEvent(new Carrier("a")));
	}
	
	@Test
	public void testUpdateCarrierWithNoNameChange() throws Exception {
		Carrier c = new Carrier("a");
		c.setId(1);
		when(this.state.getCarrier(1)).thenReturn(c);
		
		MockRoundtrip trip = new MockRoundtrip(this.context, CarrierInfoActionBean.class);
		trip.setParameter("carrierId", "1");
		trip.setParameter("name", "a");
		trip.execute("update");
		assertTrue(trip.getResponse().getOutputString().contains("\"status\":true"));
	}
}
