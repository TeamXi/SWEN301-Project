package nz.ac.victoria.ecs.kpsmart.util;

import org.aspectj.lang.reflect.MethodSignature;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.LoggerFactory;

public final aspect TestLogger {
	private pointcut beforeCall() : this(Object) && (execution(@Before * *()) || execution(@BeforeClass * * ()));
	
	before() : beforeCall() {
		MethodSignature sig = (MethodSignature) thisJoinPoint.getSignature();
		LoggerFactory.getLogger(thisJoinPoint.getThis().getClass()).debug("Calling before method {}", sig.getMethod());
	}
	
	private pointcut afterCall() : this(Object) && (execution(@After * * ()) || execution(@AfterClass * * ()));
	
	before() : afterCall() {
		MethodSignature sig = (MethodSignature) thisJoinPoint.getSignature();
		LoggerFactory.getLogger(thisJoinPoint.getThis().getClass()).debug("Calling after method {}", sig.getMethod());
	}
}
