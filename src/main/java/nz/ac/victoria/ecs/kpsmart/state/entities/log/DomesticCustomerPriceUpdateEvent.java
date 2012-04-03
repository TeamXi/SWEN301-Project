package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.DomesticCustomerPrice;

@Entity
public final class DomesticCustomerPriceUpdateEvent extends Event {
	@ManyToOne
	private DomesticCustomerPrice price;

	public DomesticCustomerPrice getPrice() {
		return price;
	}

	public void setPrice(DomesticCustomerPrice price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "DomesticCustomerPriceUpdateEvent [price=" + price + ", id="
				+ id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		DomesticCustomerPriceUpdateEvent other = (DomesticCustomerPriceUpdateEvent) obj;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}
}
