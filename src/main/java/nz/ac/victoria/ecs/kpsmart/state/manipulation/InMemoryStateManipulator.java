/**
 * 
 */
package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;

/**
 * @author hodderdani
 *
 */
public final class InMemoryStateManipulator implements StateManipulator {
	private final Map<Long, MailDelivery> mailDeliveries = new HashMap<Long, MailDelivery>();
	private final Collection<Location> locations = new HashSet<Location>();
	
	@Override
	public MailDelivery getMailDelivery(long id) {
		return mailDeliveries.get(id);
	}

	@Override
	public void saveMailDelivery(MailDelivery delivery) {
		this.mailDeliveries.put(delivery.getId(), delivery);
	}

	@Override
	public Collection<Location> getAllLocations() {
		return locations;
	}

}
