package nz.ac.victoria.ecs.kpsmart.state.entities.state;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public final class Route implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Enumerated(EnumType.STRING)
	private TransportMeans transportMeans;
	
	private Location startPoint;
	
	private Location endPoint;
	
	private Carrier carrier;
	
	private float carrierWeightUnitCost;
	
	private float carrierVolumeUnitCost;
	
	private float customerWeightUnitCost;
	
	private float customerVolumeUnitCost;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startingTime;
	
	private int frequency;
	
	private int duration;
	
	private boolean disabled;
	
	public boolean isInternational() {
		return this.startPoint.isInternational() || this.endPoint.isInternational();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TransportMeans getTransportMeans() {
		return transportMeans;
	}

	public void setTransportMeans(TransportMeans transportMeans) {
		this.transportMeans = transportMeans;
	}

	public Location getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Location startPoint) {
		this.startPoint = startPoint;
	}

	public Location getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Location endPoint) {
		this.endPoint = endPoint;
	}

	public Carrier getCarrier() {
		return carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Route [id=" + id + ", transportMeans=" + transportMeans
				+ ", startPoint=" + startPoint + ", endPoint=" + endPoint
				+ ", carrier=" + carrier + ", carrierWeightUnitCost="
				+ carrierWeightUnitCost + ", carrierVolumeUnitCost="
				+ carrierVolumeUnitCost + ", customerWeightUnitCost="
				+ customerWeightUnitCost + ", customerVolumeUnitCost="
				+ customerVolumeUnitCost + ", startingTime=" + startingTime
				+ ", frequency=" + frequency + ", duration=" + duration
				+ ", disabled=" + disabled + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carrier == null) ? 0 : carrier.hashCode());
		result = prime * result + Float.floatToIntBits(carrierVolumeUnitCost);
		result = prime * result + Float.floatToIntBits(carrierWeightUnitCost);
		result = prime * result + Float.floatToIntBits(customerVolumeUnitCost);
		result = prime * result + Float.floatToIntBits(customerWeightUnitCost);
		result = prime * result + (disabled ? 1231 : 1237);
		result = prime * result + duration;
		result = prime * result
				+ ((endPoint == null) ? 0 : endPoint.hashCode());
		result = prime * result + frequency;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((startPoint == null) ? 0 : startPoint.hashCode());
		result = prime * result
				+ ((startingTime == null) ? 0 : startingTime.hashCode());
		result = prime * result
				+ ((transportMeans == null) ? 0 : transportMeans.hashCode());
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
		Route other = (Route) obj;
		if (carrier == null) {
			if (other.carrier != null)
				return false;
		} else if (!carrier.equals(other.carrier))
			return false;
		if (Float.floatToIntBits(carrierVolumeUnitCost) != Float
				.floatToIntBits(other.carrierVolumeUnitCost))
			return false;
		if (Float.floatToIntBits(carrierWeightUnitCost) != Float
				.floatToIntBits(other.carrierWeightUnitCost))
			return false;
		if (Float.floatToIntBits(customerVolumeUnitCost) != Float
				.floatToIntBits(other.customerVolumeUnitCost))
			return false;
		if (Float.floatToIntBits(customerWeightUnitCost) != Float
				.floatToIntBits(other.customerWeightUnitCost))
			return false;
		if (disabled != other.disabled)
			return false;
		if (duration != other.duration)
			return false;
		if (endPoint == null) {
			if (other.endPoint != null)
				return false;
		} else if (!endPoint.equals(other.endPoint))
			return false;
		if (frequency != other.frequency)
			return false;
		if (id != other.id)
			return false;
		if (startPoint == null) {
			if (other.startPoint != null)
				return false;
		} else if (!startPoint.equals(other.startPoint))
			return false;
		if (startingTime == null) {
			if (other.startingTime != null)
				return false;
		} else if (!startingTime.equals(other.startingTime))
			return false;
		if (transportMeans != other.transportMeans)
			return false;
		return true;
	}

	public float getCarrierWeightUnitCost() {
		return carrierWeightUnitCost;
	}

	public void setCarrierWeightUnitCost(float carrierWeightUnitCost) {
		this.carrierWeightUnitCost = carrierWeightUnitCost;
	}

	public float getCarrierVolumeUnitCost() {
		return carrierVolumeUnitCost;
	}

	public void setCarrierVolumeUnitCost(float carrierVolumeUnitCost) {
		this.carrierVolumeUnitCost = carrierVolumeUnitCost;
	}

	public float getCustomerWeightUnitCost() {
		return customerWeightUnitCost;
	}

	public void setCustomerWeightUnitCost(float customerWeightUnitCost) {
		this.customerWeightUnitCost = customerWeightUnitCost;
	}

	public float getCustomerVolumeUnitCost() {
		return customerVolumeUnitCost;
	}

	public void setCustomerVolumeUnitCost(float customerVolumeUnitCost) {
		this.customerVolumeUnitCost = customerVolumeUnitCost;
	}

	public Date getStartingTime() {
		return startingTime;
	}

	public void setStartingTime(Date startingTime) {
		this.startingTime = startingTime;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
