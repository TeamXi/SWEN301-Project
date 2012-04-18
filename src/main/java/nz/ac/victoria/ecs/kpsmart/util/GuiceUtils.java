package nz.ac.victoria.ecs.kpsmart.util;

import java.lang.annotation.Annotation;

import nz.ac.victoria.ecs.kpsmart.GuiceServletContextListner;

import com.google.inject.Key;
import com.google.inject.name.Names;

public final class GuiceUtils {
	public static <T> T getInstance(Class<T> clazz, Class<? extends Annotation> annotation) {
		Key<T> key = Key.get(clazz, annotation);
		return GuiceServletContextListner.getInjector().getInstance(key);
	}
	
	public static <T> T getInstance(Class<T> clazz, String name) {
		Key<T> key = Key.get(clazz, Names.named(name));
		return GuiceServletContextListner.getInjector().getInstance(key);
	}
}
