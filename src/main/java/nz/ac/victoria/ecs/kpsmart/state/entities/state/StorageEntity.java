package nz.ac.victoria.ecs.kpsmart.state.entities.state;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

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
}
