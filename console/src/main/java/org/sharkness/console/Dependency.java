package org.sharkness.console;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class Dependency {

	public static String sharknessVersion;

	public static String getUserHome() {
		return System.getProperty("user.home");
	}

	public static void loadSharkness() {

		try {

			String sharknessVersionRemote = null;
			
			String sharknessVersionLocal = null;
			
			String userHomePath = Dependency.getUserHome();

			System.out.println(".:: SHARKNESS FRAMEWORK ::.");

			System.out.println("sharkness> Connecting with Sharkness Framework Repository in github.com for discover the last version...");

			String metadata = "https://raw.github.com/trgpwild/sharkness-framework/master/repository/org/sharkness/sharkness/maven-metadata.xml";

			String line = null;
			
			URL url = new URL(metadata);
			
			HttpURLConnection urlconn = (HttpURLConnection) url.openConnection();
			
			urlconn.setConnectTimeout(1000);
			urlconn.setInstanceFollowRedirects(false);
			urlconn.setRequestMethod("GET");
			urlconn.connect();
			
			BufferedReader buf = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			
			while ((line = buf.readLine()) != null) {
				if (line.trim().startsWith("<release>")) {
					sharknessVersionRemote = line.replaceAll("<release>", "").replaceAll("</release>", "").trim();
				}
			}
			
			BufferedReader in = new BufferedReader(new FileReader(
				new StringBuilder(userHomePath)
					.append("/.m2/repository/org/sharkness/sharkness/maven-metadata-local.xml")
				.toString()
			));

			String str;

			while (in.ready()) {
				str = in.readLine();
				if (str.trim().startsWith("<release>")) {
					sharknessVersionLocal = str.replaceAll("<release>", "").replaceAll("</release>", "").trim();
				}
			}

			in.close();
			
			if (urlconn.getResponseCode() == 200) {
				
				Dependency.sharknessVersion = sharknessVersionRemote;
				
				Integer aI = Integer.parseInt(sharknessVersionLocal.replaceAll(Pattern.quote("."), ""));
				Integer bI = Integer.parseInt(sharknessVersionRemote.replaceAll(Pattern.quote("."), ""));

				if (bI > aI) {
					
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					
					System.out.print(
						new StringBuilder("sharkness> Do you want to update the Sharkness Framework to version ")
							.append(sharknessVersionRemote).append("? (Y/n) ")
						.toString()
					);

					String response = "y"; 
					
					String readLine = br.readLine();
					
					if (readLine != null && readLine.equalsIgnoreCase("n")) response = readLine;
					
					if (response.equalsIgnoreCase("y")) {
						
						downloadAndInstallSharknessJar();
						
					} else {
						
						Dependency.sharknessVersion = sharknessVersionLocal;
						
					}
					
				}
				
			} else {
				
				Dependency.sharknessVersion = sharknessVersionLocal;
				
			}

			System.out.println(
				new StringBuilder("sharkness> Searching Sharkness Framework(")
					.append(Dependency.sharknessVersion).append(") on: ").append(userHomePath)
					.append("/.m2/repository/org/sharkness/sharkness/").append(Dependency.sharknessVersion)
					.append("/sharkness-").append(Dependency.sharknessVersion).append(".jar")
				.toString()
			);

			System.out.println(
				new StringBuilder("sharkness> Sharkness Framework(")
					.append(Dependency.sharknessVersion)
					.append(") Ready: OK.")
				.toString()
			);

		} catch (Exception e) {

			e.printStackTrace();
			
			System.out.println(":0 - Oops!! Error on load sharkness framework...");

		}

	}

	public static ClassLoader getClassLoader() {
		return ClassLoader.getSystemClassLoader();
	}
	
	public static void downloadAndInstallSharknessJar() throws Exception {
		
		String userHomePath = Dependency.getUserHome();

		String httpAddress = new StringBuilder("https://raw.github.com/trgpwild/sharkness-framework/master/repository/org/sharkness/sharkness/")
			.append(Dependency.sharknessVersion).append("/sharkness-").append(Dependency.sharknessVersion).append(".jar")
		.toString();
		
		String httpAddressLabel = new StringBuilder("${REMOTE_REPOSITORY}/org/sharkness/sharkness/")
			.append(Dependency.sharknessVersion).append("/sharkness-").append(Dependency.sharknessVersion).append(".jar")
		.toString();

		String localAddressLabel = new StringBuilder(userHomePath).append("/.m2/repository/org/sharkness/sharkness/")
			.append(Dependency.sharknessVersion).append("/sharkness-").append(Dependency.sharknessVersion).append(".jar")
		.toString();

		System.out.println(new StringBuilder("sharkness> Downloading: ").append(httpAddressLabel).append(" ...").toString());
			
		URL url = new URL(httpAddress);

		InputStream inStream = url.openStream();
		BufferedInputStream bufIn = new BufferedInputStream(inStream);

		File fileWrite = new File(
			new StringBuilder(System.getProperty("user.home"))
				.append("/sharkness-").append(Dependency.sharknessVersion).append(".jar")
			.toString()
		);
		OutputStream out = new FileOutputStream(fileWrite);
		BufferedOutputStream bufOut = new BufferedOutputStream(out);
		byte buffer[] = new byte[8];
		while (true) {
			int nRead = bufIn.read(buffer, 0, buffer.length);
			if (nRead <= 0)
				break;
			bufOut.write(buffer, 0, nRead);
		}
		
		bufOut.flush();
		out.close();
		inStream.close();
		
		Runtime rt = Runtime.getRuntime();
		
		Process pr = rt.exec(
			new StringBuilder("mvn install:install-file -DgroupId=org.sharkness -DartifactId=sharkness -Dpackaging=jar -Dversion=")
				.append(Dependency.sharknessVersion).append(" -Dfile=").append(fileWrite.getCanonicalPath()).append(" -DgeneratePom=true")
			.toString()
		);

		if (pr.waitFor() == 0) {
			
			System.out.println(new StringBuilder("sharkness> Downloaded: ").append(localAddressLabel).append(" ...").toString());

			if (fileWrite.exists()) fileWrite.delete();

		}
		
	}
	
}