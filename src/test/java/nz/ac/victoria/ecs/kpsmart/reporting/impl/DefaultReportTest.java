package nz.ac.victoria.ecs.kpsmart.reporting.impl;

import static org.junit.Assert.assertEquals;

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
}
