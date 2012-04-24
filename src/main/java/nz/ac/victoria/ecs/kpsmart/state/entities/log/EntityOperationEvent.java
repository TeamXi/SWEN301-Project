package nz.ac.victoria.ecs.kpsmart.state.entities.log;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;

@MappedSuperclass
public abstract class EntityOperationEvent<E extends StorageEntity> implements Serializable {
	@OneToOne
	protected E entity;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@Id
	@ManyToOne
	@Cascade(CascadeType.ALL)
	private EventID uid = new EventID();
	
	protected EntityOperationEvent(E entity) {
		this.entity = entity;
		
		if (entity != null)
			this.entity.setRelateEventID(uid);
	}

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		EntityOperationEvent other = (EntityOperationEvent) obj;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public long getId() {
		return getUid().getId();
	}

	public void setId(long id) {
		this.getUid().setId(id);
	}

	public EventID getUid() {
		return uid;
	}

	public void setUid(EventID uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "EntityOperationEvent [entity=" + entity + ", timestamp="
				+ timestamp + ", uid=" + uid + "]";
	}

}
