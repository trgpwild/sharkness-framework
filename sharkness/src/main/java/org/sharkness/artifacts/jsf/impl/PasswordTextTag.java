package org.sharkness.artifacts.jsf.impl;

import org.sharkness.artifacts.jsf.Tag;

public class PasswordTextTag extends InputTextTag {

	/**
	 * @param entityName
	 * @param property
	 */
	public PasswordTextTag(String entityName, String property) {
		super(entityName, property, null);
	}
	
	@Override
	public String getTagName() {
		return "p:password";
	}
	
	@Override
	protected String getAditionalMainTag() {
		if (super.getAditionalMainTag() != null) {
			return new StringBuilder(super.getAditionalMainTag()).append(" ").append("inline=\"true\"").toString();
		} else {
			return "inline=\"true\"";
		}
	}
	
	public static void main(String[] args) {
		
		Tag tag = new PasswordTextTag("Contato", "nome");
		
		System.out.println(tag);
		
	}

}