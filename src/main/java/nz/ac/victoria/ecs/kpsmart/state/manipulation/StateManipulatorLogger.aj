package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.util.Collection;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Carrier;

public final aspect StateManipulatorLogger {
	private pointcut getMailDeliveryByID(long id) : execution(MailDelivery StateManipulator.getMailDelivery(long)) && args(id);
	
	before(long id) : getMailDeliveryByID(id) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).info("Getting mail delivery for ID: {}", id);
	}
	
	after(long id) returning (MailDelivery r) : getMailDeliveryByID(id) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).debug("Got {}, for ID: {}", new Object[] {r, id} );
	}
	
	//----------------------------------
	
	private pointcut saveMailDelivery(MailDelivery md) : execution(void StateManipulator.saveMailDelivery(MailDelivery)) && args(md);
	
	before(MailDelivery md) : saveMailDelivery(md) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).info("Saving mail delivery: {}", md);
	}
	
	// ----------------------------------
	
	private pointcut getAllLocations() : execution(Collection<Location> StateManipulator.getAllLocations());
	
	before(): getAllLocations(){
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).info("Getting all locations");
	}
	
	after() returning(Collection<Location> locations) : getAllLocations() {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).debug("Got locations: {}", locations);
	}
	
	private pointcut getLocationForName(String name) : execution(Location StateManipulator.getLocationForName(String)) && args(name);
	
	before(String name) : getLocationForName(name) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).info("Get a location by it's name: {}", name);
	}
	
	// ------------------------------------
	
	private pointcut exceptions() : execution(* StateManipulator.*(..));
	
	after() throwing(Exception e) : exceptions() {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		LoggerFactory.getLogger(signature.getDeclaringType()).error("Error occured with the data backend", e);
	}
	
	// ------------------------------------
	
	private pointcut getAllRoutes() : execution(Collection<Route> StateManipulator.getAllRoutes());
	
	before() : getAllRoutes() {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).info("Getting all routes");
	}
	
	private pointcut getAllRoutesWithPoint(Location location) : 
		execution(Carrier StateManipulator.getAllRoutesWithPoint(Location)) && args(location);
	
	before(Location location) : getAllRoutesWithPoint(location) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).info("Getting all locations with a point of: {}", location);
	}
	
	private pointcut saveRoute(Route route) : execution(void StateManipulator.saveRoute(Route)) && args(route);
	
	before(Route route) : saveRoute(route) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).info("Saving route: {}", route);
	}
	
	// ----------------------------------------
	
	private pointcut getCarrier(long id) : execution(Carrier StateManipulator.getCarrier(long)) && args(id);
	
	before(long id) : getCarrier(id) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).info("Getting carrier for id: {}", id);
	}
	
	private pointcut saveCarrier(Carrier carrier) : execution(void StateManipulator.saveCarrier(Carrier)) && args(carrier);
	
	before(Carrier carrier) : saveCarrier(carrier) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).info("Saving carrier: {}", carrier);
	}
}
