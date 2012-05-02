package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import nz.ac.victoria.ecs.kpsmart.entities.logging.CarrierDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.CarrierUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.resolutions.FormValidationResolution;

@UrlBinding("/event/carrier?{$event}")
public class CarrierInfoActionBean extends FormActionBean {
	
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
	
	@HandlesEvent("list")
	public Resolution carrierListJSON() {
		return new ForwardResolution("/views/event/carrierListJSON.jsp");
	}
	
	@HandlesEvent("new")
	public Resolution newCarrierInfo() {
		getEntityManager().performEvent(
				new CarrierUpdateEvent(
						new Carrier(this.name)));
		
		return new FormValidationResolution(true,null,null);

	}
	
	@HandlesEvent("updateform")
	public Resolution updateForm() {
		name = getState().getCarrier(carrierId).getName();
		return new ForwardResolution("/views/event/carrierForm.jsp");
	}
	
	@HandlesEvent("newform")
	public Resolution newForm() {
		return new ForwardResolution("/views/event/carrierForm.jsp");
	}
	
	@HandlesEvent("update")
	public Resolution submitupdate() {
		Carrier carrier = getState().getCarrier(carrierId);
		carrier.setName(this.name);
		getEntityManager().performEvent(
				new CarrierUpdateEvent(carrier));
		return new FormValidationResolution(true, null, null);
	}
	
	@HandlesEvent("delete")
	public Resolution deleteCarrier() {
		getEntityManager().performEvent(
				new CarrierDeleteEvent(
						getState().getCarrier(carrierId)));
		return new FormValidationResolution(true, null, null);
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
