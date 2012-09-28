<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:p="http://primefaces.org/ui">
	
	<ui:composition>
	
		<h:form>
		
			<p:menubar autoSubmenuDisplay="true">
			
				<p:submenu label="#\{i18n['global.manager.label']\}" icon="ui-icon ui-icon-document">
					<#list listMenuItem as item>
					
					<p:menuitem value="#\{i18n['model.${item.label?uncap_first}.panel.title']\}" url="${item.url}" />
					</#list>
					
					<p:menuitem value="#\{i18n['global.exit.label']\}" url="/j_spring_security_logout" />
					
				</p:submenu>
				
			</p:menubar>
			
		</h:form>
		
	</ui:composition>

</html>