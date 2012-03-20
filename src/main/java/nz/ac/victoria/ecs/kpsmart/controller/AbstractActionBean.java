package nz.ac.victoria.ecs.kpsmart.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.StateManipulator;

@InjectOnContruct
public abstract class AbstractActionBean implements ActionBean {
	private ActionBeanContext context;
	
	@Inject
	@Named("memory")
	private StateManipulator stateManipulator;
	
	public StateManipulator getStateManipulator() {
		return stateManipulator;
	}
	
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
