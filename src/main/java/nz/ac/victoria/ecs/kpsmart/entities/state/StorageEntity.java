package nz.ac.victoria.ecs.kpsmart.entities.state;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nz.ac.victoria.ecs.kpsmart.entities.logging.EventID;

import org.slf4j.LoggerFactory;

/**
 * An abstract class representing any entity.
 * 
 * @author hodderdani
 *
 */
@MappedSuperclass
public abstract class StorageEntity {
	@Enumerated(EnumType.STRING)
	protected Bool disabled = Bool.False;
	
	@ManyToOne
	protected EventID relateEventID;

	private boolean fromDatasource;

	public Bool getDisabled() {
		return disabled;
	}

	public void setDisabled(Bool disabled) {
		this.disabled = disabled;
	}

	public boolean isDisabled() {
		return disabled == Bool.True;
	}

	public void setDisabled(final boolean disabled) {
		this.disabled = disabled ? Bool.True : Bool.False;
	}

	public EventID getRelateEventID() {
		return relateEventID;
	}

	public void setRelateEventID(EventID relateEventID) {
		LoggerFactory.getLogger(getClass()).trace("Setting related ID to {}", relateEventID);
		
		this.relateEventID = relateEventID;
	}
	
	public StorageEntity setFromDatasource(boolean flag) {
		this.fromDatasource = true;
		
		return this;
	}
	
	public boolean isFromDatasource() {
		return this.fromDatasource;
	}
	
	/**
	 * Check if the given entity clashes with the unique constraint columns. Two entities may be non-equal but return
	 * true from this method
	 * 
	 * @param entity	The entity to check
	 * @return	True if adding this entity would violate the uniqueness constraints of the database.
	 */
	public abstract boolean isNonUnique(StorageEntity entity);
}
