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
	
	private String divId;
	
	private String formId;
	
	private String submitCallback;
	
	private String carrier;
	
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

		//TODO: Add following options to Route.
		newRoute.setCarrier(getStateManipulator().getCarrier(carrier));
		newRoute.setCarrierVolumeUnitCost((float)8.0);
		newRoute.setCarrierWeightUnitCost((float)8.9);
		newRoute.setDisabled(false);
		newRoute.setDuration(1);
		newRoute.setFrequency(1);

		return newRoute;
	}
	
	private Route fillUpdateRoute(Route route) {
		// TODO: implement
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
	 * @return the divId
	 */
	public String getDivId() {
		return divId;
	}
	/**
	 * @param divId the divId to set
	 */
	public void setDivId(String divId) {
		this.divId = divId;
	}
	/**
	 * @return the formId
	 */
	public String getFormId() {
		return formId;
	}
	/**
	 * @param formId the formId to set
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}
	/**
	 * @return the submitCallback
	 */
	public String getSubmitCallback() {
		return submitCallback;
	}
	/**
	 * @param submitCallback the submitCallback to set
	 */
	public void setSubmitCallback(String submitCallback) {
		this.submitCallback = submitCallback;
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
}
