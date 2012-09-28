package org.sharkness.artifacts.generate;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sharkness.artifacts.annotation.NoPanelForm;
import org.sharkness.artifacts.jsf.PropertyModel;
import org.sharkness.artifacts.jsf.Tag;
import org.sharkness.artifacts.jsf.TagFactory;
import org.sharkness.artifacts.jsf.ViewValue;
import org.sharkness.artifacts.jsf.ViewValueFactory;
import org.sharkness.artifacts.utility.FreemarkerUtils;
import org.sharkness.business.entity.Model;
import org.sharkness.business.factory.PropertiesFactory;

@SuppressWarnings("rawtypes")
public class JsfViewGenerator extends BusinessGenerator {

	protected static void createJsfViewComponent(Class<? extends Model> classModel) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String classModelName = classModel.getSimpleName();
		
		TagFactory tagFactory = new TagFactory(classModelName);
		
		List<Tag> tags = new ArrayList<Tag>();
		
		List<PropertyModel> listProperties = new ArrayList<PropertyModel>();
		
		for (Field field : classModel.getDeclaredFields()) {
			
			if (!field.getName().equalsIgnoreCase("serialVersionUID")) {

				ViewValue viewValue = ViewValueFactory.getViewValueByField(field);
				
				if (viewValue.getDataTable()) {
					
					String propName = ViewValueFactory.getPropertyNameOfList(field, viewValue);

					listProperties.add(new PropertyModel(classModelName, viewValue.getName(), propName, field));
					
				}
				
				if (!field.isAnnotationPresent(NoPanelForm.class)) {
					
					tags.add(tagFactory.getTagByField(viewValue.getTag(), field));
					
				}
				
			}
			
		}
		
		map.put("tags", tags);
		
		map.put("model", classModelName);

		map.put("listProperties", listProperties);
		
		String content = FreemarkerUtils.parseTemplate(map, "CrudView.ftl");
		
		File dirWebapp = new File(
			PropertiesFactory.getApplicationCrudFolder()
		);
		
		File xhtml = new File(
			new StringBuilder(PropertiesFactory.getApplicationCrudFolder())
				.append("/").append(classModelName.substring(0, 1).toLowerCase())
				.append(classModelName.substring(1)).append(".xhtml").toString()
		);
		
		SharknessGenerator.createResourceComponent(content, dirWebapp, xhtml);
		
	}

}