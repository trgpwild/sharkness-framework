package org.sharkness.artifacts.jsf;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

public class PropertyModel {

	private String classModel;
	
	private String label;
	
	private String name;
	
	private Field field;
	
	public PropertyModel(String classModel, String label, String name, Field field) {
		setClassModel(classModel);
		setLabel(label);
		setName(name);
		setField(field);
	}
	
	public String getClassModel() {
		return classModel;
	}
	
	public void setClassModel(String classModel) {
		this.classModel = classModel;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Field getField() {
		return field;
	}
	
	public void setField(Field field) {
		this.field = field;
	}
	
	public String getTag() {
		if (Date.class.isAssignableFrom(field.getType())) {
			return new StringBuilder("<h:outputText value=\"#{").append(classModel.toLowerCase()).append(".").append(name).append("}\">")
				.append("<f:convertDateTime pattern=\"#{").append(classModel.toLowerCase()).append("Controller.locale}\"/>")
				.append("</h:outputText>").toString();
		} else if (Calendar.class.isAssignableFrom(field.getType())) {
			return new StringBuilder("<h:outputText value=\"#{").append(classModel.toLowerCase()).append(".").append(name).append(".time").append("}\">")
				.append("<f:convertDateTime pattern=\"#{").append(classModel.toLowerCase()).append("Controller.locale}\"/>")
				.append("</h:outputText>").toString();
		} else {
			return new StringBuilder("#{").append(classModel.toLowerCase()).append(".").append(name).append("}").toString();
		}
	}

}