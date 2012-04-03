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
