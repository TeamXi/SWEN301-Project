package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/event/mail?{$event}")
public class MailActionBean extends AbstractActionBean {
	
	private String source = "This";
	
	private String destination;

	private int priority;
	
	private float weight;
	
	private float volume;
	
	
	@DefaultHandler
	public Resolution defaultEvent() {
		return new ForwardResolution("/views/event/mail.jsp");
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
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
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
