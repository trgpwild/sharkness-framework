package org.sharkness.artifacts.jsf.impl;

import org.sharkness.artifacts.jsf.SubTag;

public class SubTagSelectItems extends SubTag {

	private String entityName;
	
	public SubTagSelectItems(String entityName) {
		this.entityName = entityName;
	}
	
	private String getValue() {
		return new StringBuilder("#{")
			.append(entityName.substring(0, 1).toLowerCase())
			.append(entityName.substring(1)).append("Controller")
			.append(".listModel}")
		.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("<").append("f:selectItems");
		str.append(" value=\"").append(getValue()).append("\"");
		str.append("/>");
		return str.toString();
	}
	
}