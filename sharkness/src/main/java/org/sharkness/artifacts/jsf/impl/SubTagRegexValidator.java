package org.sharkness.artifacts.jsf.impl;

import org.sharkness.artifacts.jsf.SubTag;

public class SubTagRegexValidator extends SubTag {
	
	private String regex;
	
	public SubTagRegexValidator(String regex) {
		this.regex = regex;
	}

	@Override
	public boolean isValid() {
		return (regex != null && regex.length() > 0);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		if (isValid()) {
			str.append("<").append("f:validateRegex");
			str.append(" pattern=\"").append(regex).append("\"");
			str.append("/>");
		}
		return str.toString();
	}
	
}