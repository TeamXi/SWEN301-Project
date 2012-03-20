package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import nz.ac.victoria.ecs.kpsmart.GuiceServletContextListner;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import static org.mockito.Mockito.*;

public class HibernateStateManipulatorTest {
	private MailDelivery mailDelivery;
	private EntityManager em;
	
	@Before
	public void setUp() throws Exception {
		this.mailDelivery = new MailDelivery();
		this.mailDelivery.setId(1);
		
		em = mock(EntityManager.class);
		
		GuiceServletContextListner.overloadModules(new AbstractModule() {
			@Override
			protected void configure() {
				bind(EntityManager.class).toInstance(em);
			}
		});
	}

	@Test
	public void testGetMailDelivery() {
		when(em.find(MailDelivery.class, (long) 1)).thenReturn(mailDelivery);
		
		assertEquals(this.mailDelivery, new HibernateStateManipulator().getMailDelivery(1));
		verify(em).find(MailDelivery.class, (long) 1);
	}

	@Test
	public void testSaveMailDelivery() {
		when(em.merge(mailDelivery)).thenReturn(mailDelivery);
		
		new HibernateStateManipulator().saveMailDelivery(mailDelivery);
		
		verify(em).merge(mailDelivery);
	}

	@Test @Ignore("Too hard to test")
	public void testGetAllLocations() {
	}

}
