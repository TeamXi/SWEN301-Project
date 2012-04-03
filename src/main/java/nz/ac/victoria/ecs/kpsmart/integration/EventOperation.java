package nz.ac.victoria.ecs.kpsmart.integration;

import nz.ac.victoria.ecs.kpsmart.state.entities.log.Event;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.StateManipulator;

/**
 * Change the state of the system in response to the given event
 * 
 * @author hodderdani
 *
 */
interface EventOperation<EventType extends Event> {
	/**
	 * Apply an event to the state.
	 * 
	 * @param event	The event to apply.
	 * @param manipulator	The state to change.
	 */
	public void apply(EventType event, StateManipulator manipulator);
}
