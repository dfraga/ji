package com.anxocode.ji;

import java.util.HashMap;
import java.util.Map;

public class Beans {

	private static final Beans INSTANCE = new Beans();

	public static Bean get(final Class<?> type) {
		Bean bean = Beans.INSTANCE.beans.get(type);

		if (bean == null) {
			bean = new Bean(type);
			Beans.INSTANCE.beans.put(type, bean);
		}

		return bean;
	}

	public static Property property(final Class<?> type, final String name) {
		return Beans.get(Ji.notNull(type, "type")).get(name);
	}

	public static Object property(final Object instance, final String name) {
		Ji.notNull(instance, "instance");

		return Beans.get(instance.getClass()).get(instance, Ji.notNull(name, "name"));
	}

	public static void property(final Object instance, final String name, final Object value) {
		Ji.notNull(instance, "instance");

		Beans.get(instance.getClass()).set(instance, Ji.notNull(name, "name"), value);
	}

	public static Iterable<Property> properties(final Class<?> type) {
		return Beans.get(type);
	}

	private final Map<Class<?>, Bean> beans;

	private Beans() {
		this.beans = new HashMap<Class<?>, Bean>();
	}
}
