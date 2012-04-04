package nz.ac.victoria.ecs.kpsmart.controller;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import nz.ac.victoria.ecs.kpsmart.resolutions.FormValidationResolution;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;

@UrlBinding("/event/mail?{$event}")
public class MailActionBean extends AbstractActionBean {
	
	private String source;
	
	private String destination;

	private Priority priority;
	
	private float weight;
	
	private float volume;
	
	@DefaultHandler
	public Resolution defaultEvent() {
		return new ForwardResolution("/views/event/mail.jsp");
	}
	
	@HandlesEvent("new")
	public Resolution newMailDelivery() {
		MailDelivery delivery = processDelivery();
		if(delivery != null) {
			return new FormValidationResolution(true, null, null);
		}
		else {
			return new FormValidationResolution(false,new String[]{"destination"},new String[]{"There is no route from "+source+" to "+destination});
		}
	}
	
	private MailDelivery processDelivery() {
		do{
			MailDelivery delivery = new MailDelivery();
			
			List<Route> route = findRoute(source,destination);
			
			if(route == null) break;
			
			delivery.setVolume(volume);
			delivery.setWeight(weight);
			delivery.setPriority(priority);
			delivery.setRoute(route);
			
		return delivery;
		
		}while(false);
		
		return null;
	}
	
	private List<Route> findRoute(String sourceName, String destinationName) {
		Boolean routeIsValid = (((int)(Math.random() * 2)) == 1);
		System.out.println(routeIsValid);
		if(routeIsValid){
			Route route = new Route();
			Location locations[] = getStateManipulator().getAllLocations().toArray(new Location[]{});
			Location source = locations[0];
			Location dest = locations[1];
			route.setStartPoint(source);
			route.setEndPoint(dest);
			
			List<Route> routes = new ArrayList<Route>();
			

			routes.add(route);
			return routes;
		}
		return null;
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
	 * @return the priority
	 */
	public String getPriority() {
		return priority.toString();
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = Priority.valueOf(priority);
	}

	/**
	 * @return the weight
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}

	/**
	 * @return the volume
	 */
	public float getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(float volume) {
		this.volume = volume;
	}
}
