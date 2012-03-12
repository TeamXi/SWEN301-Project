package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/{$event}")
public final class HomeActionBean extends AbstractActionBean {
	@DefaultHandler @HandlesEvent("home")
	public Resolution homeAction() {
		return new ForwardResolution("views/home/home.jsp");
	}
}
