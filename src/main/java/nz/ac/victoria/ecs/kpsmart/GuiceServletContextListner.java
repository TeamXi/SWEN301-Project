package nz.ac.victoria.ecs.kpsmart;

import java.util.Stack;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import nz.ac.victoria.ecs.kpsmart.integration.HibernateModule;
import nz.ac.victoria.ecs.kpsmart.integration.LoggingEntityManager;
import nz.ac.victoria.ecs.kpsmart.logging.impl.HibernateLogger;
import nz.ac.victoria.ecs.kpsmart.reporting.impl.DefaultReport;
import nz.ac.victoria.ecs.kpsmart.routefinder.DijkstraRouteFinder;
import nz.ac.victoria.ecs.kpsmart.state.impl.HibernateState;

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
		
		initNoData();
		new Data().createData();
	}
	
	public static synchronized void initNoData() {
		if (initilized)
			return;
		
		Module[] modules = {
//				new HibernateStateManipulationModule(),
//				new InMemoryStateManipulationModule(),
				new HibernateModule(),
				new HibernateState.Module(),
				new HibernateLogger.Module(),
				new DefaultReport.Module(),
				new LoggingEntityManager.Module(),
				new DijkstraRouteFinder.Module()
		};
		
		for (Module m : modules)
			if (m instanceof LifecycleModule)
				((LifecycleModule) m).load();
		
		injectors.push(new History(Guice.createInjector(modules), modules));
		
		initilized = true;
	}
	
	/**
	 * Override the modules in the current injector with the given ones
	 * 
	 * @param modules	The new modules that will overload the modules in the injector
	 */
	public static synchronized void overloadModules(Module... modules) {
		for (Module m : injectors.peek().modules)
			if (m instanceof LifecycleModule)
				((LifecycleModule) m).unload();
		
		Module[] newModules = (injectors.isEmpty()) ? 
				modules : 
				new Module[] { Modules.override(injectors.peek().modules).with(modules) };
		
		for (Module m : injectors.peek().modules)
			if (m instanceof LifecycleModule)
				((LifecycleModule) m).load();
		
		injectors.push(new History(Guice.createInjector(newModules), newModules));
	}
	
	public static synchronized void createNewInjector(Module... modules) {
		for (Module m : injectors.peek().modules)
			if (m instanceof LifecycleModule)
				((LifecycleModule) m).unload();
		
		injectors.push(new History(Guice.createInjector(modules), modules));
		
		for (Module m : injectors.peek().modules)
			if (m instanceof LifecycleModule)
				((LifecycleModule) m).load();
	}
	
	/**
	 * Restore the previous injector state
	 */
	public static synchronized void restorePreviousInjector() {
		for (Module m : injectors.peek().modules)
			if (m instanceof LifecycleModule)
				((LifecycleModule) m).unload();
		
		injectors.pop();
		
		for (Module m : injectors.peek().modules)
			if (m instanceof LifecycleModule)
				((LifecycleModule) m).load();
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
