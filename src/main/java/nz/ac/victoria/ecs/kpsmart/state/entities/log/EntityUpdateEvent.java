package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.MappedSuperclass;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;

@MappedSuperclass
public abstract class EntityUpdateEvent<E extends StorageEntity> extends EntityOperationEvent<E> {
	protected EntityUpdateEvent(E entity) {
		super(entity);
	}
	
	@Override
	public String toString() {
		return "EntityUpdateEvent [id=" + getId() + ", entity=" + entity + "]";
	}
}
