package nz.ac.victoria.ecs.kpsmart.integration;

import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.entities.logging.EntityDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.EntityUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.StorageEntity;
import nz.ac.victoria.ecs.kpsmart.logging.Log;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

@InjectOnContruct
public class LoggingEntityManager extends EntityManager {
	@Inject
	private Log log;
	
	@Override
	public void performEvent(EntityUpdateEvent<? extends StorageEntity> event) {
		log.save(event);
		event.getEntity().setRelateEventID(event.getUid());
		
		super.performEvent(event);
	}
	
	@Override
	public void performEvent(EntityDeleteEvent<? extends StorageEntity> event) {
		log.save(event);
		event.getEntity().setRelateEventID(event.getUid());
		
		super.performEvent(event);
	}
	
	public static final class Module extends AbstractModule {
		@Override
		protected void configure() {
			bind(EntityManager.class).to(LoggingEntityManager.class);
		}
	}
}
