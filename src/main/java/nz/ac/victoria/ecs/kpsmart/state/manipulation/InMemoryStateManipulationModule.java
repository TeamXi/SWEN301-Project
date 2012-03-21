package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public final class InMemoryStateManipulationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(StateManipulator.class)
				.annotatedWith(Names.named("memory"))
				.to(InMemoryStateManipulator.class);
	}

}
