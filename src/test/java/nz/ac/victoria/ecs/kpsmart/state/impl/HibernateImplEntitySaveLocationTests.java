package nz.ac.victoria.ecs.kpsmart.state.impl;

import static org.junit.Assert.assertEquals;
import nz.ac.victoria.ecs.kpsmart.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.DuplicateEntityException;
import nz.ac.victoria.ecs.kpsmart.util.HibernateDataTest;

import org.junit.Test;

public class HibernateImplEntitySaveLocationTests extends HibernateDataTest {
	@Test
	public void testSaveLocation() {
		Location l = new Location("a", 0, 0, true);
		
		this.state.save(l);
		
		assertEquals(l, this.state.getLocationForName("a"));
	}
	
	@Test
	public void testSaveTwoLocations() {
		Location l = new Location("a", 0, 0, true);
		
		this.state.save(l);
		Location newL = this.state.getLocationForName("a");
		assertEquals(l, newL);
		
		this.state.delete(newL);
		
		Location l2 = new Location("b", 0, 0, true);
		
		this.state.save(l2);
		Location newL2 = this.state.getLocationForName("b");
		assertEquals(l2, newL2);
		assertEquals(1, this.state.getAllLocations().size());
	}
	
	@Test(expected=DuplicateEntityException.class)
	public void testSaveTwoLocationsFails() {
		Location l = new Location("a", 0, 0, true);
		
		this.state.save(l);
		Location newL = this.state.getLocationForName("a");
		assertEquals(l, newL);
		
		Location l2 = new Location("b", 0, 0, true);
		
		this.state.save(l2);
		Location newL2 = this.state.getLocationForName("b");
		assertEquals(l2, newL2);
		assertEquals(2, this.state.getAllLocations().size());
	}
}
