package nz.ac.victoria.ecs.kpsmart.integration;

import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.CustomerPriceUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.DomesticCustomerPriceUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.MailDeliveryEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.TransportCostUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.TransportDiscontinuedEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.LogManipulator;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.ReadOnlyLogManipulator;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.ReadOnlyStateManipulator;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.StateManipulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

/**
 * Handles calls down to the various state mechanisms. 
 * 
 * @author hodderdani
 *
 */
@InjectOnContruct
public class EntityManager {
	@Inject
	private StateManipulator manipulator;
	
	@Inject(optional=true)
	private LogManipulator log;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * Perform an entity update event on the datasource
	 * 
	 * @param event	The update event to process
	 */
	public void performEvent(EntityUpdateEvent<? extends StorageEntity> event) {
		logger.info("Performing entity update event: {}", event);
		
		manipulator.save(event.getEntity());
	}
	
	/**
	 * Perform an event to delete an entity from the datasource
	 * 
	 * @param event	The event to apply.
	 */
	public void performEvent(EntityDeleteEvent<? extends StorageEntity> event) {
		logger.info("Performing entity delete event: {}", event);
		
		manipulator.delete(event.getEntity());
	}
	
	/**
	 * Perform a route discontinued event.
	 * 
	 * @param event	The event to execute
	 */
	public void performEvent(TransportDiscontinuedEvent event) {
		logger.info("Performing transport discontinued event: {}", event);
		
		manipulator.delete(event.getRoute());
	}
	
	/**
	 * Perform a transport cost update event
	 * 
	 * @param event	The event to apply
	 */
	public void performEvent(TransportCostUpdateEvent event) {
		logger.info("Performing transport cost update event: {}", event);
		
		Route route = event.getRoute();
		route.setCarrierVolumeUnitCost(event.getNewVolumeUnitCost());
		route.setCarrierWeightUnitCost(event.getNewWeightUnitCost());
		
		manipulator.save(route);
	}
	
	/**
	 * Perform a customer price update event
	 * 
	 * @param event	The event to apply
	 */
	public void performEvent(CustomerPriceUpdateEvent event) {
		logger.info("Performing customer price update event: {}", event);
		
		CustomerPrice price = event.getCurrentPrice();
		price.setPricePerUnitVolume(event.getNewVolumeUnitCost());
		price.setPricePerUnitWeight(event.getNewWeightUnitCost());
		
		manipulator.save(price);
	}
	
	public void performEvent(DomesticCustomerPriceUpdateEvent event) {
		logger.info("Performing domestic customer price event: {}", event);
		
		manipulator.save(event.getPrice());
	}
	
	/**
	 * Perform a mail delivery event.
	 * 
	 * @param event	The event to process
	 */
	public void performEvent(MailDeliveryEvent event) {
		logger.info("Performing mail devlivery event: {}", event);
		
		manipulator.save(event.getDelivery());
	}
	
	/**
	 * Get a read only state manipulation class
	 * 
	 * @return	The read only state manipulator for accessing the current state
	 */
	public ReadOnlyStateManipulator getData() {
		logger.debug("Getting read only state manipulator");
		
		return manipulator;
	}
	
	/**
	 * Return the current log manipulator
	 * 
	 * @return	The read only copy of the log, or null if logging is diabled.
	 */
	public ReadOnlyLogManipulator getLog() {
		return log;
	}
	
	public static final class Module extends AbstractModule {
		@Override
		protected void configure() {
			bind(EntityManager.class);
		}
	}
}
