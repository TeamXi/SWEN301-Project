package nz.ac.victoria.ecs.kpsmart.entities.logging;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.entities.state.DomesticCustomerPrice;

@Entity
public final class DomesticCustomerPriceDeleteEvent extends EntityDeleteEvent<DomesticCustomerPrice> {
	public DomesticCustomerPriceDeleteEvent() { super(null); }
	public DomesticCustomerPriceDeleteEvent(DomesticCustomerPrice price) {
		super(price);
	}
}
