package nz.ac.victoria.ecs.kpsmart.logging.impl;

import static nz.ac.victoria.ecs.kpsmart.util.ListUtils.sort;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.entities.logging.EntityOperationEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.StorageEntity;
import nz.ac.victoria.ecs.kpsmart.logging.Log;
import nz.ac.victoria.ecs.kpsmart.logging.ReadOnlyLog;

@InjectOnContruct
public final class HibernateLogger implements Log, ReadOnlyLog {
	@Inject
	private Session session;
	
	private Long maxEventID = null;
	
	/**
	 * The default no-op constuctor
	 */
	public HibernateLogger() {}
	
	/**
	 * Construct a log with a max event id.
	 * 
	 * @param maxID	The maximum ID of the event.
	 */
	public HibernateLogger(long maxID) {
		assert maxID >= 0;
		
		this.maxEventID = maxID;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public EntityOperationEvent<? extends StorageEntity> getEvent(long id) {
		return (EntityOperationEvent<? extends StorageEntity>) this.getEventCriteria(EntityOperationEvent.class)
				.add(Restrictions.eq("uid.id", id))
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EntityOperationEvent<? extends StorageEntity>> getAllEvents() {
		return sort((List<EntityOperationEvent<? extends StorageEntity>>) this.getEventCriteria(EntityOperationEvent.class)
				.addOrder(Order.asc("uid.Id"))
				.list(),
			new Comparator<EntityOperationEvent<? extends StorageEntity>>() {
				@Override
				public int compare(EntityOperationEvent<? extends StorageEntity> o1, EntityOperationEvent<? extends StorageEntity> o2) {
					return ((Long) o1.getId()).compareTo(o2.getId());
				}
			}
		);
	}
	
	@Override
	public List<EntityOperationEvent<? extends StorageEntity>> getAllEventsBefore(long id) {
		return null;
	}

	@Override
	public void save(EntityOperationEvent<? extends StorageEntity> event) {
		event.setTimestamp(Calendar.getInstance().getTime());
		this.session.save(event.getUid());
		this.session.save(event);
	}
	
	@SuppressWarnings("rawtypes")
	private Criteria getEventCriteria(Class<? extends EntityOperationEvent> clazz) {
		if (this.maxEventID == null)
			return this.session.createCriteria(clazz);
		else 
			return this.session.createCriteria(clazz)
					.add(Restrictions.le("uid.id", this.maxEventID));
	}

	@Override
	public Log getAtEventID(long eventID) {
		return new HibernateLogger(eventID);
	}
	
	public static final class Module extends AbstractModule {
		@Override
		protected void configure() {
			bind(Log.class).to(HibernateLogger.class);
			bind(ReadOnlyLog.class).to(HibernateLogger.class);
		}
	}

	@Override
	public int getNumberOfEvents() {
		return getAllEvents().size();
	}
}
