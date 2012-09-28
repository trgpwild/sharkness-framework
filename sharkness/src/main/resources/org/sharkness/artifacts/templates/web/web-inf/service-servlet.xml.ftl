<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:tool="http://www.springframework.org/schema/tool"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:sec="http://www.springframework.org/schema/security"
	xmlns:jms="http://www.springframework.org/schema/jms"
	xmlns:jee="http://www.springframework.org/schema/jee"
	xmlns:jdbc="http://www.springframework.org/schema/jdbc"
	xmlns:util="http://www.springframework.org/schema/util"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc-3.0.xsd
		http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-2.0.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.0.xsd
		http://www.springframework.org/schema/jms http://www.springframework.org/schema/jms/spring-jms-2.5.xsd
		http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security-2.0.1.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd
		http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-2.0.xsd
		http://www.springframework.org/schema/tool http://www.springframework.org/schema/tool/spring-tool-2.0.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-2.0.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd">

	<bean name="/AuthenticationService" class="org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter">
		<property name="service" ref="authRemoteService"/>
		<property name="serviceInterface" value="org.sharkness.remoting.service.AuthRemoteService"/>
	</bean>
	<#list listService as service>
	
	<bean name="/${service.name}Service" class="org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter">
		<property name="service" ref="${service.name?uncap_first}Service"/>
		<property name="serviceInterface" value="${service.serviceInterface}"/>
	</bean>
	</#list>

</beans>