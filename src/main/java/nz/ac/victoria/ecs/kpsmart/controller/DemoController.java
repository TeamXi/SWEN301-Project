package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/demo")
public final class DemoController implements ActionBean {
	private ActionBeanContext context;

	@Override
	public ActionBeanContext getContext() {
		return this.context;
	}

	@Override
	public void setContext(final ActionBeanContext context) {
		this.context = context;
	}
	
	@DefaultHandler
	public Resolution demoAction() {
		return new ForwardResolution("views/demo/demo.jsp");
	}
}
