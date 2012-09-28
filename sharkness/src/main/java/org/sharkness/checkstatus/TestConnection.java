package org.sharkness.checkstatus;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class TestConnection {

	private String url;
	private String port;
	
	public static void main(String[] args) throws IOException {
		System.out.println(
			TestConnection.isConnected("localhost", 8080)
		);
	}
	
	public TestConnection(String url,  String port) {
		this.url = url;
		this.port = port;
	}
	
	public static boolean isConnected(String url) throws IOException {
		return isConnected(url, 80);
	}
	
	public static boolean isConnected(String url, int port) throws IOException {
		
		try {
		    
			InetAddress addr = InetAddress.getByName(url);
		    SocketAddress sockaddr = new InetSocketAddress(addr, port);
		    Socket sock = new Socket();
		    sock.connect(sockaddr, 3000);
		    
		    return sock.isConnected();
		    
		} catch (Exception e) {
			
			return false;
			
		}
		
	}
	
	public boolean isConnected() throws Exception {
		return isConnected(url, new Integer(port));
	}
	
}