package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import static org.junit.Assert.*;

import nz.ac.victoria.ecs.kpsmart.GuiceServletContextListner;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class HibernateStateManipulatorDBTest {

	@Before
	public void setUp() throws Exception {
		GuiceServletContextListner.init();
	}

	@Test
	public void testGetMailDelivery() {
		
	}

	@Test
	public void testSaveMailDelivery() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllLocations() {
		fail("Not yet implemented");
	}

}
