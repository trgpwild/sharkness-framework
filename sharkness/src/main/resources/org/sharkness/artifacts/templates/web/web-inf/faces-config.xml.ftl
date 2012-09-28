<?xml version="1.0" encoding="UTF-8"?>
<faces-config version="2.0" xmlns="http://java.sun.com/xml/ns/javaee"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
      http://java.sun.com/xml/ns/javaee
      http://java.sun.com/xml/ns/javaee/web-facesconfig_2_0.xsd">

	<application>
		<el-resolver>org.springframework.web.jsf.el.SpringBeanFacesELResolver</el-resolver>
		<message-bundle>ValidationMessages</message-bundle>
		<resource-bundle>
			<base-name>${i18nFilename}</base-name>
			<var>i18n</var>
		</resource-bundle>
	</application>
	<converter>
		<converter-id>simpleModelConverter</converter-id>
		<converter-class>org.sharkness.jsf.support.SimpleModelConverter</converter-class>
	</converter>
	<validator>
		<validator-id>emailValidator</validator-id>
		<validator-class>org.sharkness.jsf.validation.EmailValidator</validator-class>
	</validator>
	<validator>
		<validator-id>cpfValidator</validator-id>
		<validator-class>org.sharkness.jsf.validation.CpfValidator</validator-class>
	</validator>
	<validator>
		<validator-id>cnpjValidator</validator-id>
		<validator-class>org.sharkness.jsf.validation.CnpjValidator</validator-class>
	</validator>
	
</faces-config>