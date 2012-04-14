package nz.ac.victoria.ecs.kpsmart.controller;

import java.util.List;

import com.google.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import nz.ac.victoria.ecs.kpsmart.resolutions.FormValidationResolution;
import nz.ac.victoria.ecs.kpsmart.routefinder.RouteFinder;
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
	
	@Inject
	private RouteFinder routeFinder;
	
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
			
			List<Route> route = this.routeFinder.calculateRoute(
					priority, 
					this.getEntityManager().getData().getLocationForName(source), 
					this.getEntityManager().getData().getLocationForName(destination), 
					weight, 
					volume);
			
			if(route == null) break;
			
			delivery.setVolume(volume);
			delivery.setWeight(weight);
			delivery.setPriority(priority);
			delivery.setRoute(route);
			
		return delivery;
		
		}while(false);
		
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
	public Priority getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
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
