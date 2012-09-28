package org.sharkness.business.factory;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.sharkness.business.entity.Model;
import org.sharkness.business.entity.User;
import org.sharkness.helper.StringHelper;
import org.sharkness.web.component.Component;
import org.sharkness.web.component.ComponentNotFoundException;
import org.sharkness.web.component.ControllerComponent;
import org.sharkness.web.component.ConverterComponent;

@SuppressWarnings({"unchecked","rawtypes"})
public class ModelFactory {

	private static String userClassName;
	
	public static String getUserClassName() {
		return userClassName;
	}
	
	public static <T> Class<T> getClass(Component componentCaller) throws ClassNotFoundException, ComponentNotFoundException {
    	if (componentCaller instanceof ControllerComponent) {
			return getClassModel(componentCaller);
		} else if (componentCaller instanceof ConverterComponent) {
			return getClassModelService(componentCaller);
		} else {
			throw new ComponentNotFoundException("O componente nao foi encontrado pela fabrica.");
		}
    }

	public static <T> Class<T> getClassModel(Component component) {
    	try {
			return (Class<T>) Class.forName(getModelCannonicalName(component));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
	public static <T> Class<T> getClassModelService(Component component) {
    	try {
			return (Class<T>) Class.forName(getModelServiceCannonicalName(component));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	
	public static <T> Class<T> getClassDao(Component component) {
    	try {
			return (Class<T>) Class.forName(getDaoCannonicalName(component));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

	private static String getSimpleModelName(String simpleNameComponent) {
		StringBuilder str = new StringBuilder();
		List<String> names = StringHelper.splitCamelCaseString(simpleNameComponent);
		int compensation = -1;
		if (names.get(names.size()-2).equalsIgnoreCase("Service")) compensation = -2;
		if (names.get(names.size()-1).equalsIgnoreCase("Service")) compensation = -1;
		for (int i = 0; i < names.size() + compensation; i++) str.append(names.get(i));
		return str.toString();
	}
	
	public static List<String> getSharknessHibernateEntities() {
		try {
			List<String> sharknessEntities = new ArrayList<String>();
			for (String cls : ScannerClass.getClassNamesFromPackage(PropertiesFactory.getModelPackage())) {
				Class<?> klass = Class.forName(cls);
				if (klass.isAnnotationPresent(Entity.class)) {
					sharknessEntities.add(cls);
				}
				if (User.class.isAssignableFrom(klass)) {
					ModelFactory.userClassName = klass.getSimpleName();
				}
			}
			return sharknessEntities;
		} catch (Exception e) {
			return new ArrayList<String>();
		}
	}

	public static List<Class<Model>> getSharknessClassEntities() {
		try {
			List<Class<Model>> sharknessEntities = new ArrayList<Class<Model>>();
			for (String cls : ScannerClass.getClassNamesFromPackage(PropertiesFactory.getModelPackage())) {
				Class<Model> classModel = (Class<Model>) Class.forName(cls);
				sharknessEntities.add(classModel);
			}
			return sharknessEntities;
		} catch (Exception e) {
			return new ArrayList<Class<Model>>();
		}
	}

	public static String getModelCannonicalName(Component component) throws Exception {
		String modelServiceName = component.getClass().getSimpleName();
		modelServiceName = getSimpleModelName(modelServiceName);
    	return new StringBuilder(PropertiesFactory.getModelPackage())
    		.append(".").append(modelServiceName).toString();
    }
    
	public static String getModelServiceCannonicalName(Component component) throws Exception {
		String modelServiceName = component.getClass().getSimpleName();
		modelServiceName = getSimpleModelName(modelServiceName);
    	return new StringBuilder(PropertiesFactory.getServicePackage())
    		.append(".").append(modelServiceName).append("Service").toString();
    }
	
	public static String getDaoCannonicalName(Component component) throws Exception {
		String modelDaoName = component.getClass().getSimpleName();
		modelDaoName = getSimpleModelName(modelDaoName);
    	return new StringBuilder(PropertiesFactory.getDaoPackage())
    		.append(".").append(modelDaoName).append("Dao").toString();
    }

}