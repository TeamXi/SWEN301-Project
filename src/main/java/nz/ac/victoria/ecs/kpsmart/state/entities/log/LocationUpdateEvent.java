package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;

@Entity
public final class LocationUpdateEvent extends EntityUpdateEvent<Location> {
	public LocationUpdateEvent() {super(null);}
	public LocationUpdateEvent(Location entity) {
		super(entity);
	}}