package org.sharkness.artifacts.generate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.sharkness.artifacts.annotation.Generator;
import org.sharkness.business.entity.Model;
import org.sharkness.business.entity.User;
import org.sharkness.business.factory.ModelFactory;
import org.sharkness.business.factory.PropertiesFactory;
import org.sharkness.business.factory.ScannerClass;

@SuppressWarnings({"unchecked","rawtypes"})
public class SharknessGenerator extends WebContentGenerator {
	
	private static boolean forcegen = false;
	
	public static void setForcegen(boolean forcegen) {
		SharknessGenerator.forcegen = forcegen;
	}
	
	public static void setToolbarEnabled(boolean toolbarEnabled) {
		WebContentGenerator.setToolbarEnabled(toolbarEnabled);
	}
	
	public static void setWebXmlEnabled(boolean webXmlEnabled) {
		WebContentGenerator.setWebXmlEnabled(webXmlEnabled);
	}
	
	public static void setJsfConfigEnabled(boolean jsfConfEnabled) {
		WebContentGenerator.setJsfConfigEnabled(jsfConfEnabled);
	}

	public static boolean isForcegen() {
		return forcegen;
	}
	
	public void loadPassiveConfigurations() throws Exception {
		SharknessGenerator.setForcegen(PropertiesFactory.getForceCodeGeneration());
		SharknessGenerator.setToolbarEnabled(PropertiesFactory.getToolbarEnabled());
		SharknessGenerator.setWebXmlEnabled(PropertiesFactory.getWebXmlEnabled());
		SharknessGenerator.setJsfConfigEnabled(PropertiesFactory.getJsfConfigEnabled());
	}

	private static Class<Model> getClassModelBySimpleName(String model) throws ClassNotFoundException, Exception {
		return (Class<Model>) Class.forName(new StringBuilder(getBaseModelPackage()).append(model).toString());
	}
	
	public static void createAllArtifacts(String model) throws Exception {
		createAllArtifacts(getClassModelBySimpleName(model));
	}

	public static void createAllArtifacts(Class<? extends Model> klassModel) throws Exception {
		createControllerArtifact(klassModel);
		createDaoArtifact(klassModel);
		createServiceArtifact(klassModel);
	}
	
	@Generator
	public static void createArtifacts() throws Exception {
		createBusinessArtifacts();
		createViewArtifacts();
		createWebContent();
		createResourceBundle();
	}
	
	public static String getBaseModelPackage() throws Exception {
		return new StringBuilder(PropertiesFactory.getModelPackage()).append(".").toString();
	}
	
	public static String getBaseServicePackage() throws Exception {
		return new StringBuilder(PropertiesFactory.getServicePackage()).append(".").toString();
	}

	private static String getNameModelRemovingBase(String completeModelName, String base) {
		return completeModelName.replaceAll(base, "");
	}
	
	public static String getSimpleModelName(String completeModelName) throws Exception {
		return getNameModelRemovingBase(completeModelName, getBaseModelPackage());
	}
	
	public static void createResourceComponent(String content, File dirComponent, File fileComponent) throws IOException, Exception {
		dirComponent.mkdirs();
		if (SharknessGenerator.isForcegen()) {
			writeResourceComponent(content, fileComponent);
		} else if (fileComponent.createNewFile()) {
			writeResourceComponent(content, fileComponent);
		}
	}

	private static void writeResourceComponent(String content, File fileComponent) throws IOException {
		FileWriter fileWriter = new FileWriter(fileComponent);
		fileWriter.write(content.replaceAll("\\\\", ""));
		fileWriter.flush();
	}

	@Generator
	public static void createResourceBundle() throws Exception {
		String i18nFilename = PropertiesFactory.getApplicationI18nFilename();
		List<String> options = PropertiesFactory.getApplicationI18nOptions();
		if (options.size() > 0) {
			for (String i18nLocaleCode : options) {
				ResourceBundleGenerator.createBundle(
					new StringBuilder(i18nFilename).append("_")
						.append(i18nLocaleCode).append(".properties")
						.toString()
				);
				ValidationBundleGenerator.createBundle(
					new StringBuilder("ValidationMessages_")
						.append(i18nLocaleCode).append(".properties")
						.toString()
				);
			}
		} else {
			ResourceBundleGenerator.createBundle(
				new StringBuilder(i18nFilename).append(".properties")
					.toString()
			);
			ValidationBundleGenerator.createBundle(
				new StringBuilder("ValidationMessages").append(".properties")
					.toString()
			);
		}
	}
	
	@Generator
	public static void createBusinessArtifacts() throws Exception {
		WebContentGenerator.createServerConfigContent();
		createDaoArtifacts();
		createServiceArtifacts();
	}
	
	public static void createViewArtifact(String model) throws Exception {
		createViewArtifact(getClassModelBySimpleName(model));
	}

	public static void createViewArtifact(Class<? extends Model> klassModel) throws Exception {
		JsfViewGenerator.createJsfViewComponent(klassModel);
	}
	
	@Generator
	public static void createViewArtifacts() throws Exception {
		createControllerArtifacts();
		for (String modelName : ScannerClass.getListNamesOfModelPackage()) {
			createViewArtifact(getSimpleModelName(modelName));
		}
	}
	
