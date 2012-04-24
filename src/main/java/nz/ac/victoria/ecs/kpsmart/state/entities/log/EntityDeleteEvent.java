package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.MappedSuperclass;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;

@MappedSuperclass
public abstract class EntityDeleteEvent<EntityType extends StorageEntity> extends EntityOperationEvent<EntityType> {

	protected EntityDeleteEvent(EntityType entity) {
		super(entity);
	}
	@Override
	public String toString() {
		return "EntityDeleteEvent [id=" + getId() + ", entity=" + entity + "]";
	}
}
