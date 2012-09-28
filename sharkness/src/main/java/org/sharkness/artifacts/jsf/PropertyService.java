package org.sharkness.artifacts.jsf;

public class PropertyService {

	private String name;
	
	private String serviceInterface;

	public PropertyService(String name, String serviceInterface) {
		setName(name);
		setServiceInterface(serviceInterface);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServiceInterface() {
		return serviceInterface;
	}

	public void setServiceInterface(String serviceInterface) {
		this.serviceInterface = serviceInterface;
	}
	
}