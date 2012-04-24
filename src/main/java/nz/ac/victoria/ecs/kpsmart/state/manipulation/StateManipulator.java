package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.util.Collection;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.DomesticCustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;

/**
 * Defines how to interact with a state. 
 * 
 * @author hodderdani
 *
 */
public interface StateManipulator extends ReadOnlyStateManipulator, ReportManager {
	/**
	 * Save a mail delivery to the underlying state represnetation.
	 * 
	 * @param delivery	The delivery to save.
	 */
	@Deprecated
	public void saveMailDelivery(final MailDelivery delivery);
	
	/**
	 * Delete the given route from the datasource
	 * 
	 * @param route	The route to delete.
	 */
	@Deprecated
	public void deleteRoute(Route route);
	
	/**
	 * Save a route to the backend
	 * 
	 * @param route
	 */
	@Deprecated
	public void saveRoute(final Route route);
	
	/**
	 * Save a carrier back to the datasource.
	 * 
	 * @param carier	The carrier to save
	 */
	@Deprecated
	public void saveCarrier(final Carrier carier);
	
	/**
	 * Delete the specified carrier from the datasource
	 * 	
	 * @param carrier	The carrier to delete
	 */
	@Deprecated
	public void deleteCarrier(final Carrier carrier);

	/**
	 * Save a location to the database
	 * 
	 * @param location	The location to save
	 */
	@Deprecated
	public void saveLocation(Location location);

	/**
	 * Save an entity to the datasource
	 * 
	 * @param entity	The entity to save.
	 */
	public void save(StorageEntity entity);
	
	/**
	 * Save the given domestic price object over the existing domestic price.
	 * 
	 * @param domesticPrice	The new domestic price
	 */
	public void save(DomesticCustomerPrice domesticPrice);

	/**
	 * Delete an entity from the datasource
	 * 	
	 * @param entity	The entity to delete
	 */
	public void delete(StorageEntity entity);
}
