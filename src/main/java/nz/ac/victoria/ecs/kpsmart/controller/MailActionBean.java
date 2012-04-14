package nz.ac.victoria.ecs.kpsmart.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import nz.ac.victoria.ecs.kpsmart.resolutions.FormValidationResolution;
import nz.ac.victoria.ecs.kpsmart.routefinder.RouteFinder;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;

@UrlBinding("/event/mail?{$event}")
public class MailActionBean extends AbstractActionBean {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
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
			return new FormValidationResolution(false,new String[]{"destination"},new String[]{"KPS does not deliver mail from "+source+" to "+destination});
		}
	}
	
	private MailDelivery processDelivery() {
		Location from = getStateManipulator().getLocationForName(source);
		Location to = getStateManipulator().getLocationForName(destination);
		
		CustomerPrice price = getStateManipulator().getCustomerPrice(from, to, priority);
		if(price != null) {
			List<Route> route = this.routeFinder.calculateRoute(
					priority, 
					from, 
					to, 
					weight, 
					volume);
			
			if(route != null) {
				MailDelivery delivery = new MailDelivery();
				
				delivery.setVolume(volume);
				delivery.setWeight(weight);
				delivery.setPriority(priority);
				delivery.setRoute(route);
			
				return delivery;
			}
			else {
				log.info("Rejecting mail: there is no route");
			}
		}
		else {
			log.info("Rejecting mail: there is no customer price");
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
