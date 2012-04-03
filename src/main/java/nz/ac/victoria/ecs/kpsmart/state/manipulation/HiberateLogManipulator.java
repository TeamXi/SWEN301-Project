package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.util.Calendar;
import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.google.inject.Inject;

import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.Event;

@InjectOnContruct
public class HiberateLogManipulator implements LogManipulator {
	@Inject @PersistenceContext
	private Session session;
	
	@Override
	public void save(Event event) {
		event.setTimestamp(Calendar.getInstance().getTime());
		this.session.save(event);
	}

	@Override
	public Event getEvent(long id) {
		return (Event) this.session.get(Event.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Event> getAllEvents() {
		return (List<Event>) this.session.createCriteria(Event.class)
				.addOrder(Order.asc("timestamp"))
				.list();
	}

}
