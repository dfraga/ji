package com.anxocode.ji;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Property {

	private final String name;
	private final Method getter;
	private final Method setter;
	private final Class<?> type;

	Property(final String name, final Class<?> type, final Method getter, final Method setter) {
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

	public void set(final Object instance, final Object value) {
		Ji.notNull(instance, "instance");
		Ji.check(isWritable(), ErrorMessages.notWritable, this.name, instance.getClass());

		try {
			this.setter.invoke(instance, value);
		} catch (IllegalAccessException e) {
			throw Ji.buildFail(ErrorMessages.defaultError, this.name, instance.getClass(), e);
		} catch (InvocationTargetException e) {
			throw Ji.buildFail(ErrorMessages.defaultError, this.name, instance.getClass(),
					e.getCause());
		}
	}

	public Object get(final Object instance) {
		Ji.notNull(instance, "instance");
		Ji.check(isReadable(), ErrorMessages.notReadable, this.name, instance.getClass());

		try {
			return this.getter.invoke(instance);
		} catch (IllegalAccessException e) {
			throw Ji.buildFail(ErrorMessages.defaultError, this.name, instance.getClass(), e);
		} catch (InvocationTargetException e) {
			throw Ji.buildFail(ErrorMessages.defaultError, this.name, instance.getClass(),
					e.getCause());
		}
	}
}
