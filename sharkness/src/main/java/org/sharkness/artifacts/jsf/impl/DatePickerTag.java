package org.sharkness.artifacts.jsf.impl;

import org.sharkness.artifacts.jsf.Tag;

public class DatePickerTag extends Tag {

	private boolean isCalendar;
	
	/**
	 * @param entityName
	 * @param property
	 */
	public DatePickerTag(String entityName, String property, boolean isCalendar) {
		super(entityName, property);
		this.isCalendar = isCalendar;
	}
	
	@Override
	public String getTagName() {
		return "p:calendar";
	}

	@Override
	protected String getValue() {
		if (isCalendar) {
			return new StringBuilder(super.getValue().replace("}", "")).append(".time}").toString();
		} else {
			return super.getValue();
		}
	}

	@Override
	protected String getAditionalMainTag() {
		return new StringBuilder("pattern=\"#{").append(getNameController()).append(".locale}\"").toString();
	}
	
	public static void main(String[] args) {
		
		Tag tag = new DatePickerTag("Cliente", "data", true);
		
		System.out.println(tag);
		
	}

}