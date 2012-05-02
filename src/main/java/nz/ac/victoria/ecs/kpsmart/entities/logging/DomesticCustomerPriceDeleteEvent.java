package nz.ac.victoria.ecs.kpsmart.entities.logging;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.entities.state.DomesticCustomerPrice;

@Entity
public final class DomesticCustomerPriceDeleteEvent extends EntityDeleteEvent<DomesticCustomerPrice> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DomesticCustomerPriceDeleteEvent() { super(null); }
	public DomesticCustomerPriceDeleteEvent(DomesticCustomerPrice price) {
		super(price);
	}
}
