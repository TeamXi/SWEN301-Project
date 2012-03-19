package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.util.Collection;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;

/**
 * Defines how to interact with a state. 
 * 
 * @author hodderdani
 *
 */
public interface StateManipulator {
	/**
	 * Get a mail delivery by it's id
	 * 
	 * @param id	The ID of the mail delivery
	 * @return	The referenced mail delivery, or null if none matched the id.
	 */
	public MailDelivery getMailDelivery(final long id);
	
	/**
	 * Save a mail delivery to the underlying state represnetation.
	 * 
	 * @param delivery	The delivery to save.
	 */
	public void saveMailDelivery(final MailDelivery delivery);
	
	/**
	 * Get all the locations in the datasource.
	 * 
	 * @return	All the locations known to this state. The order is undefined.
	 */
	public Collection<Location> getAllLocations();
}
