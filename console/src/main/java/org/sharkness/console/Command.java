package org.sharkness.console;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Command {

	private String command = null;
	
	private String pluginVersion = "1.0";

	public Command(String command) throws Exception {

		String[] commands = command.split(" ");

		StringBuilder str = new StringBuilder();

		for (int i = 0; i < commands.length; i++) {
			str.append(commands[i]);
		}

		command = str.toString();

		this.command = command;

	}

	public void execute() throws Exception {

		Runtime rt = Runtime.getRuntime();

		if (command.equals("pwd")) {
			
			System.out.println(
				new StringBuilder("sharkness> ")
					.append(new File(new StringBuilder(".").toString())
						.toURI().toURL().toString().replaceAll(Pattern.quote("file:"), "")
							.replaceAll(Pattern.quote("./"), "")).toString()
			);

		} else if (command.equalsIgnoreCase("help")) {
			
			Process pr = rt.exec(new StringBuilder("mvn org.sharkness:generator:").append(pluginVersion).append(":commands").toString());

			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

			String line = null;

			while ((line = input.readLine()) != null) {
				if (line.trim().startsWith("sharkness>")) {
					line = line.replaceAll(Pattern.quote("sharkness>"), "").trim();
					System.out.println(new StringBuilder("sharkness> exec:").append(line).toString());
				}
			}
			
		} else if (command.startsWith("exec")) {

			Process pr = rt.exec("mvn compile");

			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

			List<String> list = new ArrayList<String>();
			
			String line;
			while ((line = input.readLine()) != null) {
				list.add(new StringBuilder("sharkness> ").append(line).toString());
			}

			if (pr.waitFor() == 0) {
				
				String cmd = new StringBuilder("mvn org.sharkness:generator:")
					.append(pluginVersion).append(":exec -Df=")
					.append(Arrays.asList(command.split(Pattern.quote(":"))).get(1))
						.toString();
	
				pr = rt.exec(cmd);
	
				input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
	
				list = new ArrayList<String>();
				
				while ((line = input.readLine()) != null) {
					list.add(new StringBuilder("sharkness> ").append(line).toString());
				}
	
				if (pr.waitFor() == 0) {
					
					System.out.println("sharkness> Yeah! Function executed with success!");
					
				} else {
					
					System.out.println("sharkness> Ops! Someting wrong! Verify the documentation again...");
					
					for (String str : list) System.out.println(str);
					
				}
	
			} else {
				
				System.out.println("sharkness> Ops! Couldn't compile the application...");
				
				for (String str : list) System.out.println(str);

			}
			
		}

	}
	
	public static void newApp() throws Exception {

		Runtime rt = Runtime.getRuntime();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("sharkness> Create the Application: ");
		
		System.out.print("sharkness> GroupId: "); String groupId = getKeyboard(br);
		
		String pack = new StringBuilder(groupId).append(".").toString();
		
		System.out.print("sharkness> ArtifactId: "); String artifactId = getKeyboard(br);
		
		String version = "1.0";
		
		System.out.print(new StringBuilder("sharkness> Initial Version of App (").append(version).append("): ").toString());
		
		String versionRead = getKeyboard(br);
		
		if (versionRead != null && versionRead.length() > 0) version = versionRead;
		
		String cmd = new StringBuilder("mvn archetype:generate -B ")
			.append("-DarchetypeGroupId=org.sharkness ")
			.append("-DarchetypeArtifactId=archetype4tomcat ")
			.append("-DarchetypeVersion=").append(Dependency.archetype4tomcatVersion).append(" ")
			.append("-DgroupId=").append(groupId).append(" ")
			.append("-Dversion=").append(version).append(" ")
			.append("-Dpackage=").append(pack).append(".entity ")
			.append("-DartifactId=").append(artifactId)
		.toString();
		
		Process pr = rt.exec(cmd);

		BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

		System.out.print("sharkness> Creating .");
		while (input.readLine() != null) {
			System.out.print(".");
		}
		
		System.out.println(".");
		
		if (pr.waitFor() == 0) {
			
			System.out.println("sharkness> ********************************************************************************************************************");
			
			System.out.println("sharkness> Yeah! The project was created with success! Please enter in directory of application and back to Sharkness Framework");

			System.out.println("sharkness> ********************************************************************************************************************");
			
			System.out.println("sharkness>");

			File readme = new File(new StringBuilder(artifactId).append("/README").toString());
			
			if (readme.exists()) {
				
				BufferedReader in = new BufferedReader(new FileReader(readme));
	
				while (in.ready())	System.out.println(new StringBuilder("sharkness> ").append(in.readLine()).toString());
	
				in.close();
				
			}
			
			System.exit(0);
			
		} else {
			
			System.out.println("sharkness> Ops! Someting wrong! Verify the documentation again...");
			
			System.exit(1);
			
		}

	}
	
	public static String getKeyboard(BufferedReader bufferedReader) throws IOException {
		return bufferedReader.readLine().trim().replaceAll(Pattern.quote(" "), "");
	}
	
}
