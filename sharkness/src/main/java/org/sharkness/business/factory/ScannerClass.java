package org.sharkness.business.factory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.sharkness.logging.support.LoggerFactory;

public class ScannerClass {

	private static Logger getLogger() {
		return LoggerFactory.getLogger();
	}

	private static List<String> getClassNamesFromPackage(String packageName) throws IOException {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    URL packageURL;
	    List<String> names = new ArrayList<String>();

	    packageName = packageName.replace(".", "/");
	    packageURL = classLoader.getResource(packageName);

	    if (packageURL.getProtocol().equals("jar")) {
	        String jarFileName;
	        JarFile jf;
	        Enumeration<JarEntry> jarEntries;
	        String entryName;

	        jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
	        jarFileName = jarFileName.substring(5,jarFileName.indexOf("!"));
	        jf = new JarFile(jarFileName);
	        jarEntries = jf.entries();
	        while(jarEntries.hasMoreElements()){
	            entryName = jarEntries.nextElement().getName();
	            if(entryName.startsWith(packageName) && entryName.length()>packageName.length()+5){
	                entryName = entryName.substring(packageName.length(),entryName.lastIndexOf('.'));
	        		Class<?> klass = null;
	        		try { klass = Class.forName(entryName); } catch (Exception e) {}
	        		if (klass != null) names.add(entryName);
	            }
	        }

	    } else {
	        File folder = new File(packageURL.getFile());
	        File[] contenuti = folder.listFiles();
	        String entryName;
	        for(File actual: contenuti){
	            entryName = new StringBuilder(packageName).append(".")
					.append(actual.getName()).toString().replaceAll("/", ".");
	            entryName = entryName.substring(0, entryName.lastIndexOf('.'));
        		Class<?> klass = null;
        		try { klass = Class.forName(entryName); } catch (Exception e) {
        			getLogger().error("ScannerClass.getClassNamesFromPackage", e);
        		}
        		if (klass != null) names.add(entryName);
	        }
	    }
	    return names;
	}
	
	public static List<String> getListNamesOfModelPackage() {
		try {
			List<String> list = ScannerClass.getClassNamesFromPackage(PropertiesFactory.getModelPackage());
			if (list != null && list.size() > 0) {
				return list;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			try {
				List<String> list = new ArrayList<String>();
				String fileEntitiesPath = null;
				try {
					fileEntitiesPath = new StringBuilder(".").append("/")
						.append(PropertiesFactory.getApplicationDevSrc())
						.append("/").append(PropertiesFactory.getModelPackage()
							.replaceAll(Pattern.quote("."), "/")).toString();
				} catch (Exception ex) {
					getLogger().error("ScannerClass.getListNamesOfModelPackage", e);
				}
				File fileEntities = new File(fileEntitiesPath);
				if (fileEntities.isDirectory()) {
					for (File fileEntity : fileEntities.listFiles()) {
						list.add(
							new StringBuilder(PropertiesFactory.getModelPackage())
								.append(".").append(fileEntity.getName().replaceAll(".java", ""))
									.toString()
						);
					}
				}
				return list;
			} catch (Exception ex) {
				getLogger().error("ScannerClass.getListNamesOfModelPackage", e);
				return new ArrayList<String>();
			}
		}
	}

}