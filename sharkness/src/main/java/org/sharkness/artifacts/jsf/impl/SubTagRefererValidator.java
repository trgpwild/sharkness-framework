package org.sharkness.artifacts.jsf.impl;

import org.sharkness.artifacts.jsf.SubTag;

public class SubTagRefererValidator extends SubTag {
	
	private String validatorId;
	
	public SubTagRefererValidator(String validatorId) {
		this.validatorId = validatorId;
	}

	@Override
	public boolean isValid() {
		return (validatorId != null && validatorId.length() > 0);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		if (isValid()) {
			str.append("<").append("f:validator");
			str.append(" validatorId=\"").append(validatorId).append("\"");
			str.append("/>");
		}
		return str.toString();
	}
	
}