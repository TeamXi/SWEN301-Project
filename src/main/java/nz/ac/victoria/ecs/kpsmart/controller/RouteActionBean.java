package nz.ac.victoria.ecs.kpsmart.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import nz.ac.victoria.ecs.kpsmart.resolutions.FormValidationResolution;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityDeleteEvent.RouteDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityUpdateEvent.RouteUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.TransportMeans;

@UrlBinding("/event/route?{$event}")
public class RouteActionBean extends FormActionBean {
	
	private String source;
	private String destination;
	private TransportMeans transportType;
	private long routeId;
	private String carrier;
	private float weightCost;
	private float volumeCost;
	private int frequency;
	private int duration;
	
	@DefaultHandler
	public Resolution defaultEvent() {
		return new ForwardResolution("/views/event/route.jsp");
	}
	
	@HandlesEvent("addform")
	public Resolution addRouteScreen() {
		return new ForwardResolution("/views/event/routeForm.jsp");
	}
	
	@HandlesEvent("updateform")
	public Resolution updateRouteScreen() {
		disableFormField(new String[]{"source", "destination", "transportType", "carrier"});
		
		Route route = getStateManipulator().getRouteByID(routeId);
		source = route.getStartPoint().getName();
		destination = route.getEndPoint().getName();
		transportType = route.getTransportMeans();
		carrier = route.getCarrier().getName();
		weightCost = route.getCarrierWeightUnitCost();
		volumeCost = route.getCarrierVolumeUnitCost();
		frequency = route.getFrequency();
		duration = route.getDuration();
		
		return new ForwardResolution("/views/event/routeForm.jsp");
	}
	
	@HandlesEvent("listfragment")
	public Resolution routeListFragment() {
		return new ForwardResolution("/views/event/routeList.jsp");
	}
	
	@HandlesEvent("update")
	public Resolution updateRouteInfo(){
		Route updatedRoute = fillUpdateRoute(getStateManipulator().getRouteByID(routeId));
		RouteUpdateEvent event = new RouteUpdateEvent();
		event.setEntity(updatedRoute);
		getEntityManager().performEvent(event);
		
		return new FormValidationResolution(true, null, null);
	}
	
	@HandlesEvent("new")
	public Resolution newRouteInfo() {
		Map<String,String> errors = new HashMap<String,String>();
		Route newRoute = fillNewRoute(Route.newRoute());

		newRoute.setStartingTime(Calendar.getInstance().getTime());

		boolean isValid = true;

		if(destination.equals(source)){
			isValid = false;
			errors.put("destination", "destination and source cannot be the same");
		}

		if(!isValid){
			return new FormValidationResolution(false,errors.keySet().toArray(new String[]{}),errors.values().toArray(new String[]{}));
		}
		else {
			RouteUpdateEvent event = new RouteUpdateEvent();
			event.setEntity(newRoute);
			getEntityManager().performEvent(event);
			return new FormValidationResolution(true,null,null);
		}
	}
	
	@HandlesEvent("delete")
	public Resolution deleteRoute() {
		RouteDeleteEvent event = new RouteDeleteEvent();
		event.setEntity(getStateManipulator().getRouteByID(routeId));
		getEntityManager().performEvent(event);
		return new FormValidationResolution(true, null, null);
	}

	private Route fillNewRoute(Route newRoute) {
		newRoute.setStartPoint(getStateManipulator().getLocationForName(source));
		newRoute.setEndPoint(getStateManipulator().getLocationForName(destination));
		newRoute.setTransportMeans(transportType);
		newRoute.setCarrier(getStateManipulator().getCarrier(carrier));
		newRoute.setCarrierVolumeUnitCost(volumeCost);
		newRoute.setCarrierWeightUnitCost(weightCost);
		newRoute.setFrequency(frequency);
		newRoute.setDuration(duration);
		newRoute.setDisabled(false);
		
		return newRoute;
	}
	
	private Route fillUpdateRoute(Route route) {
		route.setCarrierVolumeUnitCost(volumeCost);
		route.setCarrierWeightUnitCost(weightCost);
		route.setFrequency(frequency);
		route.setDuration(duration);
		
		return route;
	}
	
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the transport type
	 */
	public TransportMeans getTransportType() {
		return transportType;
	}

	/**
	 * @param priority the transport type to set
	 */
	public void setTransportType(TransportMeans transportType) {
		this.transportType = transportType;
	}
	/**
	 * @return the routeId
	 */
	public long getRouteId() {
		return routeId;
	}
	/**
	 * @param routeId the routeId to set
	 */
	public void setRouteId(long routeId) {
		this.routeId = routeId;
	}

	/**
	 * @return the carrier
	 */
	public String getCarrier() {
		return carrier;
	}

	/**
	 * @param carrier the carrier to set
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	/**
	 * @return the weightCost
	 */
	public float getWeightCost() {
		return weightCost;
	}

	/**
	 * @param weightCost the weightCost to set
	 */
	public void setWeightCost(float weightCost) {
		this.weightCost = weightCost;
	}

	/**
	 * @return the volumeCost
	 */
	public float getVolumeCost() {
		return volumeCost;
	}

	/**
	 * @param volumeCost the volumeCost to set
	 */
	public void setVolumeCost(float volumeCost) {
		this.volumeCost = volumeCost;
	}

	/**
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
}
