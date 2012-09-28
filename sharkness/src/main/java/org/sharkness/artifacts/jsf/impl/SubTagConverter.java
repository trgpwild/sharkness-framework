package org.sharkness.artifacts.jsf.impl;

import org.sharkness.artifacts.jsf.SubTag;

public class SubTagConverter extends SubTag {
	
	private String converterId;
	
	public SubTagConverter() {
		this("simpleModelConverter");
	}
	
	public SubTagConverter(String converterId) {
		this.converterId = converterId;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("<").append("f:converter");
		str.append(" converterId=\"").append(converterId).append("\"");
		str.append("/>");
		return str.toString();
	}
	
}