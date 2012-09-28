package org.sharkness.artifacts.generate;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import org.sharkness.artifacts.utility.FreemarkerUtils;
import org.sharkness.business.factory.PropertiesFactory;

public abstract class BusinessGenerator {
	
	protected static void createBusinessComponent(String model, Template template) {
		
		try {
			
			boolean userController = false;
			
			String basePackage = PropertiesFactory.getApplicationPackage();
			
			model = model.replaceAll(new StringBuilder(basePackage).append(".").toString(), "").trim();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("basePackage", basePackage);
			map.put("model", model);
			
			Class<?> classModel = Class.forName(
				new StringBuilder(PropertiesFactory.getModelPackage())
					.append(".").append(model).toString()
			);

			for (Method m : classModel.getMethods()) {
				if (m.getName().equalsIgnoreCase("getId")
				&& !m.getGenericReturnType().equals(java.lang.Object.class)) {
					map.put("completePathOfClassId", m.getReturnType().getCanonicalName());
					map.put("classId", m.getReturnType().getSimpleName());
				}
			}
			
			boolean isEntity = classModel.isAnnotationPresent(Entity.class);
			
			String sourcePath = new StringBuilder("./").append(SharknessGenerator.getSrc()).append("/").toString();
			
			model = new StringBuilder(
				model.substring(0, 1).toUpperCase())
				.append(model.substring(1)
			).toString();
			
			String componentPackage = null;
			
			if (template.equals(Template.Dao)) {
				
				if (isEntity) componentPackage = PropertiesFactory.getDaoPackage();
				
			} else if (template.equals(Template.Converter)) {
				
				componentPackage = PropertiesFactory.getConverterPackage();
				
			} else if (template.equals(Template.Controller)) {
				
				componentPackage = PropertiesFactory.getControllerPackage();
				
			} else if (template.equals(Template.UserController)) {
				
				userController = true;
				
				template = Template.Controller;
				
				componentPackage = PropertiesFactory.getControllerPackage();

			} else if (template.equals(Template.Service)) {
				
				componentPackage = PropertiesFactory.getServicePackage();
				
			} else if (template.equals(Template.ServiceImpl)) {
				
				if (isEntity) componentPackage = PropertiesFactory.getServiceImplPackage();
				
			} else {
				
				throw new Exception(new StringBuilder("[")
					.append(template).append("]: ").append("Template not found.")
						.toString());
				
			}
			
			if (componentPackage != null) {
				
				String javaPath = componentPackage.replaceAll("/", "").replaceAll("\\.", "/").trim();
				
				String javaFile = new StringBuilder(model)
					.append(template.toString()).append(".java").toString();
				
				File pacote = new File(
					new StringBuilder(sourcePath)
					.append(javaPath).toString()
				);
				
				File classe = new File(
					new StringBuilder(sourcePath)
						.append(javaPath)
						.append("/").append(javaFile)
						.toString()
				);
				
				if (userController) template = Template.UserController;
				
				String content = FreemarkerUtils.parseTemplate(map,
					new StringBuilder(template.toString()).append(".ftl").toString()
				);
				
				SharknessGenerator.createResourceComponent(content, pacote, classe);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}

}