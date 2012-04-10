/**
 * 
 */
package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import org.hibernate.Session;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * @author hodderdani
 *
 */
public class InMemoryStateManipulator extends HibernateStateManipulator {
	@Inject
	@Named("memory")
	private Session session;
	
	@Override
	protected Session getSession() {
		return session;
	}
}
