package nz.ac.victoria.ecs.kpsmart.controller;

import com.google.inject.Inject;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.integration.EntityManager;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.ReadOnlyLogManipulator;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.ReadOnlyStateManipulator;

@InjectOnContruct
public abstract class AbstractActionBean implements ActionBean {
	private ActionBeanContext context;
	
	public ReadOnlyStateManipulator getState() {
		return getEntityManager().getData();
	}
	
	public ReadOnlyLogManipulator getLog() {
		return getEntityManager().getLog();
	}
	
	@Inject
	private EntityManager entityManager;
	public EntityManager getEntityManager() {
		return entityManager;
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
