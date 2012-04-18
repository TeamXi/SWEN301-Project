package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import com.google.inject.AbstractModule;

public final class BindingStateManipulatorModule extends AbstractModule {
	private final HibernateImpl m;
	
	public BindingStateManipulatorModule(HibernateImpl m) {
		this.m = m;
	}
	
	@Override
	protected void configure() {
		bind(LogManipulator.class).toInstance(m);
		bind(StateManipulator.class).toInstance(m);
		bind(ReportManager.class).toInstance(m);
	}

}
