package org.sharkness.artifacts.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.sharkness.artifacts.jsf.TagValues;

@Target(FIELD)
@Retention(RUNTIME)
public @interface View {

	boolean dataTable() default true;

	boolean forceDataTable() default false;
	
	boolean password() default false;
	
	String propSortAndFilter() default "";
	
	String mask() default "";

	TagValues tag() default TagValues.Default;
	
}