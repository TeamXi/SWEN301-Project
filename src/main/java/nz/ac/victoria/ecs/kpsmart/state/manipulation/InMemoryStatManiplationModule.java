package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public final class InMemoryStatManiplationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(StateManipulator.class)
				.annotatedWith(Names.named("memory"))
				.to(InMemoryStateManipulator.class);
	}

}
