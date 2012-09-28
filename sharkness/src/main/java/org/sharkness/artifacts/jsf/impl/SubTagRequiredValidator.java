package org.sharkness.artifacts.jsf.impl;

import org.sharkness.artifacts.jsf.SubTag;

public class SubTagRequiredValidator extends SubTag {
	
	@Override
	public String toString() {
		return new StringBuilder("<").append("f:validateRequired").append("/>").toString();
	}
	
}