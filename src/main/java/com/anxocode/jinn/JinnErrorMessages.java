package com.anxocode.jinn;

enum JinnErrorMessages {

	notNull("{} can't be null"),
	notEmpty("{} can't be empty"),
	notBlank("{} can't be empty or blank");

	private final String pattern;
	
	JinnErrorMessages(String pattern) {
		this.pattern = pattern;
	}
	
	@Override
	public String toString() {
		return this.pattern;
	}
}
