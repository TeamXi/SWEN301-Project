package nz.ac.victoria.ecs.kpsmart.entities.state;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public final class Route extends StorageEntity implements Serializable {
	@Cascade(CascadeType.ALL)
	@OneToOne
	private EntityID uid;
	
	@Id
	@GeneratedValue
	private long u;
	
	private RoutePK primaryKey = new RoutePK();
	
	private float carrierWeightUnitCost;
	
	private float carrierVolumeUnitCost;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startingTime;
	
	private int frequency;
	
	private int duration;
	
	@Override
	public boolean isNonUnique(final StorageEntity entity) {
		if (!(entity instanceof Route))
			return false;
		final Route route = (Route) entity;
		
		return 
				this.primaryKey.transportMeans.equals(route.primaryKey.transportMeans) &&
				this.primaryKey.startPoint.equals(route.primaryKey.startPoint) &&
				this.primaryKey.endPoint.equals(route.primaryKey.endPoint) &&
				this.primaryKey.carrier.equals(route.primaryKey.carrier); 
	}
	
	@Embeddable
	public static final class RoutePK implements Serializable {
		private static final long serialVersionUID = 1L;

		@Enumerated(EnumType.STRING)
		private TransportMeans transportMeans;
		
		@OneToOne
		private Location startPoint;
		
		@OneToOne
		private Location endPoint;
		
		@ManyToOne
		private Carrier carrier;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((this.carrier == null) ? 0 : this.carrier.hashCode());
			result = prime * result
					+ ((this.endPoint == null) ? 0 : this.endPoint.hashCode());
			result = prime * result
					+ ((this.startPoint == null) ? 0 : this.startPoint.hashCode());
			result = prime
					* result
					+ ((this.transportMeans == null) ? 0 : this.transportMeans.hashCode());
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final RoutePK other = (RoutePK) obj;
			if (this.carrier == null) {
				if (other.carrier != null)
					return false;
			} else if (!this.carrier.equals(other.carrier))
				return false;
			if (this.endPoint == null) {
				if (other.endPoint != null)
					return false;
			} else if (!this.endPoint.equals(other.endPoint))
				return false;
			if (this.startPoint == null) {
				if (other.startPoint != null)
					return false;
			} else if (!this.startPoint.equals(other.startPoint))
				return false;
			if (this.transportMeans != other.transportMeans)
				return false;
			return true;
		}
	}
	
	Route() {}
	public Route(final TransportMeans trans, final Location start, final Location end, final Carrier carrier) {
		this.primaryKey.transportMeans = trans;
		this.primaryKey.startPoint = start;
		this.primaryKey.endPoint = end;
		this.primaryKey.carrier = carrier;
		this.uid = new EntityID();
	}
	
	public boolean isInternational() {
		return getPrimaryKey().startPoint.isInternational() || getPrimaryKey().endPoint.isInternational();
	}
	
	public long getId() {
		return (this.uid == null)?0:this.uid.getId();
	}

	public EntityID getUid() {
		return this.uid;
	}

	public void setUid(final EntityID uid) {
		this.uid = uid;
	}

	public TransportMeans getTransportMeans() {
		return getPrimaryKey().transportMeans;
	}

	public void setTransportMeans(final TransportMeans transportMeans) {
		getPrimaryKey().transportMeans = transportMeans;
	}

	public Location getStartPoint() {
		return getPrimaryKey().startPoint;
	}

	public void setStartPoint(final Location startPoint) {
		getPrimaryKey().startPoint = startPoint;
	}

	public Location getEndPoint() {
		return getPrimaryKey().endPoint;
	}

	public void setEndPoint(final Location endPoint) {
		getPrimaryKey().endPoint = endPoint;
	}

	public Carrier getCarrier() {
		return getPrimaryKey().carrier;
	}

	public void setCarrier(final Carrier carrier) {
		getPrimaryKey().carrier = carrier;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Route [id=" + getId() + ", transportMeans=" + getPrimaryKey().transportMeans
				+ ", startPoint=" + getPrimaryKey().startPoint + ", endPoint=" + getPrimaryKey().endPoint
				+ ", carrier=" + getPrimaryKey().carrier + ", carrierWeightUnitCost="
				+ this.carrierWeightUnitCost + ", carrierVolumeUnitCost="
				+ this.carrierVolumeUnitCost + ", startingTime=" + this.startingTime
				+ ", frequency=" + this.frequency + ", duration=" + this.duration
				+ ", disabled=" + this.disabled + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getPrimaryKey().carrier == null) ? 0 : getPrimaryKey().carrier.hashCode());
		result = prime * result + Float.floatToIntBits(this.carrierVolumeUnitCost);
		result = prime * result + Float.floatToIntBits(this.carrierWeightUnitCost);
		result = prime * result
				+ ((this.disabled == null) ? 0 : this.disabled.hashCode());
		result = prime * result + this.duration;
		result = prime * result
				+ ((getPrimaryKey().endPoint == null) ? 0 : getPrimaryKey().endPoint.hashCode());
		result = prime * result + this.frequency;
		result = prime * result + (int) (getId() ^ (getId() >>> 32));
		result = prime * result
				+ ((getPrimaryKey().startPoint == null) ? 0 : getPrimaryKey().startPoint.hashCode());
		result = prime * result
				+ ((this.startingTime == null) ? 0 : this.startingTime.hashCode());
		result = prime * result
				+ ((getPrimaryKey().transportMeans == null) ? 0 : getPrimaryKey().transportMeans.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Route other = (Route) obj;
		if (getPrimaryKey().carrier == null) {
			if (other.getPrimaryKey().carrier != null)
				return false;
		} else if (!getPrimaryKey().carrier.equals(other.getPrimaryKey().carrier))
			return false;
		if (Float.floatToIntBits(this.carrierVolumeUnitCost) != Float
				.floatToIntBits(other.carrierVolumeUnitCost))
			return false;
		if (Float.floatToIntBits(this.carrierWeightUnitCost) != Float
				.floatToIntBits(other.carrierWeightUnitCost))
			return false;
		if (this.disabled != other.disabled)
			return false;
		if (this.duration != other.duration)
			return false;
		if (getPrimaryKey().endPoint == null) {
			if (other.getPrimaryKey().endPoint != null)
				return false;
		} else if (!getPrimaryKey().endPoint.equals(other.getPrimaryKey().endPoint))
			return false;
		if (this.frequency != other.frequency)
			return false;
		if (getId() != other.getId())
			return false;
		if (getPrimaryKey().startPoint == null) {
			if (other.getPrimaryKey().startPoint != null)
				return false;
		} else if (!getPrimaryKey().startPoint.equals(other.getPrimaryKey().startPoint))
			return false;
		if (this.startingTime == null) {
			if (other.startingTime != null)
				return false;
		} else if (!this.startingTime.equals(other.startingTime))
			return false;
		if (getPrimaryKey().transportMeans != other.getPrimaryKey().transportMeans)
			return false;
		return true;
	}

	public float getCarrierWeightUnitCost() {
		return this.carrierWeightUnitCost;
	}

	public void setCarrierWeightUnitCost(final float carrierWeightUnitCost) {
		this.carrierWeightUnitCost = carrierWeightUnitCost;
	}

	public float getCarrierVolumeUnitCost() {
		return this.carrierVolumeUnitCost;
	}

	public void setCarrierVolumeUnitCost(final float carrierVolumeUnitCost) {
		this.carrierVolumeUnitCost = carrierVolumeUnitCost;
	}

	public Date getStartingTime() {
		return this.startingTime;
	}

	public void setStartingTime(final Date startingTime) {
		this.startingTime = startingTime;
	}

	public int getFrequency() {
		return this.frequency;
	}

	public void setFrequency(final int frequency) {
		this.frequency = frequency;
	}

	/**
	 * get the duration of the delivery in hours
	 * @return
	 */
	public int getDuration() {
		return this.duration;
	}

	/**
	 * set the duration of the delivery in hours
	 * @param duration
	 */
	public void setDuration(final int duration) {
		this.duration = duration;
	}

	public RoutePK getPrimaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(final RoutePK primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public float getCost(final MailDelivery mail) {
		return getCost(mail.getWeight(), mail.getVolume());
	}
	
	public float getCost(final float weight, final float volume) {
		return volume * getCarrierVolumeUnitCost() + weight * getCarrierWeightUnitCost();
	}
	
	public long getU() {
		return this.u;
	}
	public void setU(final long u) {
		this.u = u;
	}
}
