package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class EntityUpdateEvent<E extends StorageEntity> extends Event {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@OneToOne
	private E entity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
		EntityUpdateEvent other = (EntityUpdateEvent) obj;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntityUpdateEvent [id=" + id + ", entity=" + entity + "]";
	}
	
	public static final class CarrierUpdateEvent extends EntityUpdateEvent<Carrier> {}
	public static final class LocationUpdateEvent extends EntityUpdateEvent<Location> {}
	public static final class RouteUpdateEvent extends EntityUpdateEvent<Route> {}
	public static final class CustomerPriceUpdateEvent extends EntityUpdateEvent<CustomerPrice> {}
}
