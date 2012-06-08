package nz.ac.victoria.ecs.kpsmart.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.junit.Test;

import com.google.inject.AbstractModule;

public class LoggingEntityManagerTest extends EntityManagerTest {
	@Before
	@Override
	public void overloadGuice() {
		this.mockState = mock(State.class);
		this.mockReport = mock(Report.class);
		this.mockLog = mock(Log.class);
		
		GuiceServletContextListner.createNewInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(State.class).toInstance(mockState);
				bind(Report.class).toInstance(mockReport);
				bind(Log.class).toInstance(mockLog);
			}
		});
	}
	
	@Test
	public void testUpdateEvent() {
		Carrier c = new Carrier("a");
		CarrierUpdateEvent cue = new CarrierUpdateEvent(c);
		
		new LoggingEntityManager().performEvent(cue);
		
		assertEquals(0, c.getRelateEventID().getId());
		verify(this.mockLog).save(cue);
		verify(this.mockState).save(c);
	}
	
	@Test
	public void testUpdateGeneralMethodEvent() {
		Carrier c = new Carrier("a");
		CarrierUpdateEvent cue = new CarrierUpdateEvent(c);
		
		new LoggingEntityManager().performEvent((EntityOperationEvent) cue);
		assertEquals(0, c.getRelateEventID().getId());
		verify(this.mockLog).save(cue);
		verify(this.mockState).save(c);
	}
	
	@Test
	public void testDeleteEvent() {
		Carrier c = new Carrier("a");
		CarrierDeleteEvent cue = new CarrierDeleteEvent(c);
		
		new LoggingEntityManager().performEvent(cue);
		verify(this.mockLog).save(cue);
		verify(this.mockState).delete(c);
	}
	
	@Test
	public void testDeleteGeneralMethodEvent() {
		Carrier c = new Carrier("a");
		CarrierDeleteEvent cue = new CarrierDeleteEvent(c);
		
		new LoggingEntityManager().performEvent((EntityOperationEvent) cue);
		verify(this.mockLog).save(cue);
		verify(this.mockState).delete(c);
	}
	
	@SuppressWarnings("serial")
	@Test(expected=IllegalStateException.class)
	public void testNonsensicalEventSubtype() {
		EntityOperationEvent<? extends StorageEntity> newEvent = new EntityOperationEvent<StorageEntity>(null) {
		};
		
		new LoggingEntityManager().performEvent(newEvent);
	}
	
	@Test
	public void testEventsAtPoint() {
		State newState = mock(State.class);
		when(this.mockState.getAtEventID(5)).thenReturn(newState);
		
		Report newReport = mock(Report.class);
		when(this.mockReport.getAtEventID(5)).thenReturn(newReport);
		
		Log newLog = mock(Log.class);
		when(this.mockLog.getAtEventID(5)).thenReturn(newLog);
		
		EntityManager newEntity = new LoggingEntityManager().getEntityManagerAtEventPoint(5);
		
		verify(this.mockState).getAtEventID(5);
		verify(this.mockReport).getAtEventID(5);
		verify(this.mockLog).getAtEventID(5);
		assertSame(newState, newEntity.getData());
		assertSame(newReport, newEntity.getReports());
		assertSame(newLog, newEntity.getLog());
	}
	
	@Test
	public void testInjectedObjects() {
		EntityManager entity = new LoggingEntityManager();
		
		assertSame(this.mockState, entity.getData());
		assertSame(this.mockReport, entity.getReports());
		assertSame(this.mockLog, entity.getLog());
	}
}
