package nz.ac.victoria.ecs.kpsmart.entities.logging;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.entities.state.MailDelivery;

@Entity
public final class MailDeliveryDeleteEvent extends EntityDeleteEvent<MailDelivery> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MailDeliveryDeleteEvent() {super(null);}
	public MailDeliveryDeleteEvent(MailDelivery entity) {
		super(entity);
	}
}
