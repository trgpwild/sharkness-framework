package org.sharkness.artifacts.generate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.sharkness.artifacts.annotation.RemoteBean;
import org.sharkness.artifacts.jsf.PropertyMenu;
import org.sharkness.artifacts.jsf.PropertyService;
import org.sharkness.artifacts.utility.FreemarkerUtils;
import org.sharkness.business.entity.Model;
import org.sharkness.business.factory.ModelFactory;
import org.sharkness.business.factory.PropertiesFactory;
import org.sharkness.business.factory.ScannerClass;

@SuppressWarnings("rawtypes")
public class WebContentGenerator extends JsfViewGenerator {
	
	private static String TOOLBAR_TEMPLATE = "toolbar.xhtml";
	private static String SERVICESERVLET_WEBINF = "service-servlet.xml";
	private static String WEB_WEBINF = "web.xml";
	
	private static boolean toolbarEnabled = true;
	private static boolean webXmlEnabled = true;
	private static boolean beanRemoteEnabled = false;
	private static boolean jsfConfigEnabled = true;

	private static String[] LIST_FORCE_GEN = new String[]{
		SERVICESERVLET_WEBINF, TOOLBAR_TEMPLATE, WEB_WEBINF
	};

	public static void setToolbarEnabled(boolean toolbarEnabled) {
		WebContentGenerator.toolbarEnabled = toolbarEnabled;
	}
	
	public static void setWebXmlEnabled(boolean webXmlEnabled) {
		WebContentGenerator.webXmlEnabled = webXmlEnabled;
	}
	
	public static void setJsfConfigEnabled(boolean jsfConfEnabled) {
		WebContentGenerator.jsfConfigEnabled = jsfConfEnabled;
	}
	
	protected static void createWebContent() throws Exception {
		copyImages();
		makeWebContent("css", "main.css");
		makeWebContent("template", "simple.xhtml");
		makeWebContent("template", "system.xhtml");
		if (toolbarEnabled) makeWebContent("template", "toolbar.xhtml");
		makeWebContent("web-inf", "faces-config.xml");
		makeWebContent("", "index.html");
		makeWebContent("", "index.xhtml");
		makeWebContent("", "login.xhtml");
	}
	
	protected static void createServerConfigContent() throws Exception {
		for (Class<Model> klassModel : ModelFactory.getSharknessClassEntities()) 
			if (klassModel.isAnnotationPresent(RemoteBean.class)) beanRemoteEnabled = true;
		makeWebContent("web-inf", "applicationContext.xml");
		if (beanRemoteEnabled) makeWebContent("web-inf", "service-servlet.xml");
		if (webXmlEnabled) makeWebContent("web-inf", "web.xml");
	}

	private static void makeWebContent(String diretorio, String filename) throws Exception {
		
		boolean initialForceGen = SharknessGenerator.isForcegen();
		
		String templateName = null;
		
		String name = filename.split("\\.")[0];
		
		if (name.equalsIgnoreCase("index") || name.equalsIgnoreCase("login")) {
			templateName = new StringBuilder("web")
				.append("/").append(filename).append(".ftl").toString();
		} else {
			templateName = new StringBuilder("web").append("/").append(diretorio)
				.append("/").append(filename).append(".ftl").toString();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("sharknessApplicationName", PropertiesFactory.getApplicationName());
		
		List<PropertyMenu> listMenuItem = new ArrayList<PropertyMenu>();
		
		for (String modelName : ScannerClass.getListNamesOfModelPackage()) {
			String model = SharknessGenerator.getSimpleModelName(modelName);
			listMenuItem.add(new PropertyMenu(model, 
				new StringBuilder(PropertiesFactory.getSystemFolder())
					.append(PropertiesFactory.getSystemManagerFolder())
					.append("/").append(model.substring(0, 1).toLowerCase())
					.append(model.substring(1)).append(".jsf").toString()
			));
		}

		map.put("listMenuItem", listMenuItem);
		
		List<PropertyService> listService = new ArrayList<PropertyService>();
    	
		for (Class<Model> klassModel : ModelFactory.getSharknessClassEntities()) {
			if (klassModel.isAnnotationPresent(RemoteBean.class)) {
				beanRemoteEnabled = true;
				listService.add(new PropertyService(klassModel.getSimpleName(),
					new StringBuilder()
						.append(SharknessGenerator.getBaseServicePackage())
						.append(klassModel.getSimpleName()).append("Service")
						.toString()
				));
			}
		}
		
		map.put("listService", listService);
		
		map.put("serviceFolder", PropertiesFactory.getRemoteServiceFolder());

		map.put("beanRemoteEnabled", beanRemoteEnabled ? 1 : 0);
		
		map.put("jsfConfigEnabled", jsfConfigEnabled ? 1 : 0);
		
		String defaultLocale = "en";

		if (PropertiesFactory.getApplicationI18nOptions().size() > 0) {
			defaultLocale = PropertiesFactory.getApplicationI18nOptions().get(0);
		}
		
		map.put("defaultLocale", defaultLocale);
		
		map.put("i18nFilename", PropertiesFactory.getApplicationI18nFilename().replaceAll("\\/", "."));
		
		diretorio = getDirRoot(diretorio);
			
		if (Arrays.asList(LIST_FORCE_GEN).contains(filename)) {
			SharknessGenerator.setForcegen(true);
		}
		
		File dir = new File(
			new StringBuilder(PropertiesFactory.getApplicationDevWebapp())
				.append("/").append(diretorio).append("/").toString()
		);
		
		String index = PropertiesFactory.getPageDefault();
		
		if (filename.equalsIgnoreCase("index.xhtml")) {
			
			filename = getXhtmlFromJsf(index);
			
		} else if (filename.equalsIgnoreCase("login.xhtml")) {
			
			filename = getXhtmlFromJsf(PropertiesFactory.getPageLogin());
			
		}
		
		File file = new File(
			new StringBuilder(dir.toString())
				.append("/").append(filename).toString()
		);
		
		map.put("indexPage", index.replaceAll("/", ""));
			
		String content = FreemarkerUtils.parseTemplate(map, templateName);

		SharknessGenerator.createResourceComponent(content, dir, file);
		
		SharknessGenerator.setForcegen(initialForceGen);
		
	}

	private static String getXhtmlFromJsf(String pageDefault) {
		return pageDefault.replaceAll("/", "").replaceAll(".jsf", ".xhtml");
	}

	private static String getDirRoot(String dirName) {
		if (dirName.equalsIgnoreCase("web-inf")) dirName = dirName.toUpperCase();
		else if (dirName.equalsIgnoreCase("web")) dirName = "";
		return dirName;
	}
	
	private static void copyImages() throws IOException, Exception {
		
		byte[] content = IOUtils.toByteArray(
			WebContentGenerator.class
				.getResourceAsStream("/org/sharkness/img/sharkness.ico")
		);
		
		File dir = new File(new StringBuilder(PropertiesFactory.getApplicationDevWebapp())
		.append("/img/").toString());

		dir.mkdirs();

		File file = new File(new StringBuilder(PropertiesFactory.getApplicationDevWebapp())
		.append("/img/sharkness.ico").toString());
	
		file.createNewFile();
		
		FileUtils.writeByteArrayToFile(file, content);
		
	}
	
	public static void main(String[] args) throws Exception {
		WebContentGenerator.makeWebContent("", "index.xhtml");
	}
	
}