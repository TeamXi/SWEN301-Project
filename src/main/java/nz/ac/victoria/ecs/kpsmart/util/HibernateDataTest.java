package nz.ac.victoria.ecs.kpsmart.util;

import nz.ac.victoria.ecs.kpsmart.GuiceServletContextListner;
import nz.ac.victoria.ecs.kpsmart.integration.EntityManager;
import nz.ac.victoria.ecs.kpsmart.integration.HibernateModule;
import nz.ac.victoria.ecs.kpsmart.integration.LoggingEntityManager;
import nz.ac.victoria.ecs.kpsmart.logging.Log;
import nz.ac.victoria.ecs.kpsmart.logging.impl.HibernateLogger;
import nz.ac.victoria.ecs.kpsmart.reporting.impl.DefaultReport;
import nz.ac.victoria.ecs.kpsmart.state.State;
import nz.ac.victoria.ecs.kpsmart.state.impl.HibernateState;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class HibernateDataTest {

	@BeforeClass
	public static void setUpBeforeClass() {
		GuiceServletContextListner.init();
	}

	@Before
	public void setUp() throws Exception {
		GuiceServletContextListner.createNewInjector(
				new HibernateState.Module(), 
				new HibernateLogger.Module(),
				new HibernateModule("hibernate.memory.properties"));
		
		this.state = (HibernateState) GuiceServletContextListner.getInjector().getInstance(State.class);
		this.logger = (HibernateLogger) GuiceServletContextListner.getInjector().getInstance(Log.class);
	}

	@After
	public void tearDown() {
		GuiceServletContextListner.restorePreviousInjector();
	}

	protected HibernateState state;
	protected HibernateLogger logger;
	protected EntityManager entity;
}
