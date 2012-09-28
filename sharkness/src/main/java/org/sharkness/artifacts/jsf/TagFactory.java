package org.sharkness.artifacts.jsf;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.sharkness.artifacts.annotation.Validation;
import org.sharkness.artifacts.annotation.View;
import org.sharkness.artifacts.jsf.impl.DatePickerTag;
import org.sharkness.artifacts.jsf.impl.InputTextTag;
import org.sharkness.artifacts.jsf.impl.PasswordTextTag;
import org.sharkness.artifacts.jsf.impl.SelectBooleanCheckboxTag;
import org.sharkness.artifacts.jsf.impl.SelectManyCheckboxTag;
import org.sharkness.artifacts.jsf.impl.SelectManyMenuTag;
import org.sharkness.artifacts.jsf.impl.SelectOneMenuTag;
import org.sharkness.artifacts.jsf.impl.SubTagDoubleRangeValidator;
import org.sharkness.artifacts.jsf.impl.SubTagLengthValidator;
import org.sharkness.artifacts.jsf.impl.SubTagLongRangeValidator;
import org.sharkness.artifacts.jsf.impl.SubTagRefererValidator;
import org.sharkness.artifacts.jsf.impl.SubTagRegexValidator;
import org.sharkness.artifacts.jsf.impl.SubTagRequiredValidator;
import org.sharkness.business.entity.Model;
import org.sharkness.helper.ReflectionHelper;
import org.springframework.core.annotation.AnnotationUtils;

public class TagFactory {

	private String entityName;
	
	public TagFactory(String entityName) {
		this.entityName = entityName;
	}
	
	public Tag getTagByField(TagValues tv, Field field) {

		String simpleName = null;
		
		String boundEntityName = null;
		
		if (Model.class.isAssignableFrom(field.getType())) {
			
			simpleName = field.getType().getSimpleName();
			
		} else if (Collection.class.isAssignableFrom(field.getType())) {

	        simpleName = ReflectionHelper.getClassBeanOfCollection(field).getSimpleName();
			
		}
		
		if (simpleName != null) {
			
			boundEntityName = new StringBuilder(simpleName.substring(0, 1).toLowerCase())
				.append(simpleName.substring(1)).toString();
			
		}
		
		Tag tag = null;
	
		if (tv.equals(TagValues.SelectBooleanCheckbox)) {
			
			tag = new SelectBooleanCheckboxTag(entityName, field.getName());
			
		} else if (tv.equals(TagValues.SelectManyCheckbox)) {
			
			tag = new SelectManyCheckboxTag(entityName, field.getName(), boundEntityName);
			
		} else if (tv.equals(TagValues.SelectManyMenu)) {
			
			tag = new SelectManyMenuTag(entityName, field.getName(), boundEntityName);

		} else if (tv.equals(TagValues.SelectOnMenu)) {
			
			tag = new SelectOneMenuTag(entityName, field.getName(), boundEntityName);
			
		} else if (tv.equals(TagValues.Date)) {
			
			tag = new DatePickerTag(entityName, field.getName(), false);
			
		} else if (tv.equals(TagValues.Calendar)) {
			
			tag = new DatePickerTag(entityName, field.getName(), true);
			
		} else if (tv.equals(TagValues.InputText)) {
			
			String mask = null;
			
			if (field.isAnnotationPresent(View.class)) {
				
				Annotation view = field.getAnnotation(View.class);
				
				mask = (String) AnnotationUtils.getValue(view, "mask");
				
			}
			
			tag = new InputTextTag(entityName, field.getName(), mask);

		} else if (tv.equals(TagValues.PasswordText)) {
			
			tag = new PasswordTextTag(entityName, field.getName());

		}
		
		List<SubTag> listSubTagValidation = new ArrayList<SubTag>();
		
		if (field.isAnnotationPresent(Validation.class)) {
			
			Annotation validation = field.getAnnotation(Validation.class);
			
			double min = (Double) AnnotationUtils.getValue(validation, "min");
			double max = (Double) AnnotationUtils.getValue(validation, "max");
			boolean required = (Boolean) AnnotationUtils.getValue(validation, "required");
			String regex = (String) AnnotationUtils.getValue(validation, "regex");
			String validatorId = (String) AnnotationUtils.getValue(validation, "validatorId");
			
			SubTag regexValidator = new SubTagRegexValidator(regex);
			
			SubTag refererValidator = new SubTagRefererValidator(validatorId);

			SubTag limitValidator = null;

			if (Double.class.isAssignableFrom(field.getType()) || double.class.isAssignableFrom(field.getType())) {
				
				limitValidator = new SubTagDoubleRangeValidator(min, max);

			} else if (Float.class.isAssignableFrom(field.getType()) || float.class.isAssignableFrom(field.getType())) {
				
				limitValidator = new SubTagDoubleRangeValidator((double) min, (double) max);

			} else if (Long.class.isAssignableFrom(field.getType()) || long.class.isAssignableFrom(field.getType())) {
				
				limitValidator = new SubTagLongRangeValidator((long) min, (long) max);

			} else if (Integer.class.isAssignableFrom(field.getType()) || int.class.isAssignableFrom(field.getType())) {
				
				limitValidator = new SubTagLongRangeValidator((int) min, (int) max);

			} else if (String.class.isAssignableFrom(field.getType())) {
				
				limitValidator = new SubTagLengthValidator((int) min, (int) max);
				
			}
			
			if (limitValidator != null && limitValidator.isValid()) listSubTagValidation.add(limitValidator);

			if (regexValidator.isValid()) listSubTagValidation.add(regexValidator);
			
			if (refererValidator.isValid()) listSubTagValidation.add(refererValidator);

			if (required) listSubTagValidation.add(new SubTagRequiredValidator());

		}
		
		if (listSubTagValidation.size() > 0) {
			if (tag.getElements() == null) tag.setElements(new ArrayList<SubTag>());
			for (SubTag subTagValidation : listSubTagValidation) {
				tag.getElements().add(subTagValidation);
			}
		}
		
		return tag;
		
	}
	
}