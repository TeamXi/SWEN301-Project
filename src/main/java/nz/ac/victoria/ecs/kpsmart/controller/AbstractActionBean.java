package nz.ac.victoria.ecs.kpsmart.controller;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

public abstract class AbstractActionBean implements ActionBean {
	private ActionBeanContext context;
	
	public AbstractActionBean() {
		// Inject with Guice
	}

	@Override
	public final ActionBeanContext getContext() {
		return this.context;
	}

	@Override
	public final void setContext(final ActionBeanContext context) {
		this.context = context;
	}
}
