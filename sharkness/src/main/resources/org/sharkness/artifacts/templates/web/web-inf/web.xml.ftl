<?xml version="1.0" encoding="UTF-8"?>
<web-app version="2.5" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">
<#if jsfConfigEnabled == 1>
	<servlet>
		<servlet-name>Faces Servlet</servlet-name>
		<servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>
</#if>
	<filter>
		<filter-name>openSessionInViewFilter</filter-name>
		<filter-class>org.springframework.orm.hibernate4.support.OpenSessionInViewFilter</filter-class>
		<init-param>
			<param-name>sessionFactoryBeanName</param-name>
			<param-value>sessionFactory</param-value>
		</init-param>
	</filter>
<#if jsfConfigEnabled == 1>
	<context-param>
		<param-name>javax.faces.STATE_SAVING_METHOD</param-name>
		<param-value>client</param-value>
	</context-param>
	<servlet-mapping>
		<servlet-name>Faces Servlet</servlet-name>
		<url-pattern>*.jsf</url-pattern>
	</servlet-mapping>
</#if>
	<session-config>
		<session-timeout>30</session-timeout>
	</session-config>
	<welcome-file-list>
		<welcome-file>index.html</welcome-file>
	</welcome-file-list>
<#if jsfConfigEnabled == 1>
	<context-param>   
	  <param-name>primefaces.THEME</param-name>
	  <param-value>aristo</param-value> 
	</context-param>
</#if>
	<filter>
		<filter-name>springSecurityFilterChain</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
	</filter>
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	<listener>
		<listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
	</listener>
<#if beanRemoteEnabled == 1>
	<servlet>
		<servlet-name>service</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>service</servlet-name>
		<url-pattern>${serviceFolder}/*</url-pattern>
	</servlet-mapping>
</#if>
	<filter-mapping>
		<filter-name>springSecurityFilterChain</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
</web-app>