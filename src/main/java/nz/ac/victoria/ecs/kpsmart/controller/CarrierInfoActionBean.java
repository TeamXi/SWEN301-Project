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
		Resolution res = validateCarrierInfo();
		if(res != null) {
			return res;
		}
		
		Carrier c = getState().getCarrier(name);
		if(c == null) {
			getEntityManager().performEvent(
					new CarrierUpdateEvent(
							new Carrier(this.name)));
			
			return new FormValidationResolution(true,null,null);
		}
		else {
			return new FormValidationResolution(false, new String[]{"name"}, new String[]{"A carrier with this name already exists"});
		}

	}
	
	private Resolution validateCarrierInfo() {
		if(name == null || name.length() == 0) {
			return new FormValidationResolution(false, new String[]{"name"}, new String[]{"Please enter a name"});
		}
		
		return null;
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
		Resolution res = validateCarrierInfo();
		if(res != null) {
			return res;
		}
		
		Carrier carrier = getState().getCarrier(carrierId);
		if(carrier != null) {
			if(getState().getCarrier(name) == null || getState().getCarrier(name).getId() == carrierId) {
				carrier.setName(this.name);
				getEntityManager().performEvent(
						new CarrierUpdateEvent(carrier));
				return new FormValidationResolution(true, null, null);
			}
			else {
				return new FormValidationResolution(false, new String[]{"name"}, new String[]{"A carrier with this name already exists"});
			}
		}
		else {
			return new FormValidationResolution(false, new String[]{"name"}, new String[]{"This carrier does not exist, please refresh the page"});
		}
	}
	
	@HandlesEvent("delete")
	public Resolution deleteCarrier() {
		Carrier carrier = getState().getCarrier(carrierId);
		if(carrier != null) {
			getEntityManager().performEvent(
					new CarrierDeleteEvent(carrier));
			return new FormValidationResolution(true, null, null);
		}
		else {
			return new FormValidationResolution(false, null, null);
		}
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
