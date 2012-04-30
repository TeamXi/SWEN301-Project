package nz.ac.victoria.ecs.kpsmart;

import com.google.inject.AbstractModule;

/**
 * Specifies additional methods that are useful for some modules to be able to know when they are made active or in-active
 * 
 * @author hodderdani
 *
 */
public abstract class LifecycleModule extends AbstractModule {
	/**
	 * Notifies the module that it has been made in-active
	 */
	public abstract void unload();
	
	/**
	 * Notifies that the module has been made active. This before the module's {@link #configure()} method is called.
	 */
	public abstract void load();
}
