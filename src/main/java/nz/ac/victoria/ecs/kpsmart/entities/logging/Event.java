package nz.ac.victoria.ecs.kpsmart.entities.logging;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;


/**
 * This represents a change event from the user.
 * 
 * @author hodderdani
 *
 */
//@Entity
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@Deprecated
public abstract class Event implements Serializable {
	public Event() {}
	
	protected Event(final Date timestamp) {
//		this.setTimestamp(timestamp);
	}

	private static final long serialVersionUID = 1L;

//	@Override
//	public String toString() {
//		return "Event [timestamp=" + timestamp + ", Id=" + getUid() + "]";
//	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
//		result = prime * result
//				+ ((timestamp == null) ? 0 : timestamp.hashCode());
//		return result;
//	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Event other = (Event) obj;
//		if (getUid() == null) {
//			if (other.getUid() != null)
//				return false;
//		} else if (!getUid().equals(other.getUid()))
//			return false;
//		if (timestamp == null) {
//			if (other.timestamp != null)
//				return false;
//		} else if (!timestamp.equals(other.timestamp))
//			return false;
//		return true;
//	}
	
//	public EventID getUnderlyingID() {
//		return id;
//	}

}
