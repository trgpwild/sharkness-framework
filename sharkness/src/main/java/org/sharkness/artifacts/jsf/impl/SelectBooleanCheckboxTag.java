package org.sharkness.artifacts.jsf.impl;

import org.sharkness.artifacts.jsf.Tag;

public class SelectBooleanCheckboxTag extends Tag {

	/**
	 * @param entityName
	 * @param property
	 */
	public SelectBooleanCheckboxTag(String entityName, String property) {
		super(entityName, property);
	}
	
	@Override
	public String getTagName() {
		return "p:selectBooleanCheckbox";
	}
	
	public static void main(String[] args) {
		
		Tag tag = new SelectBooleanCheckboxTag("Contato", "ativo");
		
		System.out.println(tag);
		
	}

}