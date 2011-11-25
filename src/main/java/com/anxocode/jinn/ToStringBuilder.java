package com.anxocode.jinn;

public final class ToStringBuilder {

	private final StringBuilder sb = new StringBuilder(100);
	private boolean first = true;

	protected ToStringBuilder(Object object) {
		this.sb.append(Jinn.notNull(object, "object").getClass().getSimpleName());
		this.sb.append('{');
	}

	public ToStringBuilder add(String name, Object value) {
		if (this.first) {
			this.first = false;
		} else { 
			this.sb.append(", ");
		}
		
		this.sb.append(Jinn.notNull(name, "property name"));
		this.sb.append('=');
		this.sb.append(Jinn.toString(value));
		
		return this;
	}

	public ToStringBuilder add(Object value) {
		if (this.first) {
			this.first = false;
		} else { 
			this.sb.append(", ");
		}
		
		this.sb.append(Jinn.toString(value));
		
		return this;
	}
	
	@Override
	public String toString() {
		return this.sb.append('}').toString();
	}
}