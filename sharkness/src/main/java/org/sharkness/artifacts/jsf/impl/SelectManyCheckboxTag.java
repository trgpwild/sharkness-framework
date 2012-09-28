package org.sharkness.artifacts.jsf.impl;

import java.util.List;

import org.sharkness.artifacts.jsf.SubTag;
import org.sharkness.artifacts.jsf.Tag;

public class SelectManyCheckboxTag extends Tag {

	private String boundEntityName;
	
	private String converterId;

	public SelectManyCheckboxTag(String entityName, String property, String boundEntityName) {
		this(entityName, property, boundEntityName, null);
	}
	
	public SelectManyCheckboxTag(String entityName, String property, String boundEntityName, String converterId) {
		super(entityName, property);
		this.boundEntityName = boundEntityName;
		this.converterId = converterId;
	}

	@Override
	public String getTagName() {
		return "p:selectManyCheckbox";
	}
	
	@Override
	public List<SubTag> createElements(List<SubTag> elements) {
		elements.add(new SubTagSelectItems(boundEntityName));
		if (converterId != null) {
			elements.add(new SubTagConverter(converterId));
		} else {
			elements.add(new SubTagConverter());
		}
		return elements;
	}
	
	@Override
	protected String getAditionalMainTag() {
		return "collectionType=\"java.util.HashSet\"";
	}
	
	public static void main(String[] args) {
		
		Tag tag = new SelectManyCheckboxTag("Contato", "cidade", "Cidade");
		
		System.out.println(tag);
		
	}

}