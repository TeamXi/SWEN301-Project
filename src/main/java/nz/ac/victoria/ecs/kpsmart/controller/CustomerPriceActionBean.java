package nz.ac.victoria.ecs.kpsmart.controller;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import nz.ac.victoria.ecs.kpsmart.resolutions.FormValidationResolution;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.CustomerPriceDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.CustomerPriceUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Direction;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority;

@UrlBinding("/event/customerprice?{$event}")
public class CustomerPriceActionBean extends FormActionBean {
	private long priceId;
	private String location;
	private Direction direction;
	private Priority priority;
	private float weightPrice;
	private float volumePrice;
	
	@DefaultHandler
	public Resolution customerPricePage() {
		return new ForwardResolution("/views/event/customerPrice.jsp");
	}
	
	@HandlesEvent("addform")
	public Resolution addCustomerPriceScreen() {
		return new ForwardResolution("/views/event/customerPriceForm.jsp");
	}
	
	@HandlesEvent("updateform")
	public Resolution updateCustomerPriceScreen() {
		disableFormField(new String[] {"direction", "location", "priority"});
		
		CustomerPrice price = getState().getCustomerPriceById(priceId);
		location = price.getLocation().getName();
		direction = price.getDirection().flip();
		priority = price.getPriority();
		weightPrice = price.getPricePerUnitWeight();
		volumePrice = price.getPricePerUnitVolume();
		return new ForwardResolution("/views/event/customerPriceForm.jsp");
	}
	
	@HandlesEvent("new")
	public Resolution newCustomerPrice() {
		CustomerPrice price = CustomerPrice.newInstance();
		fillPrice(price);
		
		Resolution validation = validatePrice(price);
		if(validation != null) {
			return validation;
		}
		
		CustomerPriceUpdateEvent event = new CustomerPriceUpdateEvent();
		event.setEntity(price);
		getEntityManager().performEvent(event);
		
		return new FormValidationResolution(true, null, null);
	}
	
	private Resolution validatePrice(CustomerPrice price) {
		Map<String, String> errors = new HashMap<String, String>();
		if(!price.getLocation().isInternational()) {
			errors.put("location", "The location must be international");
		}
		if(!Priority.internationalPriorities().contains(price.getPriority())) {
			errors.put("priority", "The priority must be international");
		}
		if(price.getPricePerUnitVolume() <= 0) {
			errors.put("volumePrice", "The price per cm&sup3; must be greater than zero");
		}
		if(price.getPricePerUnitWeight() <= 0) {
			errors.put("weightPrice", "The price per gram must be greater than zero");
		}
		
		if(errors.size() > 0) {
			return new FormValidationResolution(false, errors);
		}
		else {
			return null;
		}
	}

	private void fillPrice(CustomerPrice price) {
		price.setLocation(getState().getLocationForName(location));
		price.setDirection(direction.flip());
		price.setPricePerUnitVolume(volumePrice);
		price.setPricePerUnitWeight(weightPrice);
		price.setPriority(priority);
	}
	
	@HandlesEvent("update")
	public Resolution updateCustomerPrice() {
		CustomerPrice price = getState().getCustomerPriceById(priceId);
		updatePrice(price);
		
		Resolution validation = validatePrice(price);
		if(validation != null) {
			return validation;
		}
		
		CustomerPriceUpdateEvent event = new CustomerPriceUpdateEvent();
		event.setEntity(price);
		getEntityManager().performEvent(event);
		
		return new FormValidationResolution(true, null, null);
	}
	
	private void updatePrice(CustomerPrice price) {
		price.setPricePerUnitVolume(volumePrice);
		price.setPricePerUnitWeight(weightPrice);
	}

	@HandlesEvent("delete")
	public Resolution deletePrice() {
		CustomerPriceDeleteEvent event = new CustomerPriceDeleteEvent();
		event.setEntity(getState().getCustomerPriceById(priceId));
		getEntityManager().performEvent(event);
		return new FormValidationResolution(true, null, null);
	}
	
	@HandlesEvent("listfragment")
	public Resolution listFragment() {
		return new ForwardResolution("/views/event/customerPriceList.jsp");
	}

	/**
	 * @return the priceId
	 */
	public long getPriceId() {
		return priceId;
	}

	/**
	 * @param priceId the priceId to set
	 */
	public void setPriceId(long priceId) {
		this.priceId = priceId;
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
	 * @return the weightPrice
	 */
	public float getWeightPrice() {
		return weightPrice;
	}

	/**
	 * @param weightPrice the weightPrice to set
	 */
	public void setWeightPrice(float weightPrice) {
		this.weightPrice = weightPrice;
	}

	/**
	 * @return the volumePrice
	 */
	public float getVolumePrice() {
		return volumePrice;
	}

	/**
	 * @param volumePrice the volumePrice to set
	 */
	public void setVolumePrice(float volumePrice) {
		this.volumePrice = volumePrice;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
}
