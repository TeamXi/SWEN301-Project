package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;

@MappedSuperclass
public abstract class EntityOperationEvent<E extends StorageEntity> extends Event {
	@OneToOne
	protected E entity;

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityOperationEvent<?> other = (EntityOperationEvent<?>) obj;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

}
