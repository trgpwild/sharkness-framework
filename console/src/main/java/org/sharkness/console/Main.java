package org.sharkness.console;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;

@SuppressWarnings({"unused"})
public class Main {

	public static void main(String[] args) throws Exception {

		Dependency.loadSharkness();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String command = "";

		try {
			
			while (!command.equals("exit")) {
				
				System.out.print("sharkness> ");

				command = br.readLine();
				
				if (!command.equals("exit")) new Command(command).execute();

			}
			
		} catch (IOException ioe) {
			
			System.out.println("LOL, IO (Input/Output) Error !!!");
			
			System.exit(1);
			
		}

	}

}