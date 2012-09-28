package org.sharkness.artifacts.generate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Properties;

import org.sharkness.business.entity.Model;
import org.sharkness.business.factory.ModelFactory;
import org.sharkness.business.factory.PropertiesFactory;

@SuppressWarnings("rawtypes")
public class ResourceBundleGenerator extends BundleGenerator {

	public static void createBundle(String filePath) throws Exception {
		
		ready = new ArrayList<String>();
		
		String[] arr = filePath.split("\\/");
		
		String strfolder = filePath.replaceAll(arr[arr.length-1], "");
		
		File folder = new File(
			new StringBuilder(PropertiesFactory.getApplicationDevResources())
				.append("/").append(strfolder).toString()
		);
		
		if (!folder.exists()) folder.mkdirs();

		File messages = new File(
			new StringBuilder(PropertiesFactory.getApplicationDevResources())
				.append("/").append(filePath).toString()
		);
		
		if (!messages.exists()) messages.createNewFile();
		
		Properties old = new Properties();
		old.load(new FileInputStream(messages));
		
		Properties update = new SortedProperties();

		update = updateProperty("global.table.edit","Edit", update, old);
		update = updateProperty("global.table.delete","Delete", update, old);
		update = updateProperty("global.table.save","Save", update, old);
		update = updateProperty("global.manager.label","Manager", update, old);
		update = updateProperty("global.exit.label","Exit", update, old);
		update = updateProperty("global.confirmation.delete","Do you really want to delete this?", update, old);
		update = updateProperty("global.yes.label","Yes", update, old);
		update = updateProperty("global.no.label","No", update, old);
		
		update = updateProperty("global.delete.dataIntegrityViolationException",
					"Could not delete the record. It may be in use somewhere else.", update, old);
		update = updateProperty("global.delete.exception",
					"Could not delete the record. Unexpected Error.", update, old);
		update = updateProperty("global.save.success","Success", update, old);
		update = updateProperty("global.save.failure","Failure", update, old);
		
		update = updateProperty("page.login.title","Login", update, old);
		update = updateProperty("page.login.username","Username", update, old);
		update = updateProperty("page.login.password","Password", update, old);
		update = updateProperty("page.login.msg.error","Username or Password incorrect.", update, old);
		update = updateProperty("page.login.msg.denied","Access denied. User without permission.", update, old);
		update = updateProperty("page.login.button","Login", update, old);
		
		update = updateProperty("page.home.title","Home Page", update, old);
		update = updateProperty("page.home.welcome","Welcome to Home Page.", update, old);

		for (Class<? extends Model> klassModel : ModelFactory.getSharknessClassEntities()) {
			
			String panelTitle = new StringBuilder("model.").append(klassModel.getSimpleName().substring(0, 1).toLowerCase())
				.append(klassModel.getSimpleName().substring(1)).append(".panel.title")
				.toString();
			String valuePanelTitle = new StringBuilder(klassModel.getSimpleName()).append(" Management").toString();
			update = updateProperty(panelTitle, valuePanelTitle, update, old);

			String tableNew = new StringBuilder("model.").append(klassModel.getSimpleName().substring(0, 1).toLowerCase())
				.append(klassModel.getSimpleName().substring(1)).append(".table.new")
				.toString();
			String valueTableNew = new StringBuilder("New ").append(klassModel.getSimpleName()).toString();
			update = updateProperty(tableNew, valueTableNew, update, old);

			String formTitle = new StringBuilder("model.").append(klassModel.getSimpleName().substring(0, 1).toLowerCase())
				.append(klassModel.getSimpleName().substring(1)).append(".form.title")
				.toString();
			String valueFormTitle = new StringBuilder(klassModel.getSimpleName()).append(" Registration").toString();
			update = updateProperty(formTitle, valueFormTitle, update, old);
			
			for (Field field : klassModel.getDeclaredFields()) {
				if (!field.getName().equalsIgnoreCase("serialVersionUID")) {
					String key = new StringBuilder("model.").append(klassModel.getSimpleName().substring(0, 1).toLowerCase())
						.append(klassModel.getSimpleName().substring(1)).append(".property.")
						.append(field.getName().substring(0, 1).toLowerCase())
						.append(field.getName().substring(1)).toString();
					String value = new StringBuilder(field.getName().substring(0, 1).toUpperCase())
						.append(field.getName().substring(1)).toString();
					update = updateProperty(key, value, update, old);
				}
			}
			
		}
		
		for (String key : old.stringPropertyNames()) {
			if (!ready.contains(key)) update.put(key, old.getProperty(key));
		}
		
		update.store(new FileOutputStream(messages), "Internationalization by Sharkness Framework");

	}

}