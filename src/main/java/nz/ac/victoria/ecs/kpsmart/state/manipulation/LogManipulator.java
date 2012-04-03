package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.util.List;

import nz.ac.victoria.ecs.kpsmart.state.entities.log.Event;

/**
 * Persist and get log events from a backend
 * 
 * @author hodderdani
 *
 */
public interface LogManipulator {
	/**
	 * Save a log event to the backend
	 * 
	 * @param event
	 */
	public void save(Event event);
	
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
