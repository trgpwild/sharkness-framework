<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:p="http://primefaces.org/ui">
<ui:composition template="/template/simple.xhtml">
	<ui:define name="content">
		<p:dialog header="#\{i18n['page.login.title']\}" visible="true" closable="false"
			draggable="false" resizable="false">
			<center>
				<h:outputText value="#\{i18n['page.login.msg.error']\}" rendered="#\{param.erro\}" style="color: darkred" escape="false"/>
				<h:outputText value="#\{i18n['page.login.msg.denied']\}" rendered="#\{param.denied\}" style="color: darkred" escape="false"/>
			</center>
			<form action="j_spring_security_check" method="post">
				<h:panelGrid columns="2" cellpadding="5">
					<h:outputLabel for="j_username" value="#\{i18n['page.login.username']\}" escape="false"/>
					<h:inputText id="j_username" required="true" />
					<h:outputLabel for="j_password" value="#\{i18n['page.login.password']\}" escape="false"/>
					<h:inputSecret id="j_password" required="true" />
					<h:commandButton value="#\{i18n['page.login.button']\}" />
				</h:panelGrid>
			</form>
		</p:dialog>
	</ui:define>
</ui:composition>
</html>