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
public class EntityDeleteEvent<EntityType extends StorageEntity> extends EntityOperationEvent<EntityType> {

	@Override
	public String toString() {
		return "EntityDeleteEvent [id=" + id + ", entity=" + entity + "]";
	}
	
	public static final class CarrierDeleteEvent extends EntityDeleteEvent<Carrier> {}
	public static final class LocationDeleteEvent extends EntityDeleteEvent<Location> {}
	public static final class RouteDeleteEvent extends EntityDeleteEvent<Route> {}
	public static final class CustomerPriceDeleteEvent extends EntityDeleteEvent<CustomerPrice> {}
}
