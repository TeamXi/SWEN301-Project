package nz.ac.victoria.ecs.kpsmart.entities.state;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.state.State;
import nz.ac.victoria.ecs.kpsmart.util.RouteDurationCalculator;

import com.google.inject.Inject;

@Entity 
public final class MailDelivery extends StorageEntity implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToMany
	private List<Route> route;
	
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	private float weight;
	
	private float volume;
	
	private Date submissionDate;
	
	private long shippingDuration;
	
	private float price;
	
	private float cost;
	
	MailDelivery() {}
	public MailDelivery(
			List<Route> route,
			Priority priority,
			float weight, 
			float volume,
			Date submissionDate
	) {
		this.route = route;
		this.priority = priority;
		this.weight = weight;
		this.volume = volume;
		this.submissionDate = submissionDate;
		this.shippingDuration = RouteDurationCalculator.calculate(route, submissionDate);
		
		this.fillCalculatedFields();
	}
	
	@Override
	public boolean isNonUnique(StorageEntity entity) {
		if (!(entity instanceof MailDelivery))
			return false;
		MailDelivery m = (MailDelivery) entity;
		
		return  this.id == m.id;
	}
	
	public void fillCalculatedFields() {
		this.cost = Util.getInstance().calculateCost(this);
		this.price = Util.getInstance().calculatePrice(this);
	}
	
	public boolean isInternational() {
		for (Route r : this.route)
			if (!r.isInternational())
				return false;
		
		return true;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Route> getRoute() {
		return route;
	}

	public void setRoute(List<Route> route) {
		this.route = route;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "MailDelivery [id=" + id + " uid=" + relateEventID + ", disabled=" + disabled + ", route=" + route + ", priority="
				+ priority + ", weight=" + weight + ", volume=" + volume
				+ ", submissionDate=" + submissionDate + ", shippingDuration="
				+ shippingDuration + ", price=" + price + ", cost=" + cost
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(cost);
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + Float.floatToIntBits(price);
		result = prime * result
				+ ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((route == null) ? 0 : route.hashCode());
		result = prime * result
				+ (int) (shippingDuration ^ (shippingDuration >>> 32));
		result = prime * result
				+ ((submissionDate == null) ? 0 : submissionDate.hashCode());
		result = prime * result + Float.floatToIntBits(volume);
		result = prime * result + Float.floatToIntBits(weight);
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
		MailDelivery other = (MailDelivery) obj;
		if (Float.floatToIntBits(cost) != Float.floatToIntBits(other.cost))
			return false;
		if (id != other.id)
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		if (priority != other.priority)
			return false;
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		if (shippingDuration != other.shippingDuration)
			return false;
		if (submissionDate == null) {
			if (other.submissionDate != null)
				return false;
		} else if (!submissionDate.equals(other.submissionDate))
			return false;
		if (Float.floatToIntBits(volume) != Float.floatToIntBits(other.volume))
			return false;
		if (Float.floatToIntBits(weight) != Float.floatToIntBits(other.weight))
			return false;
		return true;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public long getShippingDuration() {
		return shippingDuration;
	}

	public void setShippingDuration(long shippingDuration) {
		this.shippingDuration = shippingDuration;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}
	
	@InjectOnContruct // Visable for testing
	public static final class Util {
		@Inject
		private State man;
		
		float calculatePrice(MailDelivery m) {
			return man.getPrice(
					m.getRoute().get(0).getStartPoint(), 
					m.getRoute().get(m.getRoute().size()-1).getEndPoint(), 
					m.getPriority())
							.getCost(m);
		}
		
		float calculateCost(MailDelivery m) {
			float sum = 0;
			
			for (Route r : m.getRoute())
				sum += r.getCost(m);
			
			return sum;
		}
		
		static Util getInstance() {
			if (instance == null)
				instance = new Util();
			
			return instance;
		}
		
		// Visable for testing
		public static void unload() {
			instance = null;
		}

		private static Util instance;
	}
}
