package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.util.List;

import nz.ac.victoria.ecs.kpsmart.state.entities.log.Event;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;

public aspect LogLogger {
	private pointcut getAllEvents() : execution(List<Event> LogManipulator.getAllEvents());
	
	after() returning(List<Event> events) : getAllEvents() {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		LoggerFactory.getLogger(signature.getDeclaringType()).debug("events", events);
	}
}
