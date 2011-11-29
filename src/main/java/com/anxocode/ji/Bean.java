package com.anxocode.ji;


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

	Bean(final Class<?> type) {
		this.type = type;
		final Map<String, Property> properties = new HashMap<String, Property>();

		try {
			final BeanInfo beanInfo = Introspector.getBeanInfo(type);

			for (final PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
				properties.put(pd.getName(), new Property(pd.getName(), pd.getPropertyType(),
						pd.getReadMethod(),	pd.getWriteMethod()));
			}
		} catch (IntrospectionException e) {
			throw Ji.buildFail(ErrorMessages.instrospection, Ji.or(e.getCause(), e));
		}

		this.properties = Collections.unmodifiableMap(properties);
	}

	public Property get(final String name) {
		Ji.notNull(name, "name");

		return this.properties.get(name);
	}

	public void set(final Object instance, final String name, final Object value) {
		final Property property = Ji.notNull(get(name), ErrorMessages.notFound, name, this.type);
		property.set(instance, value);
	}

	public Object get(final Object instance, final String name) {
		final Property property = Ji.notNull(get(name), ErrorMessages.notFound, name, this.type);

		return property.get(instance);
	}

	@Override
	public Iterator<Property> iterator() {
		return this.properties.values().iterator();
	}
}
