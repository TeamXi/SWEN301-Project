package reporting.impl;

import static org.junit.Assert.assertEquals;
import nz.ac.victoria.ecs.kpsmart.entities.logging.CarrierUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.reporting.impl.DefaultReport;
import nz.ac.victoria.ecs.kpsmart.util.HibernateDataTest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class DefaultReportTest extends HibernateDataTest {
	private DefaultReport report;
	
	@Before
	public void createReport() {
		this.report = new DefaultReport();
	}
	
	@Test
	public void testNumberOfEventsIsZero() {
		assertEquals(0, this.report.getNumberOfEvents());
	}
	
	@Test @Ignore
	public void testNumberOfEventsIsNonZero() {
		this.entity.performEvent(new CarrierUpdateEvent(new Carrier("a")));
		
		assertEquals(1, this.report.getNumberOfEvents());
	}
}
