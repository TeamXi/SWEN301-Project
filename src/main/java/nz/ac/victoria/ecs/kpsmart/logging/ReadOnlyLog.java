package nz.ac.victoria.ecs.kpsmart.logging;

import java.util.List;

import nz.ac.victoria.ecs.kpsmart.entities.logging.EntityOperationEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.StorageEntity;
import nz.ac.victoria.ecs.kpsmart.integration.TimeCapable;

/**
 * Defines the read only part of the log manipulator interface
 * 
 * @author hodderdani
 *
 */
public interface ReadOnlyLog extends TimeCapable<ReadOnlyLog> {
	/**
	 * Get an event by it's unique id.
	 * 
	 * @param id	The id of the event
	 * @return	The event of null if none was found.
	 */
	public EntityOperationEvent<? extends StorageEntity> getEvent(long id);
	
	/**
	 * Get the number of events that have transpired
	 * 
	 * @return the number of events
	 */
	public int getNumberOfEvents();
	
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
	@Deprecated
	public List<EntityOperationEvent<? extends StorageEntity>> getAllEventsBefore(long id);
}
