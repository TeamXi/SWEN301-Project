package nz.ac.victoria.ecs.kpsmart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.ajax.JavaScriptResolution;
import nz.ac.victoria.ecs.kpsmart.resolutions.FormValidationResolution;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.TransportMeans;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.StateManipulator;

@UrlBinding("/event/route?{$event}")
public class RouteActionBean extends AbstractActionBean {
	
	private String source;
	
	private String destination;
	
	private TransportMeans transportType;
	
	@DefaultHandler
	public Resolution defaultEvent() {
		return new ForwardResolution("/views/event/routes.jsp");
	}
	
	@HandlesEvent("new")
	public Resolution newCarrierInfo() {
		Map<String,String> errors = new HashMap<String,String>();
		boolean isValid = true;
		
		if(destination.equals(source)){
			isValid = false;
			errors.put("destination", "destination and source cannot be the same");
		}
		
		if(!isValid){
			return new FormValidationResolution(false,errors.keySet().toArray(new String[]{}),errors.values().toArray(new String[]{}));
		}
		else {
			return new FormValidationResolution(true,null,null);
			}
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
}
