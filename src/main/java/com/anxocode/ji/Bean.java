package com.anxocode.ji;


import static com.anxocode.ji.Ji.buildFail;
import static com.anxocode.ji.Ji.notNull;
import static com.anxocode.ji.Ji.or;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Bean implements Iterable<Property> {

	private final Class<?> type;
	private final Map<String, Property> properties;
	
	Bean(Class<?> type) {
		this.type = type;
		final Map<String, Property> properties = new HashMap<String, Property>();
		
		try {
			final BeanInfo beanInfo = Introspector.getBeanInfo(type);
			
			for (final PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
				properties.put(pd.getName(), new Property(pd.getName(), pd.getPropertyType(), 
						pd.getReadMethod(),	pd.getWriteMethod()));
			}
		} catch (IntrospectionException e) {
			throw buildFail(ErrorMessages.instrospection, or(e.getCause(), e));
		}
		
		this.properties = Collections.unmodifiableMap(properties);
	}
	
	public Property get(String name) {
		Ji.notNull(name, "name");
		
		return this.properties.get(name);
	}
	
	public void set(Object instance, String name, Object value) {
		final Property property = notNull(get(name), ErrorMessages.notFound, name, this.type);
		property.set(instance, value);
	}
	
	public Object get(Object instance, String name) {
		final Property property = notNull(get(name), ErrorMessages.notFound, name, this.type);
		
		return property.get(instance);
	}

	@Override
	public Iterator<Property> iterator() {
		return this.properties.values().iterator();
	}
}
