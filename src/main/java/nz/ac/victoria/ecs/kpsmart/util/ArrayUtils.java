package nz.ac.victoria.ecs.kpsmart.util;

public class ArrayUtils {
	public static <T> boolean contains(T[] array, T element) {
		for (T elem : array)
			if (elem.equals(element))
				return true;
		return false;
	}
}
