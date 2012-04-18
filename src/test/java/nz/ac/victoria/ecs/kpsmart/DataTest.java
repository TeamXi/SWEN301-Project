package nz.ac.victoria.ecs.kpsmart;

import static nz.ac.victoria.ecs.kpsmart.util.GuiceUtils.getInstance;
import nz.ac.victoria.ecs.kpsmart.Data;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.BindingStateManipulatorModule;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.HibernateImpl;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.InMemoryStateManipulationModule;
import nz.ac.victoria.ecs.kpsmart.state.manipulation.StateManipulator;

import org.junit.Before;
import org.junit.BeforeClass;

import com.google.inject.name.Names;

public abstract class DataTest {
	@BeforeClass public static void init() {  }
	
	@Before
	public void setUp() {
		GuiceServletContextListner.initNoData();
		GuiceServletContextListner.overloadModules(new BindingStateManipulatorModule((HibernateImpl) getInstance(StateManipulator.class, "memory")));
		new Data().createData();
	}
}
