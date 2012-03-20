package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;

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
}
