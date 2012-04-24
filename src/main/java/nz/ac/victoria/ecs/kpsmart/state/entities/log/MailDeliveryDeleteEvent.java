package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Entity;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;

@Entity
public final class MailDeliveryDeleteEvent extends EntityDeleteEvent<MailDelivery> {
	public MailDeliveryDeleteEvent() {super(null);}
	public MailDeliveryDeleteEvent(MailDelivery entity) {
		super(entity);
	}
}
