package nz.ac.victoria.ecs.kpsmart.integration;

import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.StateManipulator;

class EntityDeleteEventOperation implements EventOperation<EntityDeleteEvent<? extends StorageEntity>> {

	@Override
	public void apply(EntityDeleteEvent<? extends StorageEntity> event, StateManipulator manipulator) {
		manipulator.delete(event.getEntity());
	}

}
