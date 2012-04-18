package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;

@Entity
public final class RouteDeleteEvent extends EntityDeleteEvent<Route> {
	public RouteDeleteEvent() { super(null); }
	public RouteDeleteEvent(Route entity) {
		super(entity);
	}}