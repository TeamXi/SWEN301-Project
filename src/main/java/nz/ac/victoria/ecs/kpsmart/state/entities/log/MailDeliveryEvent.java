package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;

@Entity
public final class MailDeliveryEvent extends Event {
	
	@ManyToOne
	private MailDelivery delivery;
	
	public MailDeliveryEvent() {}
	public MailDeliveryEvent(MailDelivery md) {
		this.setDelivery(md);
	}

	public MailDelivery getDelivery() {
		return delivery;
	}

	public void setDelivery(MailDelivery delivery) {
		this.delivery = delivery;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((delivery == null) ? 0 : delivery.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MailDeliveryEvent other = (MailDeliveryEvent) obj;
		if (delivery == null) {
			if (other.delivery != null)
				return false;
		} else if (!delivery.equals(other.delivery))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MailDeliveryEvent [delivery=" + delivery + ", id=" + getId() + "]";
	}

	
}
