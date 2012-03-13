package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/event/{$event}")
public final class EventActionBean extends AbstractActionBean {
	@HandlesEvent("mail")
	public Resolution mailDeliveryEvent() {
		return new ForwardResolution("/views/event/mail.jsp");
	}
}
