package com.anxocode.ji;

import static com.anxocode.ji.Ji.buildFail;
import static com.anxocode.ji.Ji.notNull;
import static com.anxocode.ji.Ji.or;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Beans {
	
	private static final Beans INSTANCE = new Beans();
	
	public static Bean get(Class<?> type) {
		Bean bean = INSTANCE.beans.get(type);
		
		if (bean == null) {
			bean = INSTANCE.load(type);
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
	
	private Bean load(Class<?> type) {
		try {
			final BeanInfo beanInfo = Introspector.getBeanInfo(type);
			final List<Property> properties = new ArrayList<Property>();
			
			for (final PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
				properties.add(new Property(pd.getName(), pd.getPropertyType(), pd.getReadMethod(), 
						pd.getWriteMethod()));
			}
			
			return new Bean(type, properties);
		} catch (IntrospectionException e) {
			throw buildFail(ErrorMessages.instrospection, or(e.getCause(), e));
		}
	}
}
