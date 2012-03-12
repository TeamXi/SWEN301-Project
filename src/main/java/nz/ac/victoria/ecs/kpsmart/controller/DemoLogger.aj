package nz.ac.victoria.ecs.kpsmart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ActionBean;

public final aspect DemoLogger {
	private final pointcut actions() : execution(Resolution ActionBean+.*());
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	before() : actions() {
		this.logger.debug("Entering event {}", ((ActionBean) thisJoinPoint.getThis()).getContext().getEventName());
	}
}
