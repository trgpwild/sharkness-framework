package org.sharkness.artifacts.jsf.impl;

import java.util.List;

import org.sharkness.artifacts.jsf.SubTag;
import org.sharkness.artifacts.jsf.Tag;

public class SelectOneMenuTag extends Tag {

	private String boundEntityName;
	
	private String converterId;

	public SelectOneMenuTag(String entityName, String property, String boundEntityName) {
		this(entityName, property, boundEntityName, null);
	}
	
	public SelectOneMenuTag(String entityName, String property, String boundEntityName, String converterId) {
		super(entityName, property);
		this.boundEntityName = boundEntityName;
		this.converterId = converterId;
	}

	@Override
	public String getTagName() {
		return "p:selectOneMenu";
	}
	
	@Override
	public List<SubTag> createElements(List<SubTag> elements) {
		elements.add(new SubTagSelectItem("Selecione"));
		elements.add(new SubTagSelectItems(boundEntityName));
		if (converterId != null) {
			elements.add(new SubTagConverter(converterId));
		} else {
			elements.add(new SubTagConverter());
		}
		return elements;
	}
	
	public static void main(String[] args) {
		
		Tag tag = new SelectOneMenuTag("Contato", "cidade", "CidadeNatal");
		
		System.out.println(tag);
		
	}

}