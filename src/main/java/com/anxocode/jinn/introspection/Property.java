package com.anxocode.jinn.introspection;

import static com.anxocode.jinn.Jinn.check;
import static com.anxocode.jinn.Jinn.notNull;
import static com.anxocode.jinn.Jinn.buildFail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Property {

	private final String name;
	private final Method getter;
	private final Method setter;
	private final Class<?> type;
	
	Property(String name, Class<?> type, Method getter, Method setter) {
		this.name = name;
		this.getter = getter;
		this.setter = setter;
		this.type = type;
	}
	
	public boolean isReadable() {
		return this.getter != null;
	}
	
	public boolean isWritable() {
		return this.setter != null;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Class<?> getType() {
		return this.type;
	}
	
	public void set(Object instance, Object value) {
		notNull(instance, "instance");
		check(isWritable(), BeanErrorMessages.notWritable, this.name, instance.getClass());
		
		try {
			this.setter.invoke(instance, value);
		} catch (IllegalAccessException e) {
			throw buildFail(BeanErrorMessages.defaultError, this.name, instance.getClass(), e);
		} catch (InvocationTargetException e) {
			throw buildFail(BeanErrorMessages.defaultError, this.name, instance.getClass(),
					e.getCause());
		}
	}
	
	public Object get(Object instance) {
		notNull(instance, "instance");
		check(isReadable(), BeanErrorMessages.notReadable, this.name, instance.getClass());
		
		try {
			return this.getter.invoke(instance);
		} catch (IllegalAccessException e) {
			throw buildFail(BeanErrorMessages.defaultError, this.name, instance.getClass(), e);
		} catch (InvocationTargetException e) {
			throw buildFail(BeanErrorMessages.defaultError, this.name, instance.getClass(),
					e.getCause());
		}
	}
}
