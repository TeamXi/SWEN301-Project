package nz.ac.victoria.ecs.kpsmart.state.entities.state;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RouteID {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	  
	long setId(long id) {
		return this.id = id;
	}
	
	long getId() {
		return id;
	}
}