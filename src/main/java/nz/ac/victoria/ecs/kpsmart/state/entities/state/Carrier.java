package nz.ac.victoria.ecs.kpsmart.state.entities.state;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public final class Carrier extends StorageEntity implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Bool disabled = Bool.False;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Carrier other = (Carrier) obj;
		if (disabled != other.disabled)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Carrier [id=" + id + ", name=" + name + ", disabled="
				+ (disabled == Bool.True ? "true" : "false") + "]";
	}

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
