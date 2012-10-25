package org.sharkness.generator;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * @requiresDependencyResolution
 * @goal exec
 */
@SuppressWarnings("unchecked")
public class ExecMojo extends AbstractMojo {

	/**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
	private MavenProject project;
	
	/**
     * @parameter expression="${f}"
     */
	private String parameter;
	
	ClassLoader classLoader;

	public void execute() throws MojoExecutionException {
		
		try {
			
			Class<?> an = getClassLoader().loadClass("org.sharkness.artifacts.annotation.Generator");
			
			Class<?> cls = getClassLoader().loadClass("org.sharkness.artifacts.generate.SharknessGenerator");
			
			boolean notFounded = true;
			
			Method loadPassiveConfigurations = null;
			for (Method m : cls.getMethods()) {
				if (m.getName().equalsIgnoreCase("loadPassiveConfigurations")) {
					loadPassiveConfigurations = m;
				}
			}
			if (loadPassiveConfigurations != null) loadPassiveConfigurations.invoke(cls.newInstance(), new Object[]{});
			
			for (Method m : cls.getMethods()) {
				
				if (m.isAnnotationPresent((Class<? extends Annotation>) an)) {
					
					if (parameter != null && parameter.length() > 0) {
						
						if (m.getName().equalsIgnoreCase(parameter)) {
							
							notFounded = false;
							
							m.invoke(cls.newInstance(), new Object[]{});
							
						}

					}
					
				}
				
			}
			
			if (notFounded) throw new Exception();
			
		} catch (Exception e) {
			System.exit(1);
		}
		
	}

	protected ClassLoader getClassLoader() throws Exception {
		synchronized (ExecMojo.class) {
			if (classLoader != null) return classLoader;
		}
		synchronized (ExecMojo.class) {
			List<URL> urls = new ArrayList<URL>();
			for (Object object : project.getDependencyArtifacts()) {
				DefaultArtifact artifact = (DefaultArtifact) object;
				String filePath = new StringBuilder(System.getProperty("user.home"))
					.append("/.m2/repository/").append(artifact.getGroupId().replaceAll(Pattern.quote("."), "/"))
					.append("/").append(artifact.getArtifactId()).append("/").append(artifact.getSelectedVersion())
					.append("/").append(artifact.getArtifactId()).append("-").append(artifact.getSelectedVersion())
					.append(".jar").toString();
				File file = new File(filePath);
				urls.add(file.toURI().toURL());
			}
			for (Object object : project.getCompileClasspathElements()) {
				String path = (String) object;
				urls.add(new File(path).toURI().toURL());
			}
			classLoader = new URLClassLoader(urls.toArray(new URL[] {}));
			return classLoader;
		}
	}
}
