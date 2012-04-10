package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import org.hibernate.Session;

import com.google.inject.name.Names;

public final class InMemoryStateManipulationModule extends HibernateStateManipulationModule {
	private final boolean setAlways;
	
	/**
	 * @param setAlways	Determine if the {@link StateManipulator} binding should be for all instances or just those annotated with <code>@Named("memory")</code>
	 */
	public InMemoryStateManipulationModule(boolean setAlways) {
		this.setAlways = setAlways;
	}
	
	public InMemoryStateManipulationModule() {
		this(false);
	}
	
	@Override
	public void configure() {
		if (this.setAlways)
			bind(StateManipulator.class)
				.to(InMemoryStateManipulator.class);
		
		bind(StateManipulator.class)
				.annotatedWith(Names.named("memory"))
				.to(InMemoryStateManipulator.class);
		
		Session session = this.getSession("hibernate.memory.properties");
		session.beginTransaction();
		bind(Session.class)
			.annotatedWith(Names.named("memory"))
			.toInstance(session);
	}

}
