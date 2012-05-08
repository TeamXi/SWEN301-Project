package nz.ac.victoria.ecs.kpsmart.entities.logging;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.entities.state.Location;

@Entity
public final class LocationUpdateEvent extends EntityUpdateEvent<Location> {
	private static final long serialVersionUID = 1L;
	
	LocationUpdateEvent() {super(null);}
	public LocationUpdateEvent(Location entity) {
		super(entity);
	}}