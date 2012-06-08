package nz.ac.victoria.ecs.kpsmart.logging;

import nz.ac.victoria.ecs.kpsmart.entities.logging.EntityOperationEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.StorageEntity;

/**
 * Persist and get log events from a backend
 * 
 * @author hodderdani
 *
 */
public interface Log extends ReadOnlyLog {
	/**
	 * Save a log event to the backend
	 * 
	 * @param event
	 */
	public void save(EntityOperationEvent<? extends StorageEntity> event);
	
	@Override
	public Log getAtEventID(long eventID);
}
