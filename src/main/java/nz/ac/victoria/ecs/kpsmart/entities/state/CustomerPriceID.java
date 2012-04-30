package nz.ac.victoria.ecs.kpsmart.entities.state;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CustomerPriceID {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	  
	long setId(long id) {
		return this.id = id;
	}
	
	long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		CustomerPriceID other = (CustomerPriceID) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerPriceID [id=" + id + "]";
	}
}