package nz.ac.victoria.ecs.kpsmart.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import net.sourceforge.stripes.mock.MockRoundtrip;
import nz.ac.victoria.ecs.kpsmart.GuiceServletContextListner;
import nz.ac.victoria.ecs.kpsmart.entities.logging.CarrierDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.CarrierUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.integration.EntityManager;
import nz.ac.victoria.ecs.kpsmart.logging.Log;
import nz.ac.victoria.ecs.kpsmart.reporting.Report;
import nz.ac.victoria.ecs.kpsmart.state.State;
import nz.ac.victoria.ecs.kpsmart.util.StripesActionBeanTest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.AbstractModule;

public class CarrierInfoActionBeanTest extends StripesActionBeanTest {
	private EntityManager manager;
	private State state;
	private Log log;
	private Report report;
	
	@BeforeClass
	public static void guiceInit() {
		GuiceServletContextListner.init();
	}
	
	@Before
	public void setUpMocks() {
		this.manager = mock(EntityManager.class);
		this.state = mock(State.class);
		this.log = mock(Log.class);
		this.report = mock(Report.class);
		
		when(this.manager.getData()).thenReturn(state);
		when(this.manager.getLog()).thenReturn(log);
		when(this.manager.getReports()).thenReturn(report);
		
		GuiceServletContextListner.createNewInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(State.class).toInstance(state);
				bind(Log.class).toInstance(log);
				bind(Report.class).toInstance(report);
				bind(EntityManager.class).toInstance(manager);
			}
		});
	}
	
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
		MockRoundtrip trip = new MockRoundtrip(this.context, CarrierInfoActionBean.class);
		trip.setParameter("name", "a");
		trip.execute("new");
		assertTrue(trip.getResponse().getOutputString().contains("\"status\":true"));
		
		MockRoundtrip trip2 = new MockRoundtrip(this.context, CarrierInfoActionBean.class);
		trip2.setParameter("name", "a");
		trip2.execute("new");
		assertTrue(trip.getResponse().getOutputString().contains("\"status\":false"));
		
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
		verify(this.state).getCarrier(1);
		verify(this.manager, never()).performEvent(new CarrierUpdateEvent(newCarrier));
	}
	
	@Test
	public void testUpdateCarrierWithNonExistentIDFals() throws Exception {
		MockRoundtrip trip = new MockRoundtrip(this.context, CarrierInfoActionBean.class);
		trip.setParameter("carrierId", "1");
		trip.setParameter("name", "b");
		trip.execute("update");
		assertTrue(trip.getResponse().getOutputString().contains("\"status\":failed"));
		
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
}
