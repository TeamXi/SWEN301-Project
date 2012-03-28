package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.util.Collection;
import java.util.List;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;

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
	
	/**
	 * Get a location by it's unique name
	 * 
	 * @param name	The unique name of the location
	 * @return	The location or null if none matched.
	 */
	public Location getLocationForName(final String name);
	
	/**
	 * Get all the routes in the datasource, ordered by the unique id
	 * 
	 * @return	The list of routes.
	 */
	public List<Route> getAllRoute();
	
	/**
	 * Get all the routes with one of, either the start or end, points at the given location
	 * 
	 * @param location	The location which the route should originate from, or end at.
	 * @return	A collection of routes matching the criteria. Order is not guaranteed.
	 */
	public Collection<Route> getAllRoutesWithPoint(Location location);
	
	/**
	 * Save a route to the backend
	 * 
	 * @param route
	 */
	public void saveRoute(final Route route);
	
	/**
	 * Get a carrier by it's unique ID
	 * 
	 * @param id	The ID of the carrier
	 * @return	The carrier requested, or null if none found
	 */
	public Carrier getCarrier(final long id);
	
	/**
	 * Save a carrier back to the datasource.
	 * 
	 * @param carier	The carrier to save
	 */
	public void saveCarrier(final Carrier carier);
	
	/**
	 * Delete the specified carrier from the datasource
	 * 	
	 * @param carrier	The carrier to delete
	 */
	public void deleteCarrier(final Carrier carrier);

	/**
	 * Save a location to the database
	 * 
	 * @param location	The location to save
	 */
	public void saveLocation(Location location);
	
	/**
	 * Get all the carriers known to the datasource.
	 * 
	 * @return	A collection of the carriers. Order is not guaranteed.
	 */
	public Collection<Carrier> getAllCarriers();
}
