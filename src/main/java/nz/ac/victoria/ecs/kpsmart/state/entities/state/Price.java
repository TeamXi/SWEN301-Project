package nz.ac.victoria.ecs.kpsmart.state.entities.state;

import javax.persistence.Transient;

public interface Price {
	public float getPricePerUnitWeight();
	public void setPricePerUnitWeight(float pricePerUnitWeight);
	public float getPricePerUnitVolume();
	public void setPricePerUnitVolume(float pricePerUnitVolume);
	public Priority getPriority();
	public void setPriority(Priority prority);
	
	/**
	 * Get the price for a given mail delivery
	 * 
	 * @param mail	The mail delivery
	 * @return	The cost of that mail delivery
	 */
	@Transient
	public float getCost(MailDelivery mail);
}
