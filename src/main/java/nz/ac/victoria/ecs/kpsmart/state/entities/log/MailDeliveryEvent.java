package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;

@Entity
public final class MailDeliveryEvent extends Event {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToMany
	private List<Route> route;
	
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	private float weight;
	
	private float volume;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "MailDeliveryEvent [id=" + id + ", route=" + route
				+ ", priority=" + priority + ", weight=" + weight + ", volume="
				+ volume + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((route == null) ? 0 : route.hashCode());
		result = prime * result + Float.floatToIntBits(volume);
		result = prime * result + Float.floatToIntBits(weight);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MailDeliveryEvent other = (MailDeliveryEvent) obj;
		if (id != other.id)
			return false;
		if (priority != other.priority)
			return false;
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		if (Float.floatToIntBits(volume) != Float.floatToIntBits(other.volume))
			return false;
		if (Float.floatToIntBits(weight) != Float.floatToIntBits(other.weight))
			return false;
		return true;
	}

	
}
