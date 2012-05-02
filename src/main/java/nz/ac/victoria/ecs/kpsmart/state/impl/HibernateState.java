package nz.ac.victoria.ecs.kpsmart.state.impl;

import static nz.ac.victoria.ecs.kpsmart.util.ListUtils.filter;

import java.util.Collection;
import java.util.List;

import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.entities.state.Bool;
import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.entities.state.Direction;
import nz.ac.victoria.ecs.kpsmart.entities.state.DomesticCustomerPrice;
import nz.ac.victoria.ecs.kpsmart.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.entities.state.Price;
import nz.ac.victoria.ecs.kpsmart.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.entities.state.StorageEntity;
import nz.ac.victoria.ecs.kpsmart.state.ReadOnlyState;
import nz.ac.victoria.ecs.kpsmart.state.State;
import nz.ac.victoria.ecs.kpsmart.util.Filter;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

@InjectOnContruct
public final class HibernateState implements State, ReadOnlyState {
	@Inject
	private Session session;
	
	private Long maxEventID = null;
	private Logger logger = LoggerFactory.getLogger(HibernateState.class);
	
	public HibernateState() {}
	public HibernateState(final long maxEventID) {
		this.maxEventID = maxEventID;
	}
	
	public HibernateState(StorageEntity... initialEntities) {
		for (StorageEntity e : initialEntities)
			this.save(e);
	}
	
	@Override
	public MailDelivery getMailDelivery(final long id) {		
		return (MailDelivery) this.getEntityCriteria(MailDelivery.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
				
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Location> getAllLocations() {
		return (Collection<Location>) this.getEntityCriteria(Location.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Route> getAllRoute() {
		return (List<Route>) this.getEntityCriteria(Route.class)
				.addOrder(Order.asc("uid.id"))
				.list();
	}

	@Override
	public Carrier getCarrier(long id) {
		return (Carrier) this.getEntityCriteria(Carrier.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}

	@Override
	public Location getLocationForName(String name) {
		return (Location) this.getEntityCriteria(Location.class)
				.add(Restrictions.eq("name", name))
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Carrier> getAllCarriers() {
		return (Collection<Carrier>) this.getEntityCriteria(Carrier.class)
				.list();
	}

	@Override
	public Route getRouteByID(long id) {
		return (Route) this.getEntityCriteria(Route.class)
				.add(Restrictions.eq("uid.id", id))
				.uniqueResult();
	}

	@Override
	public void save(StorageEntity entity) {
		if (entity instanceof Route)
			this.session.save(((Route) entity).getUid());
		
		this.checkUniqueness(entity);
		
		this.session.save(entity);
		this.session.flush();
	}
	
	private void checkUniqueness(StorageEntity entity) {
		if (entity instanceof Route) {
			for (Route r : this.getAllRoute())
				if (r.isNonUnique(entity))
					throw new IllegalArgumentException("The entity is non-unique");
		} else if (entity instanceof Location) {
			for (Location l : this.getAllLocations())
				if (l.isNonUnique(entity))
					throw new IllegalArgumentException("The entity is non-unique");
		} else if (entity instanceof Carrier)  {
			for (Carrier c : this.getAllCarriers())
				if (c.isNonUnique(entity))
					throw new IllegalArgumentException("The entity is non-unique");
		} else if (entity instanceof CustomerPrice) {
			for (CustomerPrice c : this.getAllCustomerPrices())
				if (c.isNonUnique(entity))
					throw new IllegalArgumentException("The entity is non-unique");
		} else if (entity instanceof MailDelivery) {
			for (MailDelivery m : this.getAllMailDeliveries())
				if (m.isNonUnique(entity))
					throw new IllegalArgumentException("The entity is non-unique");
		// Check domestic customer price
		} else
			logger.warn("Unknown entity type {}", entity.getClass());
	}

	@Override
	public void delete(StorageEntity entity) {
		this.deleteRelatedEntities(entity);
		this.session.flush();
	}
	
	@SuppressWarnings("unchecked")
	private void deleteRelatedEntities(StorageEntity entity) {
		if (entity.isDisabled())
			return;
		
		entity.setDisabled(true);
		this.session.merge(entity);
		
		if (
			(entity instanceof MailDelivery) ||
			(entity instanceof CustomerPrice) ||
			(entity instanceof Route)
		) 
			return;
		
		if (entity instanceof Carrier)
			for (Route r : (List<Route>) this.getEntityCriteria(Route.class)
					.add(Restrictions.eq("primaryKey.carrier", entity))
					.list())
				this.deleteRelatedEntities(r);
		if (entity instanceof Location)
			for (Route r : (List<Route>) this.getEntityCriteria(Route.class)
					.add(Restrictions.or(
							Restrictions.eq("primaryKey.startPoint", entity), 
							Restrictions.eq("primaryKey.endPoint", entity)))
					.list())
				this.deleteRelatedEntities(r);
	}

	@Override
	public DomesticCustomerPrice getDomesticCustomerPrice() {
		return (DomesticCustomerPrice) this.getEntityCriteria(DomesticCustomerPrice.class)
					.uniqueResult();
	}

	@Override
	public void save(DomesticCustomerPrice domesticPrice) {
		this.session.createQuery("DELETE FROM "+DomesticCustomerPrice.class.getSimpleName()+" where priority="+domesticPrice.getPriority())
				.executeUpdate();
		this.session.save(domesticPrice);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Route> getAllRoutesForPriority(final Priority priority) {
		return filter((List<Route>) this.getEntityCriteria(Route.class)
					.add(Restrictions.in("primaryKey.transportMeans", priority.ValidTransportMeans))
					.list(),
				new Filter<Route>() {
					@Override
					public boolean filter(Route object) {
						if (priority.International)
							return true;
						
						return !object.getStartPoint().isInternational() && !object.getEndPoint().isInternational();
					}
				});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Route> getRoutesBetween(Location start, Location end, Priority priority) {
		return (Collection<Route>) this.getEntityCriteria(Route.class)
					.add(Restrictions.eq("primaryKey.startPoint", start))
					.add(Restrictions.eq("primaryKey.endPoint", end))
					.add(Restrictions.in("primaryKey.transportMeans", priority.ValidTransportMeans))
					.list();
	}

	@Override
	public Carrier getCarrier(String name) {
		return (Carrier) this.getEntityCriteria(Carrier.class)
					.add(Restrictions.eq("name", name))
					.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<CustomerPrice> getAllCustomerPrices() {
		return (List<CustomerPrice>) this.getEntityCriteria(CustomerPrice.class)
					.list();
	}

	@Override
	public CustomerPrice getCustomerPriceById(long id) {
		return (CustomerPrice) this.getEntityCriteria(CustomerPrice.class)
				.add(Restrictions.eq("uid.id", id))
				.uniqueResult();
	}

	@Override
	public Price getPrice(Location start, Location end, Priority priority) {
		if(!start.isInternational() && !end.isInternational()) {
			return this.getDomesticCustomerPrice();
		}
		else {
			return this.getCustomerPrice(start, end, priority);
		}
	}
	
	@Override
	public CustomerPrice getCustomerPrice(Location start, Location end, Priority priority) {
		Location location;
		Direction direction;
		
		if(start != null && end != null) { // Neither null
			if(start.isInternational() == end.isInternational()) {
				throw new RuntimeException("start and end locations must be different with respect to international status");
			}
			else { // One international, one domestic
				if(start.isInternational()) {
					location = start;
					direction = Direction.From;
				}
				else {
					location = end;
					direction = Direction.To;
				}
			}
		}
		else { // One or both null
			if(start == null && end == null) { // Both null
				throw new RuntimeException("start and end locations must not both be null");
			}
			else { // Only one is null (representing new zealand)
				if(start != null) {
					location = start;
					direction = Direction.From;
				}
				else {
					location = end;
					direction = Direction.To;
				}
			}
		}
		
		return (CustomerPrice) this.getEntityCriteria(CustomerPrice.class)
				.add(Restrictions.eq("primaryKey.priority", priority))
				.add(Restrictions.eq("primaryKey.location", location))
				.add(Restrictions.eq("primaryKey.direction", direction))
				.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<MailDelivery> getAllMailDeliveries() {
		return (Collection<MailDelivery>) this.getEntityCriteria(MailDelivery.class)
				.list();
	}

	@Override
	public State getAtEventID(long eventID) {
		return new HibernateState(eventID);
	}
	
	private Criteria getEntityCriteria(Class<? extends StorageEntity> clazz) {
		if (this.maxEventID == null)
			return this.session.createCriteria(clazz)
					.add(Restrictions.eq("disabled", Bool.False));
		else
			return this.session.createCriteria(clazz)
					.add(Restrictions.eq("disabled", Bool.False))
					.add(Restrictions.le("relateEventID.Id", this.maxEventID));
	}
	
	public static final class Module extends AbstractModule {
		@Override
		protected void configure() {
			bind(State.class).to(HibernateState.class);
			bind(ReadOnlyState.class).to(HibernateState.class);
		}
	}
}
