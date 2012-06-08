package nz.ac.victoria.ecs.kpsmart.state.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nz.ac.victoria.ecs.kpsmart.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.entities.state.Direction;
import nz.ac.victoria.ecs.kpsmart.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.state.DuplicateEntityException;
import nz.ac.victoria.ecs.kpsmart.util.HibernateDataTest;

import org.junit.Before;
import org.junit.Test;

public class HibernateImplEntityCustomerPriceTest extends HibernateDataTest {
	private Location location;
	
	@Before
	public void createExampleData() {
		this.state.save(new Location("a", 1, 1, true));
		this.location = this.state.getLocationForName("a");
	}
	
	@Test
	public void createNewCustomerPrice() {
		CustomerPrice price = new CustomerPrice(this.location, Direction.To, Priority.International_Air);
		
		this.state.save(price);
		
		assertEquals(price, this.state.getCustomerPrice(null, location, Priority.International_Air));
	}
	
	@Test
	public void createAndDelete() {
		this.state.save(new CustomerPrice(this.location, Direction.To, Priority.International_Air));
		this.state.delete(this.state.getCustomerPrice(null, location, Priority.International_Air));
		
		assertNull(this.state.getCustomerPrice(null, location, Priority.International_Air));
	}
	
	@Test
	public void createDeleteAndRecreate() {
		this.state.save(new CustomerPrice(this.location, Direction.To, Priority.International_Air));
		this.state.delete(this.state.getCustomerPrice(null, location, Priority.International_Air));
		this.state.save(new CustomerPrice(this.location, Direction.From, Priority.International_Standard));
		
		CustomerPrice expected = new CustomerPrice(this.location, Direction.From, Priority.International_Standard);
		expected.setId(2);
		
		assertNull(this.state.getCustomerPrice(null, location, Priority.International_Air));
		assertEquals(expected, this.state.getCustomerPrice(location, null, Priority.International_Standard));
	}
	
	@Test
	public void testCustomerPricesDontoverlap() {
		this.state.save(new CustomerPrice(this.location, Direction.To, Priority.International_Air));
		this.state.save(new CustomerPrice(this.location, Direction.From, Priority.International_Air));
		this.state.save(new CustomerPrice(this.location, Direction.To, Priority.International_Standard));
		this.state.save(new CustomerPrice(this.location, Direction.From, Priority.International_Standard));
	}
	
	@Test(expected=DuplicateEntityException.class)
	public void testCreateTwoCustomerPricesForTheSameRouteAndSamePriority() {
		this.state.save(new CustomerPrice(this.location, Direction.To, Priority.International_Air));
		this.state.save(new CustomerPrice(this.location, Direction.To, Priority.International_Air));
	}
}
