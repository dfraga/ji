package com.anxocode.ji.introspection;

import static com.anxocode.ji.Ji.notNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.anxocode.ji.Ji;

public class Bean implements Iterable<Property> {

	private final Class<?> type;
	private final Map<String, Property> properties;
	
	Bean(Class<?> type, List<Property> properties) {
		this.type = type;
		final Map<String, Property> propertiesMap = new HashMap<String, Property>();
		
		for (final Property property : properties) {
			propertiesMap.put(property.getName(), property);
		}
		
		this.properties = Collections.unmodifiableMap(propertiesMap);
	}
	
	public Property get(String name) {
		Ji.notNull(name, "name");
		
		return this.properties.get(name);
	}
	
	public void set(Object instance, String name, Object value) {
		final Property property = notNull(get(name), BeanErrorMessages.notFound, name, this.type);
		property.set(instance, value);
	}
	
	public Object get(Object instance, String name) {
		final Property property = notNull(get(name), BeanErrorMessages.notFound, name, this.type);
		
		return property.get(instance);
	}

	@Override
	public Iterator<Property> iterator() {
		return this.properties.values().iterator();
	}
}
