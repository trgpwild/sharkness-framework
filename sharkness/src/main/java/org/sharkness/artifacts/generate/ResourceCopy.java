package org.sharkness.artifacts.generate;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.sharkness.business.factory.PropertiesFactory;

public class ResourceCopy {

	public static void template(String resource) throws IOException, Exception {
		copyGeneric("/org/sharkness/artifacts/templates/", resource);
	}

	public static void context(String resource) throws IOException, Exception {
		copyGeneric("/org/sharkness/context/", resource);
	}

	public static void copyGeneric(String root, String resource) throws IOException, Exception {
		
		resource = resource.replaceFirst("/", "");
		
		String[] arr = resource.split("\\/");
		
		String templateName = arr[arr.length-1];
		
		String base = resource.replaceAll(templateName, "");
		
		System.out.println(new StringBuilder(root).append(resource).toString());
		
		byte[] content = IOUtils.toByteArray(
			WebContentGenerator.class.getResourceAsStream(
				new StringBuilder(root)
					.append(resource).toString()
			)
		);

		new File(
			new StringBuilder(PropertiesFactory.getApplicationDevResources())
				.append(root).append(base).toString()
		).mkdirs();

		File file = new File(
			new StringBuilder(PropertiesFactory.getApplicationDevResources())
				.append(root).append(resource).toString()
		);
		
		file.createNewFile();
	
		FileUtils.writeByteArrayToFile(file, content);
		
	}
	
}