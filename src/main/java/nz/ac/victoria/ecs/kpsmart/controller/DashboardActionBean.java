package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/dashboard")
public class DashboardActionBean extends AbstractActionBean {
	@DefaultHandler
	public Resolution dashboard() {
		return new ForwardResolution("/views/dashboard/dashboard.jsp");
	}
}
