package nz.ac.victoria.ecs.kpsmart.state.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import nz.ac.victoria.ecs.kpsmart.entities.state.DomesticCustomerPrice;
import nz.ac.victoria.ecs.kpsmart.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.state.DuplicateEntityException;
import nz.ac.victoria.ecs.kpsmart.util.HibernateDataTest;

public class HibernateImplEntityDomesticCustomerPriceTest extends HibernateDataTest {
	@Test
	public void testSaveDomesticCustomerPrice() {
		DomesticCustomerPrice price = new DomesticCustomerPrice(1, 1, Priority.Domestic_Air);
		
		this.state.save(price);
		
		assertEquals(price, this.state.getDomesticCustomerPrice(Priority.Domestic_Air));
	}
	
	@Test
	public void testUpdateDomesticCustomerPrice() {
		this.state.save(new DomesticCustomerPrice(1, 1, Priority.Domestic_Air));
		
		DomesticCustomerPrice price = this.state.getDomesticCustomerPrice(Priority.Domestic_Air);
		
		price.setPricePerUnitVolume(200);
		price.setPricePerUnitWeight(200);
		
		this.state.save(price);
		
		assertEquals(new DomesticCustomerPrice(200, 200, Priority.Domestic_Air), this.state.getDomesticCustomerPrice(Priority.Domestic_Air));
	}
	
	@Test
	public void testDeleteDomesticCustomerPrice() {
		this.state.save(new DomesticCustomerPrice(1, 1, Priority.Domestic_Air));
		
		this.state.delete(this.state.getDomesticCustomerPrice(Priority.Domestic_Air));
		
		assertNull(this.state.getDomesticCustomerPrice(Priority.Domestic_Air));
	}
	
	@Test
	public void testDeleteAndReCreate() {
		this.state.save(new DomesticCustomerPrice(1, 1, Priority.Domestic_Air));
		this.state.delete(this.state.getDomesticCustomerPrice(Priority.Domestic_Air));
		this.state.save(new DomesticCustomerPrice(2, 2, Priority.Domestic_Air));
		
		assertEquals(new DomesticCustomerPrice(2, 2, Priority.Domestic_Air), this.state.getDomesticCustomerPrice(Priority.Domestic_Air));
	}
	
	@Test(expected=DuplicateEntityException.class)
	public void testCreatingTwoDomesticCustomerPricesFails() {
		this.state.save(new DomesticCustomerPrice(1, 1, Priority.Domestic_Air));
		this.state.save(new DomesticCustomerPrice(2, 2, Priority.Domestic_Air));
	}
}
