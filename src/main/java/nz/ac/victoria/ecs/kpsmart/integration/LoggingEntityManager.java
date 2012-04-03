package nz.ac.victoria.ecs.kpsmart.integration;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.CustomerPriceUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.DomesticCustomerPriceUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.MailDeliveryEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.TransportCostUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.TransportDiscontinuedEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.LogManipulator;

@InjectOnContruct
public class LoggingEntityManager extends EntityManager {
	@Inject
	private LogManipulator log;
	
	@Override
	public void performEvent(EntityUpdateEvent<? extends StorageEntity> event) {
		log.save(event);
		
		super.performEvent(event);
	}
	
	@Override
	public void performEvent(EntityDeleteEvent<? extends StorageEntity> event) {
		log.save(event);
		
		super.performEvent(event);
	}
	
	@Override
	public void performEvent(TransportDiscontinuedEvent event) {
		log.save(event);
		
		super.performEvent(event);
	}
	
	@Override
	public void performEvent(TransportCostUpdateEvent event) {
		log.save(event);
		
		super.performEvent(event);
	}
	
	@Override
	public void performEvent(CustomerPriceUpdateEvent event) {
		log.save(event);
		
		super.performEvent(event);
	}
	
	@Override
	public void performEvent(DomesticCustomerPriceUpdateEvent event) {
		log.save(event);
		
		super.performEvent(event);
	}
	
	@Override
	public void performEvent(MailDeliveryEvent event) {
		log.save(event);
		
		super.performEvent(event);
	}
	
	public static final class Module extends AbstractModule {
		@Override
		protected void configure() {
			bind(EntityManager.class).to(LoggingEntityManager.class);
		}
	}
}
