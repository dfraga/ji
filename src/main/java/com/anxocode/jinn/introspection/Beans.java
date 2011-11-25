package com.anxocode.jinn.introspection;

import static com.anxocode.jinn.Jinn.notNull;
import static com.anxocode.jinn.Jinn.buildFail;
import static com.anxocode.jinn.Jinn.or;

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
		return INSTANCE.getBean(type);
	}

	private final Map<Class<?>, Bean> beans;
	
	private Beans() {
		this.beans = new HashMap<Class<?>, Bean>();
	}
	
	public Bean getBean(Class<?> type) {
		Bean bean = this.beans.get(type);
		
		if (bean == null) {
			bean = load(type);
			this.beans.put(type, bean);
		}
		
		return bean;
	}
	
	public Property property(Class<?> type, String name) {
		return getBean(notNull(type, "type")).get(name);
	}
	
	public Object property(Object instance, String name) {
		notNull(instance, "instance");
		
		return getBean(instance.getClass()).get(instance, notNull(name, "name"));
	}
	
	public void property(Object instance, String name, Object value) {
		notNull(instance, "instance");
		
		getBean(instance.getClass()).set(instance, notNull(name, "name"), value);
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
			throw buildFail(BeanErrorMessages.instrospection, or(e.getCause(), e));
		}
	}
}
