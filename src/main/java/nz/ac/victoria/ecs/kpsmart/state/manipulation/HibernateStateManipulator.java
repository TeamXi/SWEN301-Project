package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.util.Collection;
import java.util.List;

import javax.persistence.PersistenceContext;

import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.google.inject.Inject;

@InjectOnContruct
public final class HibernateStateManipulator implements StateManipulator {
	@Inject @PersistenceContext
	private Session session;
	
	@Override
	public MailDelivery getMailDelivery(final long id) {		
		return (MailDelivery) session.get(MailDelivery.class, id);
	}

	@Override
	public void saveMailDelivery(final MailDelivery delivery) {
		this.session.merge(delivery);
		this.session.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Location> getAllLocations() {
		return (Collection<Location>) this.session.createCriteria(Location.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Route> getAllRoute() {
		return (List<Route>) this.session.createCriteria(Route.class)
				.add(Restrictions.ne("disabled", true))
				.addOrder(Order.asc("id"))
				.list();
	}

	@Override
	public void saveRoute(Route route) {
		this.session.merge(route);
		this.session.flush();
	}

	@Override
	public Carrier getCarrier(long id) {
		return (Carrier) this.session.get(Carrier.class, id);
	}

	@Override
	public void saveCarrier(Carrier carier) {
		this.session.merge(carier);
		this.session.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Route> getAllRoutesWithPoint(Location location) {
		return (Collection<Route>) this.session.createCriteria(Route.class)
				.add( Restrictions.disjunction()
						.add(Restrictions.eq("startPoint", location))
						.add(Restrictions.eq("endPoint", location))
				)
				.list();
	}

	@Override
	public Location getLocationForName(String name) {
		return (Location) this.session.createCriteria(Location.class)
				.add(Restrictions.eq("name", name))
				.uniqueResult();
	}

	@Override
	public void saveLocation(Location location) {
		this.session.merge(location);
		this.session.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Carrier> getAllCarriers() {
		return (Collection<Carrier>) this.session.createCriteria(Carrier.class)
				.list();
	}

}
