package nz.ac.victoria.ecs.kpsmart.state.entities.state;

import static nz.ac.victoria.ecs.kpsmart.state.entities.state.TransportMeans.Air;
import static nz.ac.victoria.ecs.kpsmart.state.entities.state.TransportMeans.Land;
import static nz.ac.victoria.ecs.kpsmart.state.entities.state.TransportMeans.Sea;

public enum Priority {
	International_Air(Air),
	International_Standard(Air, Land, Sea),
	
	Domestic_Air(Air),
	Domestic_Standard(Air, Land, Sea);
	
	// -----------------------------------------------
	
	public final TransportMeans[] ValidTransportMeans;
	
	private Priority(final TransportMeans... valid) {
		this.ValidTransportMeans = valid;
	}
}
