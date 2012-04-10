package nz.ac.victoria.ecs.kpsmart.state.entities.state;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CustomerPrice extends StorageEntity implements Serializable {
	@Id
	private CustomerPricePK primaryKey;
	
	private float pricePerUnitWeight;
	
	private float pricePerUnitVolume;
	
	@Embeddable
	public static final class CustomerPricePK implements Serializable {
		@ManyToOne
		private Location startLocation;
		
		@ManyToOne
		private Location endLocation;
		
		@Enumerated(EnumType.STRING)
		private Priority priority;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((endLocation == null) ? 0 : endLocation.hashCode());
			result = prime * result
					+ ((priority == null) ? 0 : priority.hashCode());
			result = prime * result
					+ ((startLocation == null) ? 0 : startLocation.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CustomerPricePK other = (CustomerPricePK) obj;
			if (endLocation == null) {
				if (other.endLocation != null)
					return false;
			} else if (!endLocation.equals(other.endLocation))
				return false;
			if (priority != other.priority)
				return false;
			if (startLocation == null) {
				if (other.startLocation != null)
					return false;
			} else if (!startLocation.equals(other.startLocation))
				return false;
			return true;
		}
	}

	public Location getStartLocation() {
		return getPrimaryKey().startLocation;
	}

	public void setStartLocation(Location startLocation) {
		this.getPrimaryKey().startLocation = startLocation;
	}

	public Location getEndLocation() {
		return getPrimaryKey().endLocation;
	}

	public void setEndLocation(Location endLocation) {
		this.getPrimaryKey().endLocation = endLocation;
	}

	public Priority getPriority() {
		return getPrimaryKey().priority;
	}

	public void setProirity(Priority prority) {
		this.getPrimaryKey().priority = prority;
	}

	public CustomerPricePK getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(CustomerPricePK primaryKey) {
		this.primaryKey = primaryKey;
	}

	public float getPriceperUnitWeight() {
		return pricePerUnitWeight;
	}

	public void setPriceperUnitWeight(float price) {
		this.pricePerUnitWeight = price;
	}

	public float getPricePerUnitVolume() {
		return pricePerUnitVolume;
	}

	public void setPricePerUnitVolume(float pricePerUnitVolume) {
		this.pricePerUnitVolume = pricePerUnitVolume;
	}
}
