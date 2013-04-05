package org.sharkness.console;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.sharkness.exception.IsNotAnSharknessAppException;
import org.sharkness.exception.NotFoundSharknessLibException;

public class Dependency {

	public static String sharknessVersion;
	public static String generatorVersion;
	public static String archetype4tomcatVersion;

	public static String getUserHome() {
		return System.getProperty("user.home");
	}
	
	public static boolean mvnIsInstalled() {
		
		try {
			
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("mvn -version");
			
			if (pr.waitFor() == 0) {
				
				return true;
				
			} else {
				
				return false;
				
			}
			
		} catch (Exception e) {
			
			return false;
			
		}
		
	}

	public static void loadSharkness() {
		
		System.out.println(".:: SHARKNESS FRAMEWORK ::.");

		if (mvnIsInstalled()) {
			
			try {
				
				String remoteRepository = "https://raw.github.com/trgpwild/sharkness-framework/master/repository";
				
				Dependency.sharknessVersion = DownloadSupport.loadResource("Sharkness Framework", remoteRepository, "sharkness");
				Dependency.generatorVersion = DownloadSupport.loadResource("Sharkness Generator", remoteRepository, "generator");
				Dependency.archetype4tomcatVersion = DownloadSupport.loadResource("Sharkness Archetype for Tomcat", remoteRepository, "archetype4tomcat");

				updatePom();

			} catch (Exception e) {

				System.out.println("sharkness> :0 - Oops!! Error on load sharkness framework...");
				
				e.printStackTrace();
				
				System.exit(1);

			}
			
		} else {

			System.out.println("sharkness> Maven is not installed, the Sharkness Framework need this to work...");
			
			System.exit(1);

		}

	}

	public static ClassLoader getClassLoader() {
		return ClassLoader.getSystemClassLoader();
	}
	
	@SuppressWarnings("resource")
	public static void updatePom() throws Exception {
		
		try {
			
			File pom = new File("pom.xml");
			
			if (pom.exists()) {
				
				BufferedReader in = new BufferedReader(new FileReader(pom));

				String str;
				
				boolean toRead = false;
				
				boolean foundSharkness = false;
				
				List<String> lines = new ArrayList<String>();
				
				while (in.ready()) {
					str = in.readLine();
					if (toRead) {
						toRead = false;
						String value = str.trim().replaceAll(Pattern.quote("<version>"), "").replaceAll(Pattern.quote("</version>"), "");
						str = str.replaceAll(Pattern.quote(value), Dependency.sharknessVersion);
					}
					if (str.trim().equalsIgnoreCase("<artifactId>sharkness</artifactId>")) {
						foundSharkness = true;
						toRead = true;
					}
					lines.add(str);
				}
				
				if (!foundSharkness) throw new NotFoundSharknessLibException();

				in.close();

				FileOutputStream fos = new FileOutputStream(pom);
				for (String line : lines) {
					byte[] write = line.getBytes();
					fos.write(write);
					fos.write(13);
				}
				fos.close();
				
			} else {
				
				throw new IsNotAnSharknessAppException();
				
			}
			
		} catch (IsNotAnSharknessAppException e) {

			System.out.println("sharkness> :0 - Oops!! This isn't an Sharkness Application...");

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			System.out.print("sharkness> Do you want create an app? (Y/n) ");
			
			String response = "y";
			
			String read = Command.getKeyboard(br);
			
			if (read != null && read.length() > 0) response = read;
			
			if (response.equalsIgnoreCase("y")) {
				
				Command.newApp();
				
			} else {
				
				System.out.println("sharkness> Okey! Bye bye...");
				
				System.exit(0);

			}

		} catch (NotFoundSharknessLibException e) {

			System.out.println("sharkness> :0 - Oops!! Not found the Sharkness Library in pom...");

		}

	}

	
}