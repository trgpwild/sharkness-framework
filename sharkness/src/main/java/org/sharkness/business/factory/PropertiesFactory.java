package org.sharkness.business.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javassist.NotFoundException;

public class PropertiesFactory {

	private static Properties cfg;
	
	private InputStream getSharknessInputStream(String propFileName) {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
		if (inputStream != null) {
			return inputStream;
		} else {
			try {
				return new FileInputStream(new File(
					new StringBuilder("./").append(getApplicationDevResources())
						.append("/").append(propFileName).toString()
				));
			} catch (FileNotFoundException e) {
				return null;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	public static Properties getSharknessProperties() {
		if (cfg == null) {
			try {
				cfg = new Properties();
	        	cfg.loadFromXML(new PropertiesFactory()
	        		.getSharknessInputStream("sharkness.cfg.xml"));
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		}
		return cfg;
	}
	
	private static String getPackageOfArtifact(String package_, String key) throws Exception {
		if (getSharknessProperties().containsKey(key)) {
			return getApplicationPackage(getSharknessProperties().getProperty(key));
		}
		return getApplicationPackage(package_);
	}
	
	private static String getPropertyValue(String default_, String key) throws Exception {
		if (getSharknessProperties().containsKey(key)) {
			return getPropertyValue(key);
		}
		return default_;
	}

	private static String getPropertyValue(String key) throws Exception {
		if (getSharknessProperties().containsKey(key)) {
			return getSharknessProperties().getProperty(key);
		} else {
			throw new NotFoundException(
				new StringBuilder("Must define config property in sharkness.ini : ")
					.append(key).toString()
			);
		}
	}

	public static String getApplicationName() throws Exception {
		return getPropertyValue("sharkness.application.name");
	}

	public static String getApplicationPackage(String subpackage) throws Exception {
		return new StringBuilder(getApplicationPackage())
			.append(".").append(subpackage).toString();
	}
	
	private static boolean stringToBoolean(String str) {
		return str.equals("true") ? true : false;
	}

	public static boolean getForceCodeGeneration() throws Exception {
		return stringToBoolean(getPropertyValue("false","sharkness.forceGenerationCode"));
	}

	public static boolean getToolbarEnabled() throws Exception {
		return stringToBoolean(getPropertyValue("true","sharkness.toolbarEnabled"));
	}

	public static boolean getWebXmlEnabled() throws Exception {
		return stringToBoolean(getPropertyValue("true","sharkness.webXmlEnabled"));
	}

	public static boolean getJsfConfigEnabled() throws Exception {
		return stringToBoolean(getPropertyValue("true","sharkness.jsfConfigEnabled"));
	}

	public static String getApplicationDevSrc() throws Exception {
		return getPropertyValue("src/main/java", "sharkness.application.dev.src");
	}

	public static String getApplicationDevWebapp() throws Exception {
		return getPropertyValue("src/main/webapp", "sharkness.application.dev.webapp");
	}

	public static String getApplicationDevResources() throws Exception {
		return getPropertyValue("src/main/resources", "sharkness.application.dev.resources");
	}

	public static String getApplicationI18nFilename() throws Exception {
		return getPropertyValue("messages", "sharkness.application.i18n.filename");
	}

	public static List<String> getApplicationI18nOptions() throws Exception {
		String strOptions = getPropertyValue(null, "sharkness.application.i18n.options");
		if (strOptions != null && strOptions.length() > 0) {
			return Arrays.asList(strOptions.split("\\,"));
		} else {
			return new ArrayList<String>();
		}
	}

	public static String getApplicationPackage() throws Exception {
		return getPropertyValue("sharkness.application.package");
	}

	public static String getDatabaseJdbcUrl() throws Exception {
		return getPropertyValue("sharkness.database.jdbcUrl");
	}
	
	public static String getDatabaseDriverClass() throws Exception {
		return getPropertyValue("sharkness.database.driverClass");
	}

	public static String getDatabaseUser() throws Exception {
		return getPropertyValue("sharkness.database.user");
	}

	public static String getDatabasePassword() throws Exception {
		return getPropertyValue("sharkness.database.password");
	}

	public static String getDatabaseInitialPoolSize() throws Exception {
		return getPropertyValue("5","sharkness.database.initialPoolSize");
	}

	public static String getDatabaseMaxPoolSize() throws Exception {
		return getPropertyValue("10","sharkness.database.maxPoolSize");
	}

	public static String getDatabaseUsernameHQL() throws Exception {
		String defaultHql = new StringBuilder("from ")
			.append(ModelFactory.getUserClassName())
			.append(" where username = ?").toString();
		return getPropertyValue(defaultHql, "sharkness.database.username.hql");
	}

	public static String getHibernateDialect() throws Exception {
		return getPropertyValue("sharkness.hibernate.dialect");
	}

	public static String getHibernateDDL() throws Exception {
		return getPropertyValue("validate","sharkness.hibernate.ddl");
	}

	public static String getHibernateShowSQL() throws Exception {
		return getPropertyValue("false","sharkness.hibernate.showsql");
	}

	public static String getHibernateFormatSQL() throws Exception {
		return getPropertyValue("false","sharkness.hibernate.formatsql");
	}

	public static String getAuditActivated() throws Exception {
		return getPropertyValue("false","sharkness.audit.activated");
	}

	public static String getModelPackage() throws Exception {
		return getPackageOfArtifact("entity", "sharkness.model.package");
	}

	public static String getDaoPackage() throws Exception {
		return getPackageOfArtifact("dao", "sharkness.dao.package");
	}

	public static String getControllerPackage() throws Exception {
		return getPackageOfArtifact("controller", "sharkness.controller.package");
	}

	public static String getServicePackage() throws Exception {
		return getPackageOfArtifact("service", "sharkness.service.package");
	}
	
	public static String getServiceImplPackage() throws Exception {
		return getPackageOfArtifact("service.impl", "sharkness.service.impl.package");
	}

	public static String getConverterPackage() throws Exception {
		return getPackageOfArtifact("converter", "sharkness.converter.package");
	}
	
	public static String getSystemFolder() throws Exception {
		return getPropertyValue("sharkness.system.folder");
	}
	
	public static String getSystemManagerFolder() throws Exception {
		return getPropertyValue("sharkness.system.manager.folder");
	}
	
	public static String getRemoteServiceFolder() throws Exception {
		return getPropertyValue("sharkness.remote.service.folder");
	}

	public static String getPageDefault() throws Exception {
		return getPropertyValue("sharkness.page.default");
	}

	public static String getPageLogin() throws Exception {
		return getPropertyValue("sharkness.page.login");
	}

	public static String getPageAccessDenied() throws Exception {
		return getPropertyValue("sharkness.page.access.denied");
	}

	public static String getPageAuthFailure() throws Exception {
		return getPropertyValue("sharkness.page.auth.failure");
	}

	public static String getRoleAdminValue() throws Exception {
		return getPropertyValue("sharkness.role.admin.value");
	}

	public static String getRoleRemoteValue() throws Exception {
		return getPropertyValue("sharkness.role.remote.value");
	}
	
	public static Properties getHibernateProperties() throws Exception {
		
		String hibernateConfigPrefix = "sharkness.hibernate:";
		
		Properties hp = new Properties();
		hp.put("hibernate.dialect", getHibernateDialect());
		hp.put("hibernate.show_sql", getHibernateShowSQL());
		hp.put("hibernate.format_sql", getHibernateFormatSQL());
		hp.put("hibernate.hbm2ddl.auto", getHibernateDDL());

		for (Entry<Object, Object> entry : getSharknessProperties().entrySet()) {
			if (entry.getKey().toString().startsWith(hibernateConfigPrefix)) {
				hp.put(entry.getKey().toString().replaceAll(hibernateConfigPrefix, ""), entry.getValue());
			}
		}
		
		return hp;
		
	}

	public static String getApplicationCrudFolder() throws Exception {
		return new StringBuilder(getApplicationDevWebapp())
			.append("/").append(getSystemFolder()).append("/")
			.append(getSystemManagerFolder()).toString();
	}
	
}