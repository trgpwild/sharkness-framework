<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:p="http://primefaces.org/ui">
<ui:composition template="/template/system.xhtml">
	<ui:param name="panelTitle" value="#\{i18n['model.${model?uncap_first}.panel.title']\}" />
	<ui:define name="content">
		<h:form id="rootform">
			<p align="right" style="margin: 0px;">
				<p:commandButton value="#\{i18n['model.${model?uncap_first}.table.new']\}" actionListener="#\{${model?uncap_first}Controller.prepareAddModel}"
					update="table,:dialogform:${model?uncap_first}Form" oncomplete="dialogGer${model}.show()" />
			</p><hr/>
			<p:dataTable id="table" var="${model?uncap_first}" value="#\{${model?uncap_first}Controller.listDataModel\}" selection="#\{${model?uncap_first}Controller.model}" selectionMode="single" lazy="true"
				paginator="true" rows="10" paginatorTemplate="{RowsPerPageDropdown} {FirstPageLink} {PreviousPageLink} {CurrentPageReport} {NextPageLink} {LastPageLink}">
				<p:ajax event="rowSelect" update=":dialogform:${model?uncap_first}Form" oncomplete="dialogGer${model}.show()" />
				<#list listProperties as property>
				<p:column headerText="#\{i18n['model.${model?uncap_first}.property.${property.label}']\}" id="${property.label}" sortBy="#\{${model?uncap_first}.${property.name}\}" filterBy="#\{${model?uncap_first}.${property.name}\}">  
					${property.tag}
				</p:column>
				</#list>
			</p:dataTable>
			<p:confirmDialog id="deleteConfirm" message="#\{i18n['global.confirmation.delete']\}" header="#\{i18n['global.table.delete']\}" severity="alert" widgetVar="deleteConfirm">
				<p:commandButton id="confirm" value="#\{i18n['global.yes.label']\}" update="table,:messages" oncomplete="deleteConfirm.hide()" actionListener="#\{${model?uncap_first}Controller.deleteModel\}"/>
				<p:commandButton id="decline" value="#\{i18n['global.no.label']\}" update="table,:messages" oncomplete="deleteConfirm.hide()" actionListener="#\{${model?uncap_first}Controller.refresh\}"/> 
			</p:confirmDialog>
		</h:form>
		<p:dialog header="#\{i18n['model.${model?uncap_first}.form.title']\}" widgetVar="dialogGer${model}" resizable="false" closable="false" modal="false" showEffect="fade">
			<h:form id="dialogform">
				<h:panelGrid id="${model?uncap_first}Form" columns="3" style="margin-bottom:10px">
					<#list tags as tag>
					<h:outputLabel for="${tag.property}" value="#\{i18n['model.${model?uncap_first}.property.${tag.property}']\}"/>
					${tag}
					<p:message for="${tag.property}" display="icon"/>
					</#list>
				</h:panelGrid>
				<h:panelGrid columns="3">
					<p:commandButton update=":rootform:table,:messages,:dialogform:${model?uncap_first}Form" value="#\{i18n['global.table.save']\}"
						actionListener="#\{${model?uncap_first}Controller.saveModel\}"/>
					<p:commandButton update=":rootform:table" value="#\{i18n['global.table.cancel']}" oncomplete="dialogGer${model}.hide()"
						actionListener="#\{${model?uncap_first}Controller.refresh\}"/>
					<p:commandButton update=":rootform:table" value="#\{i18n['global.table.delete']}" oncomplete="dialogGer${model}.hide();deleteConfirm.show()"/>
				</h:panelGrid>
			</h:form>
		</p:dialog>
	</ui:define>
</ui:composition>
</html>