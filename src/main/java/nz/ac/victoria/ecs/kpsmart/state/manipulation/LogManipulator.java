package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityOperationEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;

/**
 * Persist and get log events from a backend
 * 
 * @author hodderdani
 *
 */
public interface LogManipulator extends ReadOnlyLogManipulator {
	/**
	 * Save a log event to the backend
	 * 
	 * @param event
	 */
	public void save(EntityOperationEvent<? extends StorageEntity> event);
}
