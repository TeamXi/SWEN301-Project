package nz.ac.victoria.ecs.kpsmart.controller;

import com.google.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.ReportManager;

@UrlBinding("/dashboard")
@InjectOnContruct
public class DashboardActionBean extends AbstractActionBean {
	@Inject
	private ReportManager reportManager;
	
	@DefaultHandler
	public Resolution dashboard() {
		return new ForwardResolution("/views/dashboard/dashboard.jsp");
	}
	
	public ReportManager getReportManager() {
		return reportManager;
	}
}
