package nz.ac.victoria.ecs.kpsmart.util;

/**
 * perform a filter on a list
 * 
 * @author hodderdani
 *
 * @param <T>
 */
public interface Filter<T> {
	public boolean filter(T object);
}
