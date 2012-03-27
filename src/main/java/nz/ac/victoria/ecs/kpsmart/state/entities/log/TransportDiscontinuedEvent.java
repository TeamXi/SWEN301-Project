package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;

@Entity
public final class TransportDiscontinuedEvent extends Event {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private Route route;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "TransportDiscontinuedEvent [id=" + id + ", route=" + route
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((route == null) ? 0 : route.hashCode());
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
		TransportDiscontinuedEvent other = (TransportDiscontinuedEvent) obj;
		if (id != other.id)
			return false;
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		return true;
	}
	
}
