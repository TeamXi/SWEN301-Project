package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.DomesticCustomerPrice;

@Entity
public final class DomesticCustomerPriceUpdateEvent extends EntityUpdateEvent<DomesticCustomerPrice> {
	public DomesticCustomerPriceUpdateEvent() { super(null); }
	public DomesticCustomerPriceUpdateEvent(DomesticCustomerPrice price) {
		super(price);
	}
}
