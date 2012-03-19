package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/report/{$event}")
public final class ReportActionBean extends AbstractActionBean {
	@HandlesEvent("something")
	public Resolution someReport() {
		return new ForwardResolution("/views/reports/somthing.jsp");
	}
}
