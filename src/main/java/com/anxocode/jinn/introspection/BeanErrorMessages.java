package com.anxocode.jinn.introspection;

enum BeanErrorMessages {
	defaultError("Error in property {} in {}: {}"),
	notFound("Not found property {} in {}"),
	notReadable("Property {} in {} is not readable"),
	notWritable("Property {} in {} is not writeble"),
	instrospection("Error in {}: {}");

	private final String pattern;
	
	BeanErrorMessages(String pattern) {
		this.pattern = pattern;
	}
	
	@Override
	public String toString() {
		return this.pattern;
	}
}
