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

public class DownloadSupport {
	
	/**
	 * type		field				example
	 * @param 	label				Sharkness Framework
	 * @param 	remoteRepository 	https://raw.github.com/trgpwild/sharkness-framework/master/repository
	 * @param 	artifactId			sharkness
	 */

	public static String loadResource(String label, String remoteRepository, String artifactId) throws Exception {

		String version = null;
		
		String versionRemote = "0";

		String versionLocal = "0";

		String userHomePath = Dependency.getUserHome();

		String metadata = new StringBuilder(remoteRepository).append("/org/sharkness/")
			.append(artifactId).append("/maven-metadata.xml").toString();

		boolean online = false;
		
		try {
			
			String line = null;

			URL url = new URL(metadata);

			HttpURLConnection urlconn = (HttpURLConnection) url.openConnection();

			urlconn.setConnectTimeout(1000);
			urlconn.setInstanceFollowRedirects(false);
			urlconn.setRequestMethod("GET");
			urlconn.connect();

			BufferedReader buf = new BufferedReader(new InputStreamReader(
					urlconn.getInputStream()));

			while ((line = buf.readLine()) != null) {
				if (line.trim().startsWith("<release>")) {
					versionRemote = line.replaceAll("<release>", "")
							.replaceAll("</release>", "").trim();
				}
			}
			
			if (urlconn.getResponseCode() == 200) {
				
				online = true;
				
			} else {
				
				throw new Exception();
				
			}

		} catch (Exception e) {
			
			online = false;
			
		}

		BufferedReader in = new BufferedReader(
			new FileReader(new StringBuilder(userHomePath).append("/.m2/repository/org/sharkness/")
					.append(artifactId).append("/maven-metadata-local.xml").toString()));

		String str;

		while (in.ready()) {
			str = in.readLine();
			if (str.trim().startsWith("<release>")) {
				versionLocal = str.replaceAll("<release>", "")
						.replaceAll("</release>", "").trim();
			}
		}

		in.close();

		if (online && !versionRemote.equals("0")) {

			version = versionRemote;

			Integer aI = Integer.parseInt(versionLocal.replaceAll(Pattern.quote("."), ""));
			Integer bI = Integer.parseInt(versionRemote.replaceAll(Pattern.quote("."), ""));

			if (bI > aI) {

				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

				System.out.print(new StringBuilder("sharkness> Do you want to update the ").append(label).append(" to version ")
					.append(versionRemote).append("? PS.: This is recommended (Y/n) ").toString());

				String response = "y";

				String readLine = br.readLine();

				if (readLine != null && readLine.equalsIgnoreCase("n"))
					response = readLine;

				if (response.equalsIgnoreCase("y")) {

					downloadAndInstallJar(version, remoteRepository, artifactId);

				} else {

					version = versionLocal;

				}

			}

		} else {

			version = versionLocal;
			
			if (version.equals("0")) {
				
				System.out.println(new StringBuilder("sharkness> ").append(label).append(" never been installed and your connection with the github is out, please establish your internet connection and try again.").toString());
				
				System.exit(0);
				
			} else {
				
				System.out.println(new StringBuilder("sharkness> Your system is offline, but it's all ok, ").append(label).append(" was installed before.").toString());
				
			}

		}

		System.out.println(new StringBuilder("sharkness> ").append(label).append("(")
			.append(version).append(") on: ").append(userHomePath).append("/.m2/repository/org/sharkness/")
			.append(artifactId).append("/").append(version).append("/sharkness-").append(version).append(".jar")
		.toString());

		System.out.println(new StringBuilder(
				"sharkness> ").append(label).append("(")
				.append(version).append(") Ready: OK.")
				.toString());
		
		return version;

	}

	public static void downloadAndInstallJar(String version, String remoteRepository, String artifactId) throws Exception {
		
		String userHomePath = Dependency.getUserHome();

		String httpAddress = new StringBuilder(remoteRepository).append("/org/sharkness/").append(artifactId).append("/")
			.append(version).append("/").append(artifactId).append("-").append(version).append(".jar")
		.toString();
		
		String httpAddressLabel = new StringBuilder("${REMOTE_REPOSITORY}/org/sharkness/").append(artifactId).append("/")
			.append(version).append("/").append(artifactId).append("-").append(version).append(".jar")
		.toString();

		String localAddressLabel = new StringBuilder(userHomePath).append("/.m2/repository/org/sharkness/").append(artifactId).append("/")
			.append(version).append("/").append(artifactId).append("-").append(version).append(".jar")
		.toString();

		System.out.println(new StringBuilder("sharkness> Downloading: ").append(httpAddressLabel).append(" ...").toString());
			
		URL url = new URL(httpAddress);

		InputStream inStream = url.openStream();
		BufferedInputStream bufIn = new BufferedInputStream(inStream);

		File fileWrite = new File(
			new StringBuilder(System.getProperty("user.home"))
				.append("/").append(artifactId).append("-").append(version).append(".jar")
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
			new StringBuilder("mvn install:install-file -DgroupId=org.sharkness -DartifactId=").append(artifactId).append(" -Dpackaging=jar -Dversion=")
				.append(version).append(" -Dfile=").append(fileWrite.getCanonicalPath()).append(" -DgeneratePom=true")
			.toString()
		);

		if (pr.waitFor() == 0) {
			
			System.out.println(new StringBuilder("sharkness> Downloaded: ").append(localAddressLabel).append(" ...").toString());

			if (fileWrite.exists()) fileWrite.delete();
			
		}
		
	}

}