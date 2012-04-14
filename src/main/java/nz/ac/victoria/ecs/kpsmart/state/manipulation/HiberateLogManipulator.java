package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.util.List;

import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.Event;

@InjectOnContruct @Deprecated
public class HiberateLogManipulator implements LogManipulator {

	@Override
	public Event getEvent(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getAllEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Event event) {
		// TODO Auto-generated method stub
		
	}
}
