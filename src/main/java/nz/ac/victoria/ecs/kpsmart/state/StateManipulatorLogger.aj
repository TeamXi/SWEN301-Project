package nz.ac.victoria.ecs.kpsmart.state;

import java.util.Collection;
import java.util.List;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;

import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.entities.state.StorageEntity;

public final aspect StateManipulatorLogger {
	private pointcut getMailDeliveryByID(long id) : execution(MailDelivery State.getMailDelivery(long)) && args(id);
	
	before(long id) : getMailDeliveryByID(id) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).debug("Getting mail delivery for ID: {}", id);
	}
	
	after(long id) returning (MailDelivery r) : getMailDeliveryByID(id) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).debug("Got {}, for ID: {}", new Object[] {r, id} );
	}
	
	//----------------------------------
	
	private pointcut getAllMailDeliveries() : execution(Collection<MailDelivery> State.getAllMailDeliveries());
	
	after() returning(Collection<MailDelivery> deliveries) : getAllMailDeliveries() {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).trace("Got mail deliveries: {}", deliveries);
	}
	
	// ----------------------------------
	
	private pointcut getAllLocations() : execution(Collection<Location> State.getAllLocations());
	
	after() returning(Collection<Location> locations) : getAllLocations() {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).trace("Got locations: {}", locations);
	}
	
	private pointcut getLocationForName(String name) : execution(Location State.getLocationForName(String)) && args(name);
	
	before(String name) : getLocationForName(name) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).debug("Get a location by it's name: {}", name);
	}
	
	// ------------------------------------
	
	private pointcut exceptions() : execution(* State.*(..));
	
	after() throwing(Exception e) : exceptions() {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		LoggerFactory.getLogger(signature.getDeclaringType()).error("Error occured with the data backend", e);
	}
	
	// ------------------------------------
	
	private pointcut getAllRoutes() : execution(List<Route> State.getAllRoute());
	
	after() returning(List<Route> routes) : getAllRoutes() {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).trace("Got routes: {}", routes);
	}
	
	// ----------------------------------------
	
	private pointcut getCarrier(long id) : execution(Carrier State.getCarrier(long)) && args(id);
	
	before(long id) : getCarrier(id) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).debug("Getting carrier for id: {}", id);
	}

	// ----------------------------------------
	
	private pointcut getAllCarriers() : execution(Collection<Carrier> State.getAllCarriers());
	
	after() returning(Collection<Carrier> r) : getAllCarriers() {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).trace("Got carriers: {}", r);
	}
	
	// ----------------------------------------
	
	private pointcut save(StorageEntity entity) : execution(void State.save(StorageEntity)) && args(entity);
	before(StorageEntity entity) : save(entity) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		LoggerFactory.getLogger(signature.getDeclaringType()).debug("Saving entity: {}", entity);
	}
	
	private pointcut delete(StorageEntity entity) : execution(void State.delete(StorageEntity)) && args(entity);
	before(StorageEntity entity) : delete(entity) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		LoggerFactory.getLogger(signature.getDeclaringType()).debug("Deleting entity: {}", entity);
	}
}
