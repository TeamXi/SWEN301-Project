package nz.ac.victoria.ecs.kpsmart.state.impl;

import static org.junit.Assert.assertEquals;
import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.state.DuplicateEntityException;
import nz.ac.victoria.ecs.kpsmart.util.HibernateDataTest;

import org.junit.Test;

public class HibernateImplEntitySaveCarrierTests  extends HibernateDataTest {
	@Test
	public void testSaveCarrier() {
		Carrier c = new Carrier("a");
		
		this.state.save(c);
		
		assertEquals(c, this.state.getCarrier("a"));
	}
	
	@Test
	public void testSaveTwoCarriers() {
		Carrier c = new Carrier("a");
		
		this.state.save(c);
		Carrier newC = this.state.getCarrier("a");
		assertEquals(c, newC);
		
		this.state.delete(newC);
		
		Carrier c2 = new Carrier("b");
		
		this.state.save(c2);
		Carrier newC2 = this.state.getCarrier("b");
		assertEquals(c2, newC2);
		assertEquals(1, this.state.getAllCarriers().size());
	}
	
	@Test(expected=DuplicateEntityException.class)
	public void testSaveTwoCarriersFails() {
		Carrier c = new Carrier("a");
		
		this.state.save(c);
		Carrier newC = this.state.getCarrier("a");
		assertEquals(c, newC);
		
		Carrier c2 = new Carrier("a");
		
		this.state.save(c2);
		Carrier newC2 = this.state.getCarrier("a");
		assertEquals(c2, newC2);
		assertEquals(2, this.state.getAllCarriers().size());
	}
}
