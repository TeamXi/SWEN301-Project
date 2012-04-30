package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.ajax.JavaScriptResolution;
import nz.ac.victoria.ecs.kpsmart.integration.EntityManager;
import nz.ac.victoria.ecs.kpsmart.reporting.Report;

@UrlBinding("/charts")
public class ChartsActionBean extends AbstractActionBean{
	
	private EntityManager manager = super.getEntityManager();
	
	@DefaultHandler
	public Resolution defaultEvent() {
		return new ForwardResolution("/views/event/charts.jsp");
	}
	
	public Report getReportManager() {
		return getEntityManager().getReports();
	}
	
	@Override
	public EntityManager getEntityManager() {
		return manager;
		
	}	

}
