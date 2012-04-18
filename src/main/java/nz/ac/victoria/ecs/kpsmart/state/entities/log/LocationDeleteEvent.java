package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;

@Entity
public final class LocationDeleteEvent extends EntityDeleteEvent<Location> {
	public LocationDeleteEvent() { super(null); }
	public LocationDeleteEvent(Location entity) {
		super(entity);
	}}