package org.sharkness.artifacts.jsf.impl;

import org.sharkness.artifacts.jsf.SubTag;

public class SubTagSelectItem extends SubTag {

	private String itemValue;
	
	private String itemLabel;
	
	public SubTagSelectItem(String itemLabel) {
		this(itemLabel, "");
	}
	
	public SubTagSelectItem(String itemLabel, String itemValue) {
		this.itemLabel=itemLabel;
		this.itemValue=itemValue;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("<").append("f:selectItem");
		str.append(" itemLabel=\"").append(itemLabel).append("\"");
		str.append(" itemValue=\"").append(itemValue).append("\"");
		str.append("/>");
		return str.toString();
	}
	
}