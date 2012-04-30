package nz.ac.victoria.ecs.kpsmart.entities.logging;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;

@Entity
public final class CarrierUpdateEvent extends EntityUpdateEvent<Carrier> {
	public CarrierUpdateEvent() {super(null);}
	public CarrierUpdateEvent(Carrier entity) {
		super(entity);
	}}