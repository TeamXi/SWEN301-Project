package nz.ac.victoria.ecs.kpsmart.integration;

import static org.junit.Assert.*;

import nz.ac.victoria.ecs.kpsmart.GuiceServletContextListner;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.Event;

import org.junit.BeforeClass;
import org.junit.Test;

public class EntityManagerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		GuiceServletContextListner.init();
	}

	@Test
	public void test() {
		EntityManager em = GuiceServletContextListner.getInjector().getInstance(EntityManager.class);
		EntityManager memory = em.getNewInMemoryEntityManager();
		
		for (Event e : em.getLog().getAllEvents())
			memory.performEvent(e);
		
		assertEquals(em.getData(), memory.getData());
	}
}
