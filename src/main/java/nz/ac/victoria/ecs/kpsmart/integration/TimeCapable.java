package nz.ac.victoria.ecs.kpsmart.integration;

/**
 * Notes that a type is capable of being moved in time.
 * 
 * @author hodderdani
 *
 */
public interface TimeCapable<T extends TimeCapable<?>> {
	/**
	 * Get a new instance of the implementing class at a given point in time.
	 * 
	 * @param eventID	The event ID that represents the point in time/.
	 * @return	An instance of the implementer that will never return entities with related events with ID's greater than <code>eventID</code>.
	 */
	public T getAtEventID(long eventID);
}
