package nz.ac.victoria.ecs.kpsmart.state;

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
	 * Delete an entity from the datasource
	 * 	
	 * @param entity	The entity to delete
	 */
	public void delete(StorageEntity entity);
	
	@Override
	public State getAtEventID(long eventID);
}
