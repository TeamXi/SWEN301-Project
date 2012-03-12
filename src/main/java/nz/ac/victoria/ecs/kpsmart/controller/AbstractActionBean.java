package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

public abstract class AbstractActionBean implements ActionBean {
	private ActionBeanContext context;

	public final ActionBeanContext getContext() {
		return context;
	}

	public final void setContext(ActionBeanContext context) {
		this.context = context;
	}
}
