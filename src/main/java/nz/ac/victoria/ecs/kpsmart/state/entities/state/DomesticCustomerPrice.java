package nz.ac.victoria.ecs.kpsmart.state.entities.state;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public final class DomesticCustomerPrice extends StorageEntity implements Price {
	@Id
	private DomesticCustomerPricePK primaryKey;
	
	@Embeddable
	public static final class DomesticCustomerPricePK implements Serializable {
		private float pricePerUnitWeight;
		
		private float pricePerUnitVolume;
		
		@Enumerated(EnumType.STRING)
		private Priority priority;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Float.floatToIntBits(pricePerUnitVolume);
			result = prime * result + Float.floatToIntBits(pricePerUnitWeight);
			result = prime * result + ((priority == null) ? 0 : priority.hashCode());
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
			DomesticCustomerPricePK other = (DomesticCustomerPricePK) obj;
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
	}

	@Override
	public float getPricePerUnitWeight() {
		return primaryKey.pricePerUnitWeight;
	}

	@Override
	public void setPricePerUnitWeight(float pricePerUnitWeight) {
		this.primaryKey.pricePerUnitWeight = pricePerUnitWeight;
	}

	@Override
	public float getPricePerUnitVolume() {
		return primaryKey.pricePerUnitVolume;
	}

	@Override
	public void setPricePerUnitVolume(float pricePerUnitVolume) {
		this.primaryKey.pricePerUnitVolume = pricePerUnitVolume;
	}
	
	@Override
	public Priority getPriority() {
		return this.primaryKey.priority;
	}

	@Override
	public void setPriority(Priority prority) {
		this.primaryKey.priority = prority;
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
}
