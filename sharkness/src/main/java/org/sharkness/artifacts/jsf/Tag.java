package org.sharkness.artifacts.jsf;

import java.util.ArrayList;
import java.util.List;

public abstract class Tag {

	private String entityName;
	
	private String property;
	
	private List<SubTag> elements;
	
	public Tag(String entityName, String property) {
		setEntityName(entityName);
		setProperty(property);
	}
	
	public List<SubTag> createElements(List<SubTag> elements) {
		return elements;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public List<SubTag> getElements() {
		return elements;
	}

	public void setElements(List<SubTag> elements) {
		this.elements = elements;
	}

	protected String getValue() {
		return new StringBuilder("#{")
			.append(getNameController())
			.append(".model.").append(getProperty()).append("}")
		.toString();
	}
	
	protected String getNameController() {
		return new StringBuilder(getEntityName().substring(0, 1).toLowerCase())
			.append(getEntityName().substring(1)).append("Controller")
				.toString();
	}

	@Override
	public String toString() {
		if (getElements() == null) setElements(new ArrayList<SubTag>());
		getElements().addAll(createElements(new ArrayList<SubTag>()));
		StringBuilder str = new StringBuilder("<").append(getTagName());
		str.append(" id=\"").append(getProperty()).append("\"");
		str.append(" value=\"").append(getValue()).append("\"");
		str.append(" label=\"#{i18n['model.");
		str.append(getEntityName().substring(0, 1).toLowerCase());
		str.append(getEntityName().substring(1)).append(".property.");
		str.append(getProperty()).append("']}\"");
		if (getAditionalMainTag() != null) {
			str.append(" ").append(getAditionalMainTag());
		}
		if (getElements().size() > 0) {
			str.append(">").append("\n");
			for (SubTag element : getElements()) {
				str.append("\t\t\t\t\t\t").append(element).append("\n");
			}
			str.append("\t\t\t\t\t</").append(getTagName()).append(">");
		} else {
			str.append("/>");
		}
		return str.toString();
	}

	protected String getAditionalMainTag() {
		return null;
	}

	public abstract String getTagName();
	
}