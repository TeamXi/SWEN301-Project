package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import static nz.ac.victoria.ecs.kpsmart.util.ListUtils.sort;
import static nz.ac.victoria.ecs.kpsmart.util.ListUtils.filter;

import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import javax.persistence.PersistenceContext;

import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.Event;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Bool;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Direction;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.DomesticCustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Price;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;
import nz.ac.victoria.ecs.kpsmart.util.Filter;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.google.inject.Inject;

@InjectOnContruct
public class HibernateImpl implements StateManipulator, ReportManager, LogManipulator {
	// TODO: we should have checked exceptions and convert all
	// unchecked exceptions to checked exceptions so that the
	// controllers can deal with them nicely
	
	private static final long msinhour = 60*60*1000;
	
	@Inject @PersistenceContext
	private Session session;
	
	protected Session getSession() {
		return session;
	}

	@Override
	public MailDelivery getMailDelivery(final long id) {		
		return (MailDelivery) getSession().get(MailDelivery.class, id);
	}

	@Override
	public void saveMailDelivery(final MailDelivery delivery) {
		this.getSession().merge(delivery);
		this.getSession().flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Location> getAllLocations() {
		return (Collection<Location>) this.getSession().createCriteria(Location.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Route> getAllRoute() {
		return (List<Route>) this.getSession().createCriteria(Route.class)
				.add(Restrictions.eq("disabled", Bool.False))
				.addOrder(Order.asc("uid.id"))
				.list();
	}

	@Override
	public void saveRoute(Route route) {
		this.getSession().save(route.getUid());
		this.getSession().save(route);
		this.getSession().flush();
	}

	@Override
	public Carrier getCarrier(long id) {
//		return (Carrier) this.session.get(Carrier.class, id);
		return (Carrier) this.getSession().createCriteria(Carrier.class)
				.add(Restrictions.eq("disabled", Bool.False))
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}

	@Override
	public void saveCarrier(Carrier carier) {
		this.getSession().merge(carier);
		this.getSession().flush();
	}
	
	@Override
	public void deleteCarrier(Carrier carrier) {
		carrier.setDisabled(true);
		saveCarrier(carrier);
	}

	@Override
	public Location getLocationForName(String name) {
		return (Location) this.getSession().createCriteria(Location.class)
				.add(Restrictions.eq("name", name))
				.add(Restrictions.ne("disabled", Bool.True))
				.uniqueResult();
	}

	@Override
	public void saveLocation(Location location) {
		this.getSession().merge(location);
		this.getSession().flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Carrier> getAllCarriers() {
		return (Collection<Carrier>) this.getSession().createCriteria(Carrier.class)
				.add(Restrictions.eq("disabled", Bool.False))
				.list();
	}

	@Override
	public Route getRouteByID(long id) {
		return (Route) this.getSession().createCriteria(Route.class)
				.add(Restrictions.eq("uid.id", id))
				.add(Restrictions.ne("disabled", Bool.True))
				.uniqueResult();
	}

	@Override
	public void deleteRoute(Route route) {
		route.setDisabled(true);
		saveRoute(route);
	}

	@Override
	public void save(StorageEntity entity) {
		if (entity instanceof Route) {
			this.saveRoute((Route) entity);
			return;
		}
		
		this.getSession().save(entity);
		this.getSession().flush();
	}

	@Override
	public void delete(StorageEntity entity) {
		this.deleteRelatedEntities(entity);
		this.getSession().flush();
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
			for (Route r : (List<Route>) this.session.createCriteria(Route.class)
					.add(Restrictions.eq("primaryKey.carrier", entity))
					.list())
				this.deleteRelatedEntities(r);
		if (entity instanceof Location)
			for (Route r : (List<Route>) this.session.createCriteria(Route.class)
					.add(Restrictions.or(
							Restrictions.eq("primaryKey.startPoint", entity), 
							Restrictions.eq("primaryKey.endPoint", entity)))
					.list())
				this.deleteRelatedEntities(r);
	}

	@Override
	public DomesticCustomerPrice getDomesticCustomerPrice() {
		return (DomesticCustomerPrice) this.getSession().createCriteria(DomesticCustomerPrice.class)
					.uniqueResult();
	}

	@Override
	public void save(DomesticCustomerPrice domesticPrice) {
		this.getSession().createQuery("DELETE FROM "+DomesticCustomerPrice.class.getSimpleName()+" where priority="+domesticPrice.getPriority())
				.executeUpdate();
		this.getSession().save(domesticPrice);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Route> getAllRoutesForPriority(final Priority priority) {
		return filter((List<Route>) this.getSession().createCriteria(Route.class)
					.add(Restrictions.in("primaryKey.transportMeans", priority.ValidTransportMeans))
					.add(Restrictions.eq("disabled", Bool.False))
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
		return (Collection<Route>) this.getSession().createCriteria(Route.class)
					.add(Restrictions.eq("primaryKey.startPoint", start))
					.add(Restrictions.eq("primaryKey.endPoint", end))
					.add(Restrictions.in("primaryKey.transportMeans", priority.ValidTransportMeans))
					.add(Restrictions.eq("disabled", Bool.False))
					.list();
	}

	@Override
	public Carrier getCarrier(String name) {
		return (Carrier) this.session.createCriteria(Carrier.class)
					.add(Restrictions.eq("name", name))
					.add(Restrictions.eq("disabled", Bool.False))
					.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<CustomerPrice> getAllCustomerPrices() {
		return (List<CustomerPrice>) this.session.createCriteria(CustomerPrice.class)
					.add(Restrictions.eq("disabled", Bool.False))
					.list();
	}

	@Override
	public CustomerPrice getCustomerPriceById(long id) {
		return (CustomerPrice) this.getSession().createCriteria(CustomerPrice.class)
				.add(Restrictions.eq("uid.id", id))
				.add(Restrictions.ne("disabled", Bool.True))
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<AmountOfMail> getAmountsOfMailForAllRoutes() {
		Collection<Location> startPoints = this.getAllLocations();
		Collection<Location> endPoints = this.getAllLocations();
		Collection<MailDelivery> mailDeliveries = this.getAllMailDeliveries();
		Collection<AmountOfMail> result = new HashSet<AmountOfMail>();
		
		for (Location start : startPoints){
			for (Location end : endPoints) {
				if (start.equals(end))
					continue;
				
				int count = 0;
				double weight = 0;
				double volume = 0;
				
				for (MailDelivery m : mailDeliveries) {
					if (
							!m.getRoute().get(0).getStartPoint().equals(start) || 
							!m.getRoute().get(m.getRoute().size()-1).getEndPoint().equals(end))
						continue;
					
					count++;
					weight += m.getWeight();
					volume += m.getVolume();
				}
				
				result.add(new AmountOfMail(start, end, count, weight, volume));
			}
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<RevenueExpediture> getAllRevenueExpenditure() {
		Collection<Location> allLocations = this.getAllLocations();
		Collection<MailDelivery> mailDeliveries = this.getAllMailDeliveries();
		Collection<RevenueExpediture> result = new HashSet<RevenueExpediture>();
		
		for (Location start : allLocations) {
			for (Location end : allLocations) {
				if ((start.isInternational() && end.isInternational()) || start.equals(end))
						continue;
				
				for (Priority p : Priority.values()) {
					double revinue = 0;
					double expenditure = 0;
					double totalDeliveryTime = 0;
					long mailCount = 0;
					
					Price price = this.getPrice(start, end, p);
					
					if (price == null)
						continue;
					
					for (MailDelivery m : mailDeliveries) {
						if (
								!m.getPriority().equals(p) ||
								!m.getRoute().get(0).getStartPoint().equals(start) || 
								!m.getRoute().get(m.getRoute().size()-1).getEndPoint().equals(end)
						)
							continue;
						
						revinue += price.getCost(m);
						totalDeliveryTime += m.getShippingDuration()/msinhour;
						mailCount++;
						
						for (Route r : m.getRoute())
							expenditure += r.getCost(m);
					}
					
					result.add(new RevenueExpediture(start, end, p, revinue, expenditure, totalDeliveryTime/(double)mailCount));
				}
			}
		}
		
		return result;
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
		
		return (CustomerPrice) this.getSession().createCriteria(CustomerPrice.class)
				.add(Restrictions.eq("primaryKey.priority", priority))
				.add(Restrictions.eq("primaryKey.location", location))
				.add(Restrictions.eq("primaryKey.direction", direction))
				.add(Restrictions.eq("disabled", Bool.False))
				.uniqueResult();
	}

	@Override
	public void save(Event event) {
		event.setTimestamp(Calendar.getInstance().getTime());
		this.getSession().save(event.getUid());
		this.getSession().save(event);
	}

	@Override
	public Event getEvent(long id) {
		return (Event) this.getSession().get(Event.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Event> getAllEvents() {
		return sort((List<Event>) this.getSession().createCriteria(Event.class)
				.addOrder(Order.asc("uid.Id"))
//				.addOrder(Order.asc("timestamp"))
				.list(),
			new Comparator<Event>() {
				@Override
				public int compare(Event o1, Event o2) {
					return ((Long) o1.getId()).compareTo(o2.getId());
				}
			}
		);
	}

	@Override
	public int getNumberOfEvents() {
		return this.getSession().createCriteria(Event.class)
				.list()
				.size();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof HibernateImpl))
			return false;
		
		HibernateImpl other = (HibernateImpl) o;
		return 	this.getAllCarriers().equals(other.getAllCarriers()) &&
				this.getAllCustomerPrices().equals(other.getAllCustomerPrices()) &&
				this.getAllLocations().equals(other.getAllLocations()) &&
				this.getAllRoute().equals(other.getAllRoute()) &&
				(
						(this.getDomesticCustomerPrice() == null && other.getDomesticCustomerPrice() == null) ||
						this.getDomesticCustomerPrice().equals(other.getDomesticCustomerPrice())
				) &&
				this.getAllMailDeliveries().equals(other.getAllMailDeliveries());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<MailDelivery> getAllMailDeliveries() {
		return (Collection<MailDelivery>) this.getSession().createCriteria(MailDelivery.class)
				.add(Restrictions.eq("disabled", Bool.False))
				.list();
	}

	@Override
	public double getTotalExpenditure() {
		double sum = 0;
		for (MailDelivery m : this.getAllMailDeliveries())
			for (Route r : m.getRoute())
				sum += r.getCost(m);
		
		return sum;
	}

	@Override
	public double getTotalRevenue() {
		double sum = 0;
		for (MailDelivery m : this.getAllMailDeliveries()) {
			CustomerPrice p = this.getCustomerPrice(
					m.getRoute().get(0).getStartPoint(), 
					m.getRoute().get(m.getRoute().size()-1).getEndPoint(), 
					m.getPriority());
			
			sum += p.getCost(m);
		}
		
		return sum;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Event> getAllEventsBefore(long id) {
		return sort((List<Event>) this.getSession().createCriteria(Event.class)
				.add(Restrictions.lt("uid.id", id))
				.addOrder(Order.asc("uid.Id"))
				.list(),
			new Comparator<Event>() {
				@Override
				public int compare(Event o1, Event o2) {
					return ((Long) o1.getId()).compareTo(o2.getId());
				}
			}
		);
	}
}
