package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;

@Entity
public final class CustomerPriceUpdateEvent extends Event {
	@ManyToOne
	private CustomerPrice currentPrice;
	
	private float newWeightUnitCost;
	
	private float newVolumeUnitCost;

	public float getNewWeightUnitCost() {
		return newWeightUnitCost;
	}

	public void setNewWeightUnitCost(float newWeightUnitCost) {
		this.newWeightUnitCost = newWeightUnitCost;
	}

	public float getNewVolumeUnitCost() {
		return newVolumeUnitCost;
	}

	public void setNewVolumeUnitCost(float newVolumeUnitCost) {
		this.newVolumeUnitCost = newVolumeUnitCost;
	}

	public CustomerPrice getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(CustomerPrice price) {
		this.currentPrice = price;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "CustomerPriceUpdateEvent [id=" + id + ", route=" + currentPrice
				+ ", newWeightUnitCost=" + newWeightUnitCost
				+ ", newVolumeUnitCost=" + newVolumeUnitCost + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + Float.floatToIntBits(newVolumeUnitCost);
		result = prime * result + Float.floatToIntBits(newWeightUnitCost);
		result = prime * result + ((currentPrice == null) ? 0 : currentPrice.hashCode());
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
		CustomerPriceUpdateEvent other = (CustomerPriceUpdateEvent) obj;
		if (id != other.id)
			return false;
		if (Float.floatToIntBits(newVolumeUnitCost) != Float
				.floatToIntBits(other.newVolumeUnitCost))
			return false;
		if (Float.floatToIntBits(newWeightUnitCost) != Float
				.floatToIntBits(other.newWeightUnitCost))
			return false;
		if (currentPrice == null) {
			if (other.currentPrice != null)
				return false;
		} else if (!currentPrice.equals(other.currentPrice))
			return false;
		return true;
	}
	
}
