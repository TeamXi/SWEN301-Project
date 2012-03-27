package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.ajax.JavaScriptResolution;
import nz.ac.victoria.ecs.kpsmart.resolutions.FormValidationResolution;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.StateManipulator;

@UrlBinding("/event/carrier?{$event}")
public class CarrierInfoActionBean extends AbstractActionBean {
	
	private String name;
	
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
		newCarrier.setName(name);
		getStateManipulator().saveCarrier(newCarrier);
		
		return new FormValidationResolution(true,null,null);

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
}
