package nz.ac.victoria.ecs.kpsmart.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import nz.ac.victoria.ecs.kpsmart.entities.logging.MailDeliveryUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.entities.state.Price;
import nz.ac.victoria.ecs.kpsmart.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.resolutions.FormValidationResolution;
import nz.ac.victoria.ecs.kpsmart.routefinder.RouteFinder;
import nz.ac.victoria.ecs.kpsmart.util.RouteDurationCalculator;

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
			MailDeliveryUpdateEvent event = new MailDeliveryUpdateEvent();
			event.setEntity(delivery);
			getEntityManager().performEvent(event);
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("summary", makeSummary(delivery));
			return new FormValidationResolution(true, null, null, data);
		}
		else {
			return new FormValidationResolution(false,new String[]{"destination"},new String[]{"KPS does not deliver mail from "+source+" to "+destination});
		}
	}
	
	private Map<String, Object> makeSummary(MailDelivery delivery) {
		Map<String, Object> summary = new HashMap<String, Object>();
		summary.put("duration", delivery.getShippingDuration());
		summary.put("expenditure", delivery.getCost());
		summary.put("revenue", delivery.getPrice());
		List<Object> positions = new ArrayList<Object>();
		for(Route r : delivery.getRoute()) {
			Map<String, Double> from = new HashMap<String, Double>();
			from.put("lat", r.getStartPoint().getLatitude());
			from.put("lng", r.getStartPoint().getLongitude());
			Map<String, Double> to = new HashMap<String, Double>();
			to.put("lat", r.getEndPoint().getLatitude());
			to.put("lng", r.getEndPoint().getLongitude());
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("from", from);
			obj.put("to", to);
			positions.add(obj);
		}
		summary.put("route", positions);
		return summary;
	}

	private MailDelivery processDelivery() {
		Location from = getState().getLocationForName(source);
		Location to = getState().getLocationForName(destination);
		
		Price price = getState().getPrice(from, to, priority);
		if(price != null) {
			List<Route> route = this.routeFinder.calculateRoute(
					priority, 
					from, 
					to, 
					weight, 
					volume);
			
			if(route != null) {
				MailDelivery delivery = new MailDelivery();
				
				Date now = new Date();
				long shippingDuration = RouteDurationCalculator.calculate(route, now);
				
				delivery.setVolume(volume);
				delivery.setWeight(weight);
				delivery.setPriority(priority);
				delivery.setRoute(route);
				delivery.setSubmissionDate(now);
				delivery.setShippingDuration(shippingDuration);
				delivery.fillCalculatedFields();
			
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
