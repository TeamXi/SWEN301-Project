package nz.ac.victoria.ecs.kpsmart.entities.logging;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.entities.state.Route;

@Entity
public final class RouteDeleteEvent extends EntityDeleteEvent<Route> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RouteDeleteEvent() { super(null); }
	public RouteDeleteEvent(Route entity) {
		super(entity);
	}}