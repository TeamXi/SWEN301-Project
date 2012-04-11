package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/event/customerprice?{$event}")
public class CustomerPriceActionBean extends FormActionBean {
	@DefaultHandler
	public Resolution customerPricePage() {
		return new ForwardResolution("/views/event/customerPrice.jsp");
	}
	
	@HandlesEvent("addform")
	public Resolution addCustomerPriceScreen() {
		return new ForwardResolution("/views/event/customerPriceForm.jsp");
	}
}
