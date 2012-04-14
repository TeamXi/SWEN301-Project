package nz.ac.victoria.ecs.kpsmart.state.entities.state;

import static nz.ac.victoria.ecs.kpsmart.state.entities.state.TransportMeans.Air;
import static nz.ac.victoria.ecs.kpsmart.state.entities.state.TransportMeans.Land;
import static nz.ac.victoria.ecs.kpsmart.state.entities.state.TransportMeans.Sea;

import java.util.HashSet;
import java.util.Set;

public enum Priority {
	International_Air(true, Air),
	International_Standard(true, Air, Land, Sea),
	
	Domestic_Air(false, Air),
	Domestic_Standard(false, Air, Land, Sea);
	
	// -----------------------------------------------
	
	public final TransportMeans[] ValidTransportMeans;
	
	public final boolean International;
	
	private Priority(boolean international, final TransportMeans... valid) {
		this.ValidTransportMeans = valid;
		this.International = international;
	}

	public static Set<Priority> internationalPriorities() {
		Set<Priority> priorities = new HashSet<Priority>();
		priorities.add(International_Air);
		priorities.add(International_Standard);
		return priorities;
	}
}
