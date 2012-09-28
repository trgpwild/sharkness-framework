package org.sharkness.artifacts.jsf.impl;

import org.sharkness.artifacts.jsf.Tag;

public class InputTextTag extends Tag {

	private String mask; 
	
	/**
	 * @param entityName
	 * @param property
	 */
	public InputTextTag(String entityName, String property, String mask) {
		super(entityName, property);
		this.mask = mask;
	}
	
	@Override
	public String getTagName() {
		if (mask != null && mask.length() > 0)
			return "p:inputMask";
		return "p:inputText";
	}

	@Override
	protected String getAditionalMainTag() {
		if (mask != null && mask.length() > 0) return new StringBuilder("mask=\"")
			.append(mask).append("\"").toString(); else return null;
	}
	
	public static void main(String[] args) {
		
		Tag tag = new InputTextTag("Cliente", "telefone", "(99) 9999-9999");
		
		System.out.println(tag);
		
	}

}