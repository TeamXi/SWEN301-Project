package nz.ac.victoria.ecs.kpsmart.integration;

import nz.ac.victoria.ecs.kpsmart.InjectOnCall;
import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityOperationEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.HibernateImpl;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.LogManipulator;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.ReadOnlyLogManipulator;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.ReadOnlyStateManipulator;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.ReportManager;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.StateManipulator;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
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
	private StateManipulator manipulator;
	
	@Inject(optional=true)
	private LogManipulator log;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private ReportManager report;
	
	/**
	 * Perform an entity update event on the datasource
	 * 
	 * @param event	The update event to process
	 */
	public void performEvent(EntityUpdateEvent<? extends StorageEntity> event) {
		logger.info("Performing entity update event: {}", event);
		
		event.getEntity().setRelateEventID(event.getUid());
		getStateManipulator().save(event.getEntity());
	}
	
	/**
	 * Perform an event to delete an entity from the datasource
	 * 
	 * @param event	The event to apply.
	 */
	public void performEvent(EntityDeleteEvent<? extends StorageEntity> event) {
		logger.info("Performing entity delete event: {}", event);
		
		getStateManipulator().delete(event.getEntity());
	}
	
		
	/**
	 * Perform some event.
	 * 
	 * @param e	The event to perform
	 */
	@SuppressWarnings("unchecked")
	public void performEvent(EntityOperationEvent<? extends StorageEntity> e) {
		if (e instanceof EntityUpdateEvent)
			this.performEvent((EntityUpdateEvent<? extends StorageEntity>) e);
		if (e instanceof EntityDeleteEvent)
			this.performEvent((EntityDeleteEvent<? extends StorageEntity>) e);
	}
	
	/**
	 * Get a read only state manipulation class
	 * 
	 * @return	The read only state manipulator for accessing the current state
	 */
	public ReadOnlyStateManipulator getData() {
		logger.debug("Getting read only state manipulator");
		
		return getStateManipulator();
	}
	
	/**
	 * Return the current log manipulator
	 * 
	 * @return	The read only copy of the log, or null if logging is diabled.
	 */
	public ReadOnlyLogManipulator getLog() {
		return getLogManipulator();
	}
	
	public ReportManager getReports() {
		return this.getReportManager();
	}
	
	/**
	 * Creates and returns a new in-memory entity manager
	 * 
	 * @return
	 */
	public EntityManager getNewInMemoryEntityManager() {
		return new EntityManager() {
			@Inject
			@Named("memory")
			private StateManipulator memoryState;
			
			@Override @InjectOnCall
			protected StateManipulator getStateManipulator() {
				return this.memoryState;
			}
		};
	}
	
	public final EntityManager getEntityManagerAtEventPoint(final long id) {
		logger.info("Getting state at event ID {}", id);
		
		return new EntityManager() {
			protected HibernateImpl state = 
				new HibernateImpl() {
					@Override
					protected Criteria getEntityCriteria(Class<? extends StorageEntity> clazz) {
						return super.getEntityCriteria(clazz)
								.add(Restrictions.le("relateEventID.Id", id));
					}
					
					@Override
					protected Criteria getEventCriteria(Class<? extends EntityOperationEvent> clazz) {
						return super.getEventCriteria(clazz)
								.add(Restrictions.le("uid.Id", id));
					}
				};
			
			@Override
			protected StateManipulator getStateManipulator() {
				return state;
			}
			
			@Override
			protected ReportManager getReportManager() {
				return state;
			}
			
			@Override
			protected LogManipulator getLogManipulator() {
				return state;
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
	
	protected StateManipulator getStateManipulator() {
		return manipulator;
	}

	protected ReportManager getReportManager() {
		return report;
	}
	
	protected LogManipulator getLogManipulator() {
		return log;
	}

	public static final class Module extends AbstractModule {
		@Override
		protected void configure() {
			bind(EntityManager.class);
		}
	}
}
