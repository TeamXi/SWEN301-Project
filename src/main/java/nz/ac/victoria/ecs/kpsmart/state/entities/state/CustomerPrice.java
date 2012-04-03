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
