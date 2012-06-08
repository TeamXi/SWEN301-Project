package nz.ac.victoria.ecs.kpsmart.entities.logging;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.entities.state.CustomerPrice;

@Entity
public final class CustomerPriceDeleteEvent extends EntityDeleteEvent<CustomerPrice> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CustomerPriceDeleteEvent() { super(null); }
	public CustomerPriceDeleteEvent(CustomerPrice entity) {
		super(entity);
	}}