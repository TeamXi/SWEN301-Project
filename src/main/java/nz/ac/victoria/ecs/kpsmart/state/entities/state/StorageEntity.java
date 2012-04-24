package nz.ac.victoria.ecs.kpsmart.state.entities.state;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.slf4j.LoggerFactory;

import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityOperationEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EventID;

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
}
