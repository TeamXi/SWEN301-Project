package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;

//@Entity
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
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
