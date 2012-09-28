package org.sharkness.artifacts.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)
public @interface Validation {

	double min() default -1;

	double max() default -1;
	
	boolean required() default false;

	String regex() default "";
	
	String validatorId() default "";

}