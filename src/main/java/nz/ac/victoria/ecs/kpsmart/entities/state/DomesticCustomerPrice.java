package nz.ac.victoria.ecs.kpsmart.entities.state;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public final class DomesticCustomerPrice extends StorageEntity implements Price {
	@Id
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	private float pricePerUnitWeight;
	
	private float pricePerUnitVolume;
	
	@Override
	public boolean isNonUnique(StorageEntity entity) {
		if (!(entity instanceof DomesticCustomerPrice))
			return false;
		DomesticCustomerPrice d = (DomesticCustomerPrice) entity;
		
		return  this.priority == d.priority;
	}
	
	@Override
	public float getPricePerUnitWeight() {
		return pricePerUnitWeight;
	}

	@Override
	public void setPricePerUnitWeight(float pricePerUnitWeight) {
		this.pricePerUnitWeight = pricePerUnitWeight;
	}

	@Override
	public float getPricePerUnitVolume() {
		return pricePerUnitVolume;
	}

	@Override
	public void setPricePerUnitVolume(float pricePerUnitVolume) {
		this.pricePerUnitVolume = pricePerUnitVolume;
	}
	
	@Override
	public Priority getPriority() {
		return this.priority;
	}

	@Override
	public void setPriority(Priority prority) {
		this.priority = prority;
	}
	
	/**
	 * Get the price for a given mail delivery
	 * 
	 * @param mail	The mail delivery
	 * @return	The cost of that mail delivery
	 */
	@Transient
	public float getCost(MailDelivery mail) {
		return Math.max(mail.getVolume() * this.getPricePerUnitVolume(), mail.getWeight() * this.getPricePerUnitWeight());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(pricePerUnitVolume);
		result = prime * result + Float.floatToIntBits(pricePerUnitWeight);
		result = prime * result
				+ ((priority == null) ? 0 : priority.hashCode());
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
		DomesticCustomerPrice other = (DomesticCustomerPrice) obj;
		if (Float.floatToIntBits(pricePerUnitVolume) != Float
				.floatToIntBits(other.pricePerUnitVolume))
			return false;
		if (Float.floatToIntBits(pricePerUnitWeight) != Float
				.floatToIntBits(other.pricePerUnitWeight))
			return false;
		if (priority != other.priority)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DomesticCustomerPrice [priority=" + priority
				+ ", pricePerUnitWeight=" + pricePerUnitWeight
				+ ", pricePerUnitVolume=" + pricePerUnitVolume + "]";
	}
}
