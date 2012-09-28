package org.sharkness.helper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

@SuppressWarnings("unchecked")
public class ReflectionHelper {

	public static <T> Class<T> getClassBeanOfCollection(Field fieldCollection) {
        ParameterizedType collectionType = (ParameterizedType) fieldCollection.getGenericType();
        return (Class<T>) collectionType.getActualTypeArguments()[0];
	}
	
}