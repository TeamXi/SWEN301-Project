package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class EntityUpdateEvent<E extends StorageEntity> extends EntityOperationEvent<E> {
	@Override
	public String toString() {
		return "EntityUpdateEvent [id=" + id + ", entity=" + entity + "]";
	}
	
	public static final class CarrierUpdateEvent extends EntityUpdateEvent<Carrier> {}
	public static final class LocationUpdateEvent extends EntityUpdateEvent<Location> {}
	public static final class RouteUpdateEvent extends EntityUpdateEvent<Route> {}
	public static final class CustomerPriceUpdateEvent extends EntityUpdateEvent<CustomerPrice> {}
}
