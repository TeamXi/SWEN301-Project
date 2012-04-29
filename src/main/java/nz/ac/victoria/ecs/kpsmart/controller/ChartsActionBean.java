package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.ajax.JavaScriptResolution;
import nz.ac.victoria.ecs.kpsmart.integration.EntityManager;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.ReportManager;

@UrlBinding("/charts")
public class ChartsActionBean extends AbstractActionBean{
	
	private EntityManager manager = super.getEntityManager();
	
	@DefaultHandler
	public Resolution defaultEvent() {
		return new ForwardResolution("/views/event/charts.jsp");
	}
	
	public ReportManager getReportManager() {
		return getEntityManager().getReports();
	}
	
	@Override
	public EntityManager getEntityManager() {
		return manager;
		
	}	

}
