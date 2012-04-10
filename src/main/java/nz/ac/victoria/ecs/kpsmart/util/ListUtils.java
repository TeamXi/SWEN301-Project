package nz.ac.victoria.ecs.kpsmart.util;

import java.util.LinkedList;
import java.util.List;

public final class ListUtils {
	public static final <T> List<T> filter(List<T> input, Filter<T> filter) {
		List<T> output = new LinkedList<T>();
		
		for (T o : input)
			if (filter.filter(o))
				output.add(o);
		
		return output;
	}
}
