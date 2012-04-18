package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;

@Entity
public final class RouteUpdateEvent extends EntityUpdateEvent<Route> {
	public RouteUpdateEvent() {super(null);}
	public RouteUpdateEvent(Route entity) {
		super(entity);
	}}