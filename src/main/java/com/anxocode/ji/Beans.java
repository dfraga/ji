package com.anxocode.ji;

import static com.anxocode.ji.Ji.notNull;

import java.util.HashMap;
import java.util.Map;

public class Beans {
	
	private static final Beans INSTANCE = new Beans();
	
	public static Bean get(Class<?> type) {
		Bean bean = INSTANCE.beans.get(type);
		
		if (bean == null) {
			bean = new Bean(type);
			INSTANCE.beans.put(type, bean);
		}
		
		return bean;
	}

	public static Property property(Class<?> type, String name) {
		return get(notNull(type, "type")).get(name);
	}
	
	public static Object property(Object instance, String name) {
		notNull(instance, "instance");
		
		return get(instance.getClass()).get(instance, notNull(name, "name"));
	}
	
	public static void property(Object instance, String name, Object value) {
		notNull(instance, "instance");
		
		get(instance.getClass()).set(instance, notNull(name, "name"), value);
	}
	
	public static Iterable<Property> properties(Class<?> type) {
		return get(type);
	}
	
	private final Map<Class<?>, Bean> beans;
	
	private Beans() {
		this.beans = new HashMap<Class<?>, Bean>();
	}
}
