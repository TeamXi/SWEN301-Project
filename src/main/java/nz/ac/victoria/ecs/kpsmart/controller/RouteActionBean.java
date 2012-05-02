package nz.ac.victoria.ecs.kpsmart.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import nz.ac.victoria.ecs.kpsmart.entities.logging.RouteDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.RouteUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.entities.state.TransportMeans;
import nz.ac.victoria.ecs.kpsmart.resolutions.FormValidationResolution;

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
		
		Route route = getState().getRouteByID(routeId);
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
	
	@HandlesEvent("routeListJSON")
	public Resolution routeListJSON() {
		return new ForwardResolution("/views/event/routeListJSON.jsp");
	}
	
	
	@HandlesEvent("update")
	public Resolution updateRouteInfo(){
		getEntityManager().performEvent(
				new RouteUpdateEvent(fillUpdateRoute(
						getState().getRouteByID(routeId))));
		
		return new FormValidationResolution(true, null, null);
	}
	
	@HandlesEvent("new")
	public Resolution newRouteInfo() {
		Map<String,String> errors = new HashMap<String,String>();
		Route newRoute = fillNewRoute(new Route(
				transportType, 
				getState().getLocationForName(source), 
				getState().getLocationForName(destination), 
				getState().getCarrier(carrier)));

		newRoute.setStartingTime(Calendar.getInstance().getTime());

		boolean isValid = true;

		if(destination.equals(source)){
			isValid = false;
			errors.put("destination", "destination and source cannot be the same");
		}

		if(!isValid){
			return new FormValidationResolution(false,errors);
		}
		else {
			getEntityManager().performEvent(new RouteUpdateEvent(newRoute));
			return new FormValidationResolution(true,null,null);
		}
	}
	
	@HandlesEvent("delete")
	public Resolution deleteRoute() {
		getEntityManager().performEvent(
				new RouteDeleteEvent(
						getState().getRouteByID(routeId)));
		return new FormValidationResolution(true, null, null);
	}

	private Route fillNewRoute(Route newRoute) {
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
