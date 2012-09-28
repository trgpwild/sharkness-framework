package org.sharkness.artifacts.jsf.impl;

import org.sharkness.artifacts.jsf.Tag;

public class SelectManyMenuTag extends SelectManyCheckboxTag {

	public SelectManyMenuTag(String entityName, String property, String boundEntityName) {
		super(entityName, property, boundEntityName);
	}
	
	public SelectManyMenuTag(String entityName, String property, String boundEntityName, String converterId) {
		super(entityName, property, boundEntityName, converterId);
	}

	@Override
	public String getTagName() {
		return "p:selectManyMenu";
	}
	
	@Override
	protected String getAditionalMainTag() {
		return new StringBuilder(super.getAditionalMainTag()).append(" style=\"height:60px\"").toString();
	}
	
	public static void main(String[] args) {
		
		Tag tag = new SelectManyMenuTag("Contato", "cidade", "Cidade");
		
		System.out.println(tag);
		
	}

}