package org.sharkness.artifacts.jsf.impl;

import org.sharkness.artifacts.jsf.SubTag;

public class SubTagLongRangeValidator extends SubTag {
	
	private long min = -1;
	private long max = -1;

	public SubTagLongRangeValidator(long min, long max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public boolean isValid() {
		return (min > -1 || max > -1);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		if (isValid()) {
			str.append("<").append("f:validateLongRange");
			if (min > -1) str.append(" minimum=\"").append(min).append("\"");
			if (max > -1) str.append(" maximum=\"").append(max).append("\"");
			str.append("/>");
		}
		return str.toString();
	}
	
}