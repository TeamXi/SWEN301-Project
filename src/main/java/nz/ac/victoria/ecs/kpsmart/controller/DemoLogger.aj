package nz.ac.victoria.ecs.kpsmart.controller;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ActionBean;

public final aspect DemoLogger {
	private final pointcut actions() : this(Object) && execution(Resolution ActionBean+.*());
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	before() : actions() {
		this.logger.debug("Entering event {}", ((ActionBean) thisJoinPoint.getThis()).getContext().getEventName());
		
		if (this.logger.isTraceEnabled()) {
			try {
				BeanInfo i = Introspector.getBeanInfo(thisJoinPoint.getThis().getClass());
				for (PropertyDescriptor p : i.getPropertyDescriptors())
					logger.trace("Property {} ({}) has value {}", new Object[] {
							p.getDisplayName(),
							p.getPropertyType(),
							p.getValue(null)
					}); 
			} catch (IntrospectionException e) {
				logger.error("Could not look into action bean", e);
			}
		}
	}
}
