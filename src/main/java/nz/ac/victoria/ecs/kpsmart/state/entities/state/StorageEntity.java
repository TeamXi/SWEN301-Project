package nz.ac.victoria.ecs.kpsmart.state.entities.state;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * An abstract class representing any entity.
 * 
 * @author hodderdani
 *
 */
@MappedSuperclass
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"uid"}))
public abstract class StorageEntity {

	@Enumerated(EnumType.STRING)
	protected Bool disabled = Bool.False;
	
//	@ManyToOne
//	@Cascade(CascadeType.ALL)
//	protected EntityID uid;

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

//	public EntityID getUid() {
//		return uid;
//	}
//
//	public void setUid(EntityID uid) {
//		this.uid = uid;
//	}	
}
