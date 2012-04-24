package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.util.List;

import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityOperationEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.Event;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;

/**
 * Defines the read only part of the log manipulator interface
 * 
 * @author hodderdani
 *
 */
public interface ReadOnlyLogManipulator {
	/**
	 * Get an event by it's unique id.
	 * 
	 * @param id	The id of the event
	 * @return	The event of null if none was found.
	 */
	public EntityOperationEvent<? extends StorageEntity> getEvent(long id);
	
	/**
	 * Get all events from the database in the order they happened.
	 * 
	 * @return	A list of all events that have ever occurred
	 */
	public List<EntityOperationEvent<? extends StorageEntity>> getAllEvents();

	/**
	 * Get all the events before, or at, this point in time
	 * 
	 * @param id	The ID of an event. 
	 * @return	The list of events preceading, and incuding the event
	 */
	public List<EntityOperationEvent<? extends StorageEntity>> getAllEventsBefore(long id);
}
