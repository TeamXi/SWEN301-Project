package nz.ac.victoria.ecs.kpsmart.entities.logging;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.entities.state.MailDelivery;

@Entity
public final class MailDeliveryUpdateEvent extends EntityUpdateEvent<MailDelivery> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MailDeliveryUpdateEvent() {super(null);}
	public MailDeliveryUpdateEvent(MailDelivery entity) {
		super(entity);
	}
}
