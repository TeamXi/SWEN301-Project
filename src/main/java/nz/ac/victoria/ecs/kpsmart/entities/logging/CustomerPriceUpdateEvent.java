package nz.ac.victoria.ecs.kpsmart.entities.logging;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.entities.state.CustomerPrice;

@Entity
public final class CustomerPriceUpdateEvent extends EntityUpdateEvent<CustomerPrice> {
	public CustomerPriceUpdateEvent() {super(null);}
	public CustomerPriceUpdateEvent(CustomerPrice entity) {
		super(entity);
	}}