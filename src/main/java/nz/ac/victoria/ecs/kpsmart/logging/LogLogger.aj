package nz.ac.victoria.ecs.kpsmart.logging;

import java.util.List;

import nz.ac.victoria.ecs.kpsmart.entities.logging.EntityOperationEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.StorageEntity;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;

public aspect LogLogger {
	private pointcut getAllEvents() : execution(List<EntityOperationEvent<? extends StorageEntity>> ReadOnlyLog.getAllEvents());
	
	after() returning(List<EntityOperationEvent<? extends StorageEntity>> events) : getAllEvents() {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).debug("events", events);
	}
}
