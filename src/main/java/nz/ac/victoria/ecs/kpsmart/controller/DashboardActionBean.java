package nz.ac.victoria.ecs.kpsmart.controller;


import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import nz.ac.victoria.ecs.kpsmart.integration.EntityManager;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.ReadOnlyLogManipulator;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.ReportManager;

@UrlBinding("/dashboard")
public class DashboardActionBean extends AbstractActionBean {
	private long atevent = 0;
	private EntityManager manager = super.getEntityManager();
	
	@DefaultHandler
	public Resolution dashboard() {
		return new ForwardResolution("/views/dashboard/dashboard.jsp");
	}
	
	public ReportManager getReportManager() {
		return getEntityManager().getReports();
	}
	
	@Override
	public EntityManager getEntityManager() {
		return manager;
	}
	
	@Override
	public ReadOnlyLogManipulator getLog() {
		return super.getEntityManager().getLog();
	}

	public long getAtevent() {
		return atevent;
	}

	public void setAtevent(long atevent) {
		this.atevent = atevent;
		if(atevent == 0) {
			manager = super.getEntityManager();
		}
		else {
			manager = super.getEntityManager().getEntityManagerAtEventPoint(atevent);
		}
	}
}
