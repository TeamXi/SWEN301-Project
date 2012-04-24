package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.DomesticCustomerPrice;

@Entity
public final class DomesticCustomerPriceDeleteEvent extends EntityDeleteEvent<DomesticCustomerPrice> {
	public DomesticCustomerPriceDeleteEvent() { super(null); }
	public DomesticCustomerPriceDeleteEvent(DomesticCustomerPrice price) {
		super(price);
	}
}
