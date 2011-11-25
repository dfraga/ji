package com.anxocode.ji;

enum ErrorMessages {

	notNull("{} can't be null"),
	notEmpty("{} can't be empty"),
	notBlank("{} can't be empty or blank"),
	defaultError("Error in property {} in {}: {}"),
	notFound("Not found property {} in {}"),
	notReadable("Property {} in {} is not readable"),
	notWritable("Property {} in {} is not writeble"),
	instrospection("Error in {}: {}");

	private final String pattern;
	
	ErrorMessages(String pattern) {
		this.pattern = pattern;
	}
	
	@Override
	public String toString() {
		return this.pattern;
	}
}
