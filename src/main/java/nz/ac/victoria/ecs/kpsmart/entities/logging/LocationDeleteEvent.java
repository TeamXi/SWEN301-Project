package nz.ac.victoria.ecs.kpsmart.entities.logging;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.entities.state.Location;

@Entity
public final class LocationDeleteEvent extends EntityDeleteEvent<Location> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	LocationDeleteEvent() { super(null); }
	public LocationDeleteEvent(Location entity) {
		super(entity);
	}}