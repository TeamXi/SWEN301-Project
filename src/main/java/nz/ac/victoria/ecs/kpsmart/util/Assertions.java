package nz.ac.victoria.ecs.kpsmart.util;

public final class Assertions {
	public static void assertContains(String needle, String haystack) {
		if (!haystack.contains(needle))
			throw new AssertionError("The string '"+haystack+"' did not contain the needle '"+needle+"'");
	}
}
