package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.ajax.JavaScriptResolution;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.StateManipulator;

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
			getStateManipulator().saveMailDelivery(delivery);
			return new JavaScriptResolution("OK");
		}
		else {
			return new JavaScriptResolution("ERROR");
		}
	}
	
	private MailDelivery processDelivery() {
		MailDelivery delivery = new MailDelivery();
		delivery.setVolume(volume);
		delivery.setWeight(weight);
		delivery.setPriority(priority);
		// TODO: Magic path finding
		return delivery;
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
