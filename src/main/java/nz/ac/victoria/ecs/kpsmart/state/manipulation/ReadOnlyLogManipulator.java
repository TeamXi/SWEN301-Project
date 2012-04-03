package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.util.List;

import nz.ac.victoria.ecs.kpsmart.state.entities.log.Event;

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
	public Event getEvent(long id);
	
	/**
	 * Get all events from the database in the order they happened.
	 * 
	 * @return	A list of all events that have ever occurred
	 */
	public List<Event> getAllEvents();
}
