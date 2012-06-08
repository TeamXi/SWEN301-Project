package nz.ac.victoria.ecs.kpsmart.entities.logging;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.entities.state.Route;

@Entity
public final class RouteUpdateEvent extends EntityUpdateEvent<Route> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RouteUpdateEvent() {super(null);}
	public RouteUpdateEvent(Route entity) {
		super(entity);
	}}