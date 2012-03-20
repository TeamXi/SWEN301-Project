package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;

@Entity
public final class CustomerPriceUpdateEvent extends Event {

	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private Route route;
	
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

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "CustomerPriceUpdateEvent [id=" + id + ", route=" + route
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
		result = prime * result + ((route == null) ? 0 : route.hashCode());
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
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		return true;
	}
	
}
