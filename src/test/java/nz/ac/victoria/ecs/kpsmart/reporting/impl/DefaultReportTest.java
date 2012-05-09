package nz.ac.victoria.ecs.kpsmart.reporting.impl;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import nz.ac.victoria.ecs.kpsmart.entities.logging.CarrierUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.EntityOperationEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.entities.state.StorageEntity;
import nz.ac.victoria.ecs.kpsmart.logging.Log;
import nz.ac.victoria.ecs.kpsmart.state.State;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class DefaultReportTest {
	private DefaultReport report;
	private State state;
	private Log log;
	
	@Before
	public void createReport() {
		this.state = mock(State.class);
		this.log = mock(Log.class);
		this.report = new DefaultReport(this.state, this.log);
	}
	
	@Test
	public void testNumberOfEventsIsZero() {
		assertEquals(0, this.report.getNumberOfEvents());
	}
	
	@Test
	public void testNumberOfEventsIsNonZero() {
		when(this.log.getAllEvents()).thenReturn(
				Arrays.<EntityOperationEvent<? extends StorageEntity>>asList(new CarrierUpdateEvent(new Carrier("a"))));
		
		assertEquals(1, this.report.getNumberOfEvents());
	}
}