	@Generator
	public static void createControllerArtifacts() throws Exception {
		for (String modelName : ScannerClass.getListNamesOfModelPackage()) {
			createControllerArtifact(getSimpleModelName(modelName));
		}
	}

	public static void createControllerArtifact(String model) throws Exception {
		createControllerArtifact(getClassModelBySimpleName(model));
	}
	
	public static void createControllerArtifact(Class<? extends Model> klassModel) throws Exception {
		if (klassModel.newInstance() instanceof User<?>) {
			BusinessGenerator.createBusinessComponent(klassModel.getSimpleName(), Template.UserController);
		} else {
			BusinessGenerator.createBusinessComponent(klassModel.getSimpleName(), Template.Controller);
		}
	}
	
	@Generator
	public static void createWebContent() throws Exception {
		WebContentGenerator.createWebContent();
	}
	
	@Generator
	public static void createDaoArtifacts() throws Exception {
		for (String modelName : ModelFactory.getSharknessHibernateEntities()) {
			createDaoArtifact(getSimpleModelName(modelName));
		}
	}

	public static void createDaoArtifact(String model) throws Exception {
		createDaoArtifact(getClassModelBySimpleName(model));
	}
	
	public static void createDaoArtifact(Class<? extends Model> klassModel) throws Exception {
		BusinessGenerator.createBusinessComponent(klassModel.getSimpleName(), Template.Dao);
	}
	
	@Generator
	public static void createConverterArtifacts() throws Exception {
		for (String modelName : ScannerClass.getListNamesOfModelPackage()) {
			createConverterArtifact(getSimpleModelName(modelName));
		}
	}

	public static void createConverterArtifact(String model) throws Exception {
		createConverterArtifact(getClassModelBySimpleName(model));
	}
	
	public static void createConverterArtifact(Class<Model> klassModel) throws Exception {
		BusinessGenerator.createBusinessComponent(klassModel.getSimpleName(), Template.Converter);
	}
	
	@Generator
	public static void createServiceArtifacts() throws Exception {
		for (String modelName : ScannerClass.getListNamesOfModelPackage()) {
			createServiceArtifact(getSimpleModelName(modelName));
		}
	}

	public static void createServiceArtifact(String model) throws Exception {
		createServiceArtifact(getClassModelBySimpleName(model));
	}
	
	public static void createServiceArtifact(Class<? extends Model> klassModel) throws Exception {
		BusinessGenerator.createBusinessComponent(klassModel.getSimpleName(), Template.Service);
		BusinessGenerator.createBusinessComponent(klassModel.getSimpleName(), Template.ServiceImpl);
	}
	
	@Generator
	public static void copyTemplateController() throws Exception {
		TemplateCopy.copyTemplate("Controller.ftl");
		TemplateCopy.copyTemplate("UserController.ftl");
	}
	
	@Generator
	public static void copyTemplateConverter() throws Exception {
		TemplateCopy.copyTemplate("Converter.ftl");
	}
	
	@Generator
	public static void copyTemplateCrudView() throws Exception {
		TemplateCopy.copyTemplate("CrudView.ftl");
	}
	
	@Generator
	public static void copyTemplateDao() throws Exception {
		TemplateCopy.copyTemplate("Dao.ftl");
	}
	
	@Generator
	public static void copyTemplateService() throws Exception {
		TemplateCopy.copyTemplate("Service.ftl");
		TemplateCopy.copyTemplate("ServiceImpl.ftl");
	}
	
	@Generator
	public static void copyTemplateSimpleXhtml() throws Exception {
		TemplateCopy.copyTemplate("/web/template/simple.xhtml.ftl");
	}
	
	@Generator
	public static void copyTemplateSystemXhtml() throws Exception {
		TemplateCopy.copyTemplate("/web/template/system.xhtml.ftl");
	}
	
	@Generator
	public static void copyTemplateToolbarXhtml() throws Exception {
		TemplateCopy.copyTemplate("/web/template/toolbar.xhtml.ftl");
	}
	
	@Generator
	public static void copyTemplateApplicationContext() throws Exception {
		TemplateCopy.copyTemplate("/web/web-inf/applicationContext.xml.ftl");
	}
	
	@Generator
	public static void copyTemplateServiceServlet() throws Exception {
		TemplateCopy.copyTemplate("/web/web-inf/service-servlet.xml.ftl");
	}
	
	@Generator
	public static void copyTemplateWebXml() throws Exception {
		TemplateCopy.copyTemplate("/web/web-inf/web.xml.ftl");
	}
	
	@Generator
	public static void copyContextCore() throws Exception {
		TemplateCopy.copyContext("core.xml");
	}

	@Generator
	public static void copyContextDatabase() throws Exception {
		TemplateCopy.copyContext("database.xml");
	}

	@Generator
	public static void copyContextSecurity() throws Exception {
		TemplateCopy.copyContext("security.xml");
	}

	@Generator
	public static void copyContextSharkness() throws Exception {
		TemplateCopy.copyContext("sharkness.xml");
	}

}