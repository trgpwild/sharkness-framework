package org.sharkness.artifacts.utility;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class FreemarkerUtils {

	private static Configuration cfg = new Configuration();

	public static String TEMPLATES_FOLDER = "/org/sharkness/artifacts/templates/";
	
	public static String getTemplate(String templateName) throws Exception {
		return new StringBuilder(TEMPLATES_FOLDER).append(templateName)
			.toString();
	}
	
	public static final String parseTemplate(Map<String, Object> map, String templateName) throws Exception {

		cfg.setClassForTemplateLoading(FreemarkerUtils.class, "/");
		
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		Template t = cfg.getTemplate(getTemplate(templateName));

		StringWriter writer = new StringWriter();

		t.process(map, writer);

		writer.flush();

		writer.close();

		return writer.toString();

	}

}