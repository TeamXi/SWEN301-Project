package nz.ac.victoria.ecs.kpsmart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final aspect GuiceInjector {
	private final Logger logger = LoggerFactory.getLogger(GuiceInjector.class);
	private pointcut injectOnConstruct() : this(Object) && execution((@InjectOnContruct *).new(..));
	private pointcut injectOnCall() : this(Object) && execution(@InjectOnCall * *(..));
	
	before() : injectOnConstruct() || injectOnCall() {
		logger.debug("Injecting type {}", thisJoinPoint.getSignature().getDeclaringType().getName());
		
		GuiceServletContextListner.getInjector().injectMembers(thisJoinPoint.getThis());
	}
}
