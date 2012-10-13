package org.sharkness.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * @requiresDependencyResolution
 * @goal generator
 */
@SuppressWarnings("unchecked")
public class MainMojo extends AbstractMojo {

	/**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
	private MavenProject project;
	
	ClassLoader classLoader;

	public void execute() throws MojoExecutionException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Map<String, Method> methods = new HashMap<String, Method>();
		
		try {
			
			Class<?> an = getClassLoader().loadClass("org.sharkness.artifacts.annotation.Generator");
			
			Class<?> cls = getClassLoader().loadClass("org.sharkness.artifacts.generate.SharknessGenerator");
			
			System.out.println("");
			
			System.out.println("sharkness> Choose an option to execute above...");
			
			Integer option = 0;
			
			methods.put(option.toString(), null);
			
			System.out.println(new StringBuilder(option.toString()).append(" - ").append("Quit").toString());

			for (Method m : cls.getMethods()) {
				
				if (m.isAnnotationPresent((Class<? extends Annotation>) an)) {
					
					option++;
					
					methods.put(option.toString(), m);
					
					System.out.println(new StringBuilder(option.toString()).append(" - ").append(m.getName()).toString());
					
				}
				
			}
			
			callExecution(br, methods, cls);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("sharkness> Ops! Function not founded... Try again.");
		}
		
	}

	protected void callExecution(BufferedReader br, Map<String, Method> methods, Class<?> cls) throws Exception {
		
		System.out.print("sharkness> "); String optionChoosed = br.readLine();
		
		if (methods.containsKey(optionChoosed)) {
			
			Method m = methods.get(optionChoosed);
			
			if (m != null) {
				
				m.invoke(cls.newInstance(), new Object[]{});
								
			} else {
				
				System.out.println("sharkness> Bye!");
				
			}

		} else {
			
			System.out.println("sharkness> Ops! Function not founded... Try again.");

			callExecution(br, methods, cls);
			
		}
	}

	protected ClassLoader getClassLoader() throws Exception {
		synchronized (MainMojo.class) {
			if (classLoader != null) return classLoader;
		}
		synchronized (MainMojo.class) {
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
