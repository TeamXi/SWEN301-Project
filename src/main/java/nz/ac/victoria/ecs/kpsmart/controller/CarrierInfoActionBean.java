package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import nz.ac.victoria.ecs.kpsmart.resolutions.FormValidationResolution;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityDeleteEvent.CarrierDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityUpdateEvent.CarrierUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Carrier;

@UrlBinding("/event/carrier?{$event}")
public class CarrierInfoActionBean extends AbstractActionBean {
	
	private String name;
	
	private long carrierId;
	
	@DefaultHandler
	public Resolution defaultEvent() {
		return new ForwardResolution("/views/event/carrier.jsp");
	}
	
	@HandlesEvent("listfragment")
	public Resolution carrierListFragment() {
		return new ForwardResolution("/views/event/carrierList.jsp");
	}
	
	@HandlesEvent("new")
	public Resolution newCarrierInfo() {
		Carrier newCarrier = new Carrier();
		configureCarrier(newCarrier);
		CarrierUpdateEvent event = new CarrierUpdateEvent();
		event.setEntity(newCarrier);
		getEntityManager().performEvent(event);
		
		return new FormValidationResolution(true,null,null);

	}
	
	@HandlesEvent("updateform")
	public Resolution updateForm() {
		name = getStateManipulator().getCarrier(carrierId).getName();
		return new ForwardResolution("/views/event/carrierUpdateForm.jsp");
	}
	
	@HandlesEvent("newform")
	public Resolution newForm() {
		return new ForwardResolution("/views/event/carrierNewForm.jsp");
	}
	
	@HandlesEvent("update")
	public Resolution submitupdate() {
		Carrier carrier = getStateManipulator().getCarrier(carrierId);
		configureCarrier(carrier);
		CarrierUpdateEvent event = new CarrierUpdateEvent();
		event.setEntity(carrier);
		getEntityManager().performEvent(event);
		return new FormValidationResolution(true, null, null);
	}
	
	@HandlesEvent("delete")
	public Resolution deleteCarrier() {
		CarrierDeleteEvent event = new CarrierDeleteEvent();
		event.setEntity(getStateManipulator().getCarrier(carrierId));
		getEntityManager().performEvent(event);
		return new FormValidationResolution(true, null, null);
	}
	
	protected Carrier configureCarrier(Carrier carrier) {
		carrier.setName(name);
		
		return carrier;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the carrier id
	 */
	public long getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId the carrier id to set
	 */
	public void setCarrierId(long carrierId) {
		this.carrierId = carrierId;
	}
}
