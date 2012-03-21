package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.util.Collection;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;

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
	
	// ------------------------------------
	
	private pointcut exceptions() : execution(* StateManipulator.*(..));
	
	after() throwing(Exception e) : exceptions() {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		LoggerFactory.getLogger(signature.getDeclaringType()).error("Error occured with the data backend", e);
	}
}
