package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import javax.persistence.EntityManager;

import nz.ac.victoria.ecs.kpsmart.GuiceServletContextListner;
import nz.ac.victoria.ecs.kpsmart.InjectOnCall;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Inject;

public class HibernateStateManipulatorDBTest {
	@Inject
	private EntityManager em;

	@Before
	public void setUp() throws Exception {
		GuiceServletContextListner.init();
	}

	@Test @Ignore
	public void testGetMailDelivery() {
		
	}

	@Test @Ignore
	public void testSaveMailDelivery() {
		fail("Not yet implemented");
	}

	@Test @InjectOnCall
	public void testGetAllLocations() {
		Location l = new Location();
		l.setInternational(true);
		l.setLatitude(-1);
		l.setLongitude(1);
		l.setName("fubar");
		
		this.em.persist(l);
		
		assertEquals(Arrays.asList(l), new HibernateStateManipulator().getAllLocations());
	}

}
