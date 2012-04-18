package nz.ac.victoria.ecs.kpsmart;

import java.util.Stack;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import nz.ac.victoria.ecs.kpsmart.integration.EntityManager;
import nz.ac.victoria.ecs.kpsmart.integration.LoggingEntityManager;
import nz.ac.victoria.ecs.kpsmart.routefinder.DijkstraRouteFinder;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.HibernateStateManipulationModule;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.InMemoryStateManipulationModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

public final class GuiceServletContextListner implements ServletContextListener {
	private static Stack<History> injectors = new Stack<GuiceServletContextListner.History>();
	private static boolean initilized = false;
	
	public static Injector getInjector() {
		return injectors.peek().injector;
	}
	
	public static synchronized void init() {
		if (initilized)
			return;
		
		Module[] modules = {
				new HibernateStateManipulationModule(),
				new InMemoryStateManipulationModule(),
				new LoggingEntityManager.Module(),
				new DijkstraRouteFinder.Module()
		};
		
		injectors.push(new History(Guice.createInjector(modules), modules));
		
		new Data().createData();
		
		initilized = true;
	}
	
	/**
	 * Override the modules in the current injector with the given ones
	 * 
	 * @param modules	The new modules that will overload the modules in the injector
	 */
	public static synchronized void overloadModules(Module... modules) {
		Module[] newModules = (injectors.isEmpty()) ? 
				modules : 
				new Module[] { Modules.override(injectors.peek().modules).with(modules) };
		
		injectors.push(new History(Guice.createInjector(newModules), newModules));
	}
	
	/**
	 * Restore the previous injector state
	 */
	public static synchronized void restorePreviousInjector() {
		injectors.pop();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// NOP
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		init();
	}
	
	private static final class History {
		private final Injector injector;
		private final Module[] modules;
		
		private History(final Injector injector, final Module... modules) {
			this.injector = injector;
			this.modules = modules;
		}
	}
}
