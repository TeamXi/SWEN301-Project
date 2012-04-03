package nz.ac.victoria.ecs.kpsmart.integration;

import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.StateManipulator;

final class EntityUpdateEventOperation implements EventOperation<EntityUpdateEvent<? extends StorageEntity>> {
	@Override
	public void apply(EntityUpdateEvent<? extends StorageEntity> event, StateManipulator manipulator) {
		
	}
}
