package nz.ac.victoria.ecs.kpsmart.util;

import java.util.Collections;
import java.util.Comparator;
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
	
	public static final <T extends Comparable<? super T>> List<T> sort(List<T> list) {
		Collections.sort(list);
		return list;
	}
	
	public static final <T>	List<T> sort(List<T> list, Comparator<? super T> comp) {
		Collections.sort(list, comp);
		return list;
	}
}
