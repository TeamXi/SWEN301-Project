package nz.ac.victoria.ecs.kpsmart.state.entities.state;

import javax.persistence.Entity;

@Entity
public enum Priority {
	International_Air,
	International_Standard,
	
	Domestic_Air,
	Domestic_Standard;
}
