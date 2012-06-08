package nz.ac.victoria.ecs.kpsmart.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.stripes.controller.DispatcherServlet;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.mock.MockServletContext;
import nz.ac.victoria.ecs.kpsmart.GuiceServletContextListner;
import nz.ac.victoria.ecs.kpsmart.integration.EntityManager;
import nz.ac.victoria.ecs.kpsmart.logging.Log;
import nz.ac.victoria.ecs.kpsmart.reporting.Report;
import nz.ac.victoria.ecs.kpsmart.state.State;

import org.junit.Before;
import org.junit.BeforeClass;

import com.google.inject.AbstractModule;

public abstract class StripesActionBeanTest {
	@BeforeClass
	public static void guiceInit() {
		GuiceServletContextListner.init();
	}

	protected EntityManager manager;
	protected State state;
	protected Log log;
	protected Report report;
	protected MockServletContext context; 	
	
	@Before
	public void setUpContext() {
		 context = new MockServletContext("test");

		// Add the Stripes Filter
		Map<String,String> filterParams = new HashMap<String,String>();
		filterParams.put("ActionResolver.Packages", "nz.ac.victoria.ecs.kpsmart.controller");
		context.addFilter(StripesFilter.class, "StripesFilter", filterParams);

		// Add the Stripes Dispatcher
		context.setServlet(DispatcherServlet.class, "StripesDispatcher", null);
	}

	@Before
	public void setUpMocks() {
		this.manager = mock(EntityManager.class);
		this.state = mock(State.class);
		this.log = mock(Log.class);
		this.report = mock(Report.class);
		
		when(this.manager.getData()).thenReturn(state);
		when(this.manager.getLog()).thenReturn(log);
		when(this.manager.getReports()).thenReturn(report);
		
		GuiceServletContextListner.createNewInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(State.class).toInstance(state);
				bind(Log.class).toInstance(log);
				bind(Report.class).toInstance(report);
				bind(EntityManager.class).toInstance(manager);
			}
		});
	}
}
