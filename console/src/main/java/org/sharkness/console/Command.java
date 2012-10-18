package org.sharkness.console;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Command {

	private String versionGeneratorPlugin = "1.0";
	
	private String command = null;

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
			
			Process pr = rt.exec(new StringBuilder("mvn org.sharkness:generator:").append(versionGeneratorPlugin).append(":commands").toString());

			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

			String line = null;

			while ((line = input.readLine()) != null) {
				if (line.trim().startsWith("sharkness>")) {
					line = line.replaceAll(Pattern.quote("sharkness>"), "").trim();
					System.out.println(new StringBuilder("sharkness> exec:").append(line).toString());
				}
			}

		} else if (command.startsWith("exec")) {

			Process pr = rt.exec(
				new StringBuilder("mvn org.sharkness:generator:")
					.append(versionGeneratorPlugin).append(":exec -Df=")
					.append(Arrays.asList(command.split(Pattern.quote(":"))).get(1))
						.toString()
			);

			if (pr.waitFor() == 0) {
				
				System.out.println("sharkness> Yeah! Function executed with success!");
				
			} else {
				
				System.out.println("sharkness> Ops! Someting wrong! Verify the documentation again...");
				
			}

		}

	}

}
