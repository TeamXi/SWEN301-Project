package nz.ac.victoria.ecs.kpsmart.state;

import nz.ac.victoria.ecs.kpsmart.entities.state.DomesticCustomerPrice;
import nz.ac.victoria.ecs.kpsmart.entities.state.StorageEntity;

/**
 * Defines how to interact with a state. 
 * 
 * @author hodderdani
 *
 */
public interface State extends ReadOnlyState {
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
	
	@Override
	public State getAtEventID(long eventID);
}
