package nz.ac.victoria.ecs.kpsmart.integration;

import nz.ac.victoria.ecs.kpsmart.GuiceServletContextListner;
import nz.ac.victoria.ecs.kpsmart.entities.logging.CarrierDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.CarrierUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.EntityOperationEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.entities.state.StorageEntity;
import nz.ac.victoria.ecs.kpsmart.logging.Log;
import nz.ac.victoria.ecs.kpsmart.reporting.Report;
import nz.ac.victoria.ecs.kpsmart.state.State;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.AbstractModule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class EntityManagerTest {
	@BeforeClass
	public static void init() {
		GuiceServletContextListner.init();
	}
	
	protected State mockState;
	protected Report mockReport;
	protected Log mockLog = null;
	
	@Before
	public void overloadGuice() {
		this.mockState = mock(State.class);
		this.mockReport = mock(Report.class);
		
		GuiceServletContextListner.createNewInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(State.class).toInstance(mockState);
				bind(Report.class).toInstance(mockReport);
			}
		});
	}
	
	@After
	public void restoreInjector() {
		GuiceServletContextListner.restorePreviousInjector();
	}
	
	@Test
	public void testUpdateEvent() {
		Carrier c = new Carrier("a");
		CarrierUpdateEvent cue = new CarrierUpdateEvent(c);
		
		new EntityManager().performEvent(cue);
		
		assertEquals(null, c.getRelateEventID());
		verify(this.mockState).save(c);
	}
	
	@Test
	public void testUpdateGeneralMethodEvent() {
		Carrier c = new Carrier("a");
		CarrierUpdateEvent cue = new CarrierUpdateEvent(c);
		
		new EntityManager().performEvent((EntityOperationEvent) cue);
		assertEquals(null, c.getRelateEventID());
		verify(this.mockState).save(c);
	}
	
	@Test
	public void testDeleteEvent() {
		Carrier c = new Carrier("a");
		CarrierDeleteEvent cue = new CarrierDeleteEvent(c);
		
		new EntityManager().performEvent(cue);

		verify(this.mockState).delete(c);
	}
	
	@Test
	public void testDeleteGeneralMethodEvent() {
		Carrier c = new Carrier("a");
		CarrierDeleteEvent cue = new CarrierDeleteEvent(c);
		
		new EntityManager().performEvent((EntityOperationEvent) cue);
		verify(this.mockState).delete(c);
	}
	
	@SuppressWarnings("serial")
	@Test(expected=IllegalStateException.class)
	public void testNonsensicalEventSubtype() {
		EntityOperationEvent<? extends StorageEntity> newEvent = new EntityOperationEvent<StorageEntity>(null) {
		};
		
		new EntityManager().performEvent(newEvent);
	}
	
	@Test
	public void testEventsAtPoint() {
		State newState = mock(State.class);
		when(this.mockState.getAtEventID(5)).thenReturn(newState);
		
		Report newReport = mock(Report.class);
		when(this.mockReport.getAtEventID(5)).thenReturn(newReport);
		
		EntityManager newEntity = new EntityManager().getEntityManagerAtEventPoint(5);
		
		verify(this.mockState).getAtEventID(5);
		verify(this.mockReport).getAtEventID(5);
		assertSame(newState, newEntity.getData());
		assertSame(newReport, newEntity.getReports());
		assertSame(null, newEntity.getLog());
	}
	
	@Test
	public void testInjectedObjects() {
		EntityManager entity = new EntityManager();
		
		assertSame(this.mockState, entity.getData());
		assertSame(this.mockReport, entity.getReports());
		assertSame(null, entity.getLog());
	}
}
