package nz.ac.victoria.ecs.kpsmart.state.transformations;

import nz.ac.victoria.ecs.kpsmart.state.entities.log.Event;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.StateManipulator;

/**
 * Performs a state translation using an event
 * 
 * @author hodderdani
 *
 * @param <E>	The event type this transformer can handle
 */
public interface EventTransformer<E extends Event> {
	/**
	 * Manipulate the state of the current appliation to apply the event <code>event</code> to the state represented by the <code>state</code>.
	 * 
	 * @param event	The event to apply
	 * @param state	The state to apply it to
	 * @return	For method chaingin
	 */
	public EventTransformer<E> apply(E event, StateManipulator state);
	
	/**
	 * Reverse the effect of the given event, on the given state
	 * 
	 * @param event	The event to reverse
	 * @param state	The state to apply it to
	 * @return	For method chaining
	 */
	public EventTransformer<E> reverse(E event, StateManipulator state);
}
