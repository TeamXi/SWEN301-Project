package nz.ac.victoria.ecs.kpsmart.integration;

import nz.ac.victoria.ecs.kpsmart.InjectOnCall;
import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.entities.logging.EntityDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.EntityOperationEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.EntityUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.StorageEntity;
import nz.ac.victoria.ecs.kpsmart.logging.Log;
import nz.ac.victoria.ecs.kpsmart.logging.ReadOnlyLog;
import nz.ac.victoria.ecs.kpsmart.reporting.Report;
import nz.ac.victoria.ecs.kpsmart.state.ReadOnlyState;
import nz.ac.victoria.ecs.kpsmart.state.State;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Handles calls down to the various state mechanisms. 
 * 
 * @author hodderdani
 *
 */
@InjectOnContruct
public class EntityManager {
	@Inject
	private State state;
	
	@Inject(optional=true)
	private Log log;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private Report report;
	
	/**
	 * Perform an entity update event on the datasource
	 * 
	 * @param event	The update event to process
	 */
	public void performEvent(EntityUpdateEvent<? extends StorageEntity> event) {
		logger.info("Performing entity update event: {}", event);
		
		event.getEntity().setRelateEventID(event.getUid());
		getState().save(event.getEntity());
	}
	
	/**
	 * Perform an event to delete an entity from the datasource
	 * 
	 * @param event	The event to apply.
	 */
	public void performEvent(EntityDeleteEvent<? extends StorageEntity> event) {
		logger.info("Performing entity delete event: {}", event);
		
		getState().delete(event.getEntity());
	}
	
		
	/**
	 * Perform some event.
	 * 
	 * @param e	The event to perform
	 */
	public void performEvent(EntityOperationEvent<? extends StorageEntity> e) {
		if (e instanceof EntityUpdateEvent)
			this.performEvent((EntityUpdateEvent<? extends StorageEntity>) e);
		else if (e instanceof EntityDeleteEvent)
			this.performEvent((EntityDeleteEvent<? extends StorageEntity>) e);
		else
			throw new IllegalStateException("The event type "+e.getClass().getName()+" is not a recognized event");
	}
	
	/**
	 * Get a read only state manipulation class
	 * 
	 * @return	The read only state manipulator for accessing the current state
	 */
	public ReadOnlyState getData() {
		logger.debug("Getting read only state manipulator");
		
		return getState();
	}
	
	/**
	 * Return the current log manipulator
	 * 
	 * @return	The read only copy of the log, or null if logging is diabled.
	 */
	public ReadOnlyLog getLog() {
		return getLogManipulator();
	}
	
	public Report getReports() {
		return this.getReport();
	}
	
	/**
	 * Creates and returns a new in-memory entity manager
	 * 
	 * @return
	 */
	@Deprecated
	public EntityManager getNewInMemoryEntityManager() {
		return new EntityManager() {
			@Inject
			@Named("memory")
			private State memoryState;
			
			@Override @InjectOnCall
			protected State getState() {
				return this.memoryState;
			}
		};
	}
	
	public final EntityManager getEntityManagerAtEventPoint(final long id) {
		logger.info("Getting state at event ID {}", id);
		
		return new EntityManager() {	
			private State state;
			private Report report;
			private Log log;
			
			{
				this.state = EntityManager.this.state.getAtEventID(id);
				this.report = EntityManager.this.report.getAtEventID(id);
				if (EntityManager.this.log != null)
					this.log = EntityManager.this.log.getAtEventID(id);
			}
			
			@Override
			protected State getState() {
				return this.state;
			}
			
			@Override
			protected Report getReport() {
				return this.report;
			}
			
			@Override
			protected Log getLogManipulator() {
				return this.log;
			}
			
			@Override
			public void performEvent(EntityUpdateEvent<? extends StorageEntity> event) {
				throw new UnsupportedOperationException("Can no perform events on past states");
			}
			
			@Override
			public void performEvent(EntityDeleteEvent<? extends StorageEntity> event) {
				throw new UnsupportedOperationException("Can no perform events on past states");
			}
		};
	}
	
	protected State getState() {
		return state;
	}

	protected Report getReport() {
		return report;
	}
	
	protected Log getLogManipulator() {
		return log;
	}

	public static final class Module extends AbstractModule {
		@Override
		protected void configure() {
			bind(EntityManager.class);
		}
	}
}
