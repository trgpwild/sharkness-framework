package org.sharkness.artifacts.jsf;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.sharkness.artifacts.annotation.View;
import org.sharkness.business.entity.Model;
import org.springframework.core.annotation.AnnotationUtils;

public class ViewValueFactory {

	private static Boolean forceDataTable;
	private static Boolean dataTable;
	private static Boolean password;
	private static String propSortAndFilter;
	private static TagValues tag;
	private static Annotation view;

	public static ViewValue getViewValueByField(Field field) {
		
		forceDataTable = (Boolean) AnnotationUtils.getDefaultValue(View.class, "forceDataTable");

		dataTable = (Boolean) AnnotationUtils.getDefaultValue(View.class, "dataTable");
		
		password = (Boolean) AnnotationUtils.getDefaultValue(View.class, "password");
		
		propSortAndFilter = (String) AnnotationUtils.getDefaultValue(View.class, "propSortAndFilter");

		tag = (TagValues) AnnotationUtils.getDefaultValue(View.class, "tag");
		
		loadConfigs(field, true);
		
		String name = field.getName();
		
		if (tag.equals(TagValues.Default)) {

			if (field.getType().getSimpleName().equalsIgnoreCase("boolean")) {
				
				tag = TagValues.SelectBooleanCheckbox;
				
			} else if (Collection.class.isAssignableFrom(field.getType())) {
				
				tag = TagValues.SelectManyMenu;
				
			} else if (Model.class.isAssignableFrom(field.getType())) {
				
				tag = TagValues.SelectOnMenu;
				
			} else if (Calendar.class.isAssignableFrom(field.getType())) {
				
				tag = TagValues.Calendar;
				
			} else if (Date.class.isAssignableFrom(field.getType())) {
				
				tag = TagValues.Date;
				
			} else {
				
				tag = TagValues.InputText;
				
			}
			
		}

		loadConfigs(field, false);
		
		if (field.getType().getSimpleName().equalsIgnoreCase("boolean")) dataTable = false;
		
		if (forceDataTable) dataTable = true;
		
		if (Collection.class.isAssignableFrom(field.getType())) dataTable = false;
		else if (Model.class.isAssignableFrom(field.getType())) {
			if (propSortAndFilter != null && propSortAndFilter.length() > 0) {
				dataTable = true;
			} else dataTable = false;
		}
		
		if (password) tag = TagValues.PasswordText;
		
		return new ViewValue(name, dataTable, tag);
		
	}

	private static void loadConfigs(Field field, boolean loadTag) {
		
		if (field.isAnnotationPresent(View.class)) {
			
			view = field.getAnnotation(View.class);
			
			forceDataTable = (Boolean) AnnotationUtils.getValue(view, "forceDataTable");
			
			dataTable = (Boolean) AnnotationUtils.getValue(view, "dataTable");
			
			password = (Boolean) AnnotationUtils.getValue(view, "password");
			
			propSortAndFilter = (String) AnnotationUtils.getValue(view, "propSortAndFilter");

			if (loadTag) tag = (TagValues) AnnotationUtils.getValue(view, "tag");

		}
		
	}
	
	public static String getPropertyNameOfList(Field field, ViewValue viewValue) {
		
		String appendName = null;
		
		try {
			appendName = (String) AnnotationUtils.getValue(field.getAnnotation(View.class), "propSortAndFilter");
		} catch (Exception e) {
			appendName = null;
		}
		
		String propName = viewValue.getName();
		
		if (appendName != null) propName = new StringBuilder(propName).append(".").append(appendName).toString();

		return propName;
		
	}
	
}