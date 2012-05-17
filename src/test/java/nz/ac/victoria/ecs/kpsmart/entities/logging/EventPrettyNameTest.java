package nz.ac.victoria.ecs.kpsmart.entities.logging;

import static org.junit.Assert.*;

import org.junit.Test;

public class EventPrettyNameTest {
	@Test
	public void testPrettyName() {
		assertEquals("Carrier Update Event", new CarrierUpdateEvent().prettyName());
	}
}
