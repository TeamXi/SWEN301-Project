package nz.ac.victoria.ecs.kpsmart.entities.logging;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;

@Entity
public final class CarrierDeleteEvent extends EntityDeleteEvent<Carrier> {
	private static final long serialVersionUID = 1L;
	
	CarrierDeleteEvent() { super(null); }
	public CarrierDeleteEvent(Carrier entity) {
		super(entity);
	}}