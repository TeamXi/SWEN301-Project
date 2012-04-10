package nz.ac.victoria.ecs.kpsmart.state.entities.state;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public final class DomesticCustomerPrice extends StorageEntity {
	@Id
	private DomesticCustomerPricePK primaryKey;
	
	@Embeddable
	public static final class DomesticCustomerPricePK implements Serializable {
		private float pricePerUnitWeight;
		
		private float pricePerUnitVolume;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Float.floatToIntBits(pricePerUnitVolume);
			result = prime * result + Float.floatToIntBits(pricePerUnitWeight);
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
			return true;
		}
	}

	public float getPricePerUnitWeight() {
		return primaryKey.pricePerUnitWeight;
	}

	public void setPricePerUnitWeight(float pricePerUnitWeight) {
		this.primaryKey.pricePerUnitWeight = pricePerUnitWeight;
	}

	public float getPricePerUnitVolume() {
		return primaryKey.pricePerUnitVolume;
	}

	public void setPricePerUnitVolume(float pricePerUnitVolume) {
		this.primaryKey.pricePerUnitVolume = pricePerUnitVolume;
	}
}
