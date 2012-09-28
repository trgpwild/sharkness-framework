package org.sharkness.remoting.factory;

import org.sharkness.remoting.service.AuthRemoteService;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor;
import org.springframework.security.remoting.httpinvoker.AuthenticationSimpleHttpInvokerRequestExecutor;

@SuppressWarnings("unchecked")
public class HttpInvokerFactory {

	private String baseUrl;
	
	private HttpInvokerRequestExecutor executor;
	
	public HttpInvokerFactory(String baseUrl) {
		this.executor = new AuthenticationSimpleHttpInvokerRequestExecutor();
		this.baseUrl = baseUrl;
	}
	
	public <Service> Service get(Class<Service> serviceInterface) {
		String serviceName = serviceInterface.getSimpleName();
		if (serviceInterface.getSimpleName().equals(AuthRemoteService.class
			.getSimpleName())) serviceName = "AuthenticationService";
		HttpInvokerProxyFactoryBean factory = new HttpInvokerProxyFactoryBean();
		factory.setHttpInvokerRequestExecutor(executor);
		factory.setServiceInterface(serviceInterface);
		factory.setServiceUrl(
			new StringBuilder(baseUrl).append("/").append(serviceName).toString()
		);
		factory.afterPropertiesSet();
		return (Service) factory.getObject();
	}
	
}