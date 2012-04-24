package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import nz.ac.victoria.ecs.kpsmart.DataTest;
import nz.ac.victoria.ecs.kpsmart.InjectOnCall;
import nz.ac.victoria.ecs.kpsmart.integration.EntityManager;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.CustomerPriceUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.MailDeliveryUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.ReportManager.AmountOfMail;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

public class HibernateReportTest extends DataTest {
	@Inject
	private EntityManager manager;
	
	@Before @InjectOnCall
	public void createReportData() {
//		CustomerPrice p = CustomerPrice.newInstance();
//		p.setStartLocation(null);
//		p.setEndLocation(manager.getData().getLocationForName("Rome"));
//		p.setPriority(Priority.International_Air);
//		manager.performEvent(new CustomerPriceUpdateEvent(p));
		
		MailDelivery m = new MailDelivery();
		m.setPriority(Priority.International_Air);
		m.setRoute((List<Route>) manager.getData().getRoutesBetween(manager.getData().getLocationForName("Wellington"), manager.getData().getLocationForName("Rome"), Priority.International_Air));
		m.setVolume(1);
		m.setWeight(1);
		manager.performEvent(new MailDeliveryUpdateEvent(m));
	}
	
	@Test
	public void testGetAmountsOfMailForAllRoutes() {
		Collection<AmountOfMail> aom = this.manager.getReports().getAmountsOfMailForAllRoutes();
		
		assertEquals(30, aom.size());
	}
	
	@Test
	public void testGetExpenditure() {
		double expenditure = this.manager.getReports().getTotalExpenditure();
		
		assertTrue(((Double) 10.9).equals(expenditure));
	}
	
	@Test
	public void testGetRevenue() {
		double revenue = this.manager.getReports().getTotalRevenue();
		
		assertTrue(((Double) 1.0).equals(revenue));
	}
}
