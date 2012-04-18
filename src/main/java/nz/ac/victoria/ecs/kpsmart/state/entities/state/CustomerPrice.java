package nz.ac.victoria.ecs.kpsmart.state.entities.state;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class CustomerPrice extends StorageEntity implements Serializable, Price {
	@Cascade(CascadeType.ALL)
	@OneToOne
	private CustomerPriceID uid;
	
	@Id
	private CustomerPricePK primaryKey = new CustomerPricePK();
	
	private float pricePerUnitWeight;
	
	private float pricePerUnitVolume;
	
	@Embeddable
	public static final class CustomerPricePK implements Serializable {
		@ManyToOne
		private Location location;
		
		@Enumerated(EnumType.STRING)
		private Direction direction = Direction.From;
		
		@Enumerated(EnumType.STRING)
		private Priority priority;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((direction == null) ? 0 : direction.hashCode());
			result = prime * result
					+ ((location == null) ? 0 : location.hashCode());
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
			CustomerPricePK other = (CustomerPricePK) obj;
			if (direction != other.direction)
				return false;
			if (location == null) {
				if (other.location != null)
					return false;
			} else if (!location.equals(other.location))
				return false;
			if (priority != other.priority)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "CustomerPricePK [location=" + location + ", direction="
					+ direction + ", priority=" + priority + "]";
		}
	}
	
	public static CustomerPrice newInstance() {
		CustomerPrice p = new CustomerPrice();
		p.uid = new CustomerPriceID();
		return p;
	}
	
	private CustomerPrice() {
	}
	
	public long getId() {
		return uid.getId();
	}
	
	public void setId(long id) {
		uid.setId(id);
	}

	/**
	 * May be null to designate new zealand
	 */
	public Location getStartLocation() {
		if (this.getPrimaryKey().direction == Direction.From)
			return this.getPrimaryKey().location;
		
		return null;
	}

	/**
	 * May be null to designate new zealand
	 */
	public void setStartLocation(Location startLocation) {
		if (startLocation == null || !startLocation.isInternational())
			return;
		
		this.getPrimaryKey().direction = Direction.From;
		this.getPrimaryKey().location = startLocation;
	}

	/**
	 * May be null to designate new zealand
	 */
	public Location getEndLocation() {
		if (this.getPrimaryKey().direction == Direction.To)
			return this.getPrimaryKey().location;
		
		return null;
	}

	/**
	 * May be null to designate new zealand
	 */
	public void setEndLocation(Location endLocation) {
		if (endLocation == null || !endLocation.isInternational())
			return;
		
		this.getPrimaryKey().direction = Direction.To;
		this.getPrimaryKey().location = endLocation;
	}

	@Override
	public Priority getPriority() {
		return getPrimaryKey().priority;
	}

	@Override
	public void setPriority(Priority prority) {
		this.getPrimaryKey().priority = prority;
	}

	public CustomerPricePK getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(CustomerPricePK primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	public float getPricePerUnitWeight() {
		return pricePerUnitWeight;
	}

	@Override
	public void setPricePerUnitWeight(float price) {
		this.pricePerUnitWeight = price;
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
	@Transient
	public float getCost(MailDelivery mail) {
		return Math.max(mail.getVolume() * this.pricePerUnitVolume, mail.getWeight() * this.pricePerUnitWeight);
	}
	
	public Location getLocation() {
		return primaryKey.location;
	}
	
	public void setLocation(Location l) {
		primaryKey.location = l;
	}
	
	public Direction getDirection() {
		return primaryKey.direction;
	}
	
	public void setDirection(Direction d) {
		primaryKey.direction = d;
	}
}
