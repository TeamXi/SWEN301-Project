package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Module;

public final class HibernateStateManipulationModule extends AbstractModule {
	@Override
	public void configure() {
		bind(StateManipulator.class).to(HibernateStateManipulator.class);
	}
}
