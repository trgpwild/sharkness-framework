package org.sharkness.console;

import java.io.BufferedReader;
import java.io.FileReader;

public class CLExec {

	public static void main(String args[]) {

		try {

			// Runtime rt = Runtime.getRuntime();
			// Process pr = rt.exec("mvn -version");
			// int exitVal = pr.waitFor();
			// System.out.println("Exited with error code "+exitVal);

			/**
			 * Construir atualizacao de pom.xml corrente
			 */
			BufferedReader in = new BufferedReader(new FileReader("./pom.xml"));

			String str;

			boolean toRead = false;
			
			while (in.ready()) {
				str = in.readLine();
				if (toRead) {
					toRead = false;
					System.out.println(str.trim());
				}
				if (str.trim().equalsIgnoreCase("<artifactId>sharkness</artifactId>")) toRead = true;
			}

			in.close();

		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

}
