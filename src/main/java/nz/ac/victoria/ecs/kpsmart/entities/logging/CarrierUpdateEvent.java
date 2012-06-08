package nz.ac.victoria.ecs.kpsmart.entities.logging;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;

@Entity
public final class CarrierUpdateEvent extends EntityUpdateEvent<Carrier> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CarrierUpdateEvent() {super(null);}
	public CarrierUpdateEvent(Carrier entity) {
		super(entity);
	}}