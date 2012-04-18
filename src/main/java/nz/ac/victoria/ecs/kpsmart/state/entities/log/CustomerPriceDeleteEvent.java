package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.CustomerPrice;

@Entity
public final class CustomerPriceDeleteEvent extends EntityDeleteEvent<CustomerPrice> {
	public CustomerPriceDeleteEvent() { super(null); }
	public CustomerPriceDeleteEvent(CustomerPrice entity) {
		super(entity);
	}}