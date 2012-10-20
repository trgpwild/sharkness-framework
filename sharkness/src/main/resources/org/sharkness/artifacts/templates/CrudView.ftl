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
		<h:form prependId="false">
			<p align="right" style="margin: 0px;">
				<p:commandButton actionListener="#\{${model?uncap_first}Controller.prepareAddModel\}"
					value="#\{i18n['model.${model?uncap_first}.table.new']\}" update="table,${model?uncap_first}Form" oncomplete="dialogGer${model}.show()" />
			</p><hr/>
			<p:dataTable id="table" var="${model?uncap_first}" value="#\{${model?uncap_first}Controller.listDataModel\}"
				paginator="true" rows="10" paginatorTemplate="{RowsPerPageDropdown} {FirstPageLink} {PreviousPageLink} {CurrentPageReport} {NextPageLink} {LastPageLink}">
				<#list listProperties as property>
				<p:column headerText="#\{i18n['model.${model?uncap_first}.property.${property.label}']\}" id="${property.label}" sortBy="#\{${model?uncap_first}.${property.name}\}" filterBy="#\{${model?uncap_first}.${property.name}\}">  
					${property.tag}
				</p:column>
				</#list>
				<p:column>
					<f:facet name="header"><h:outputText value="#\{i18n['global.table.edit']\}"/></f:facet>
					<p:commandLink value="#\{i18n['global.table.edit']\}" update="${model?uncap_first}Form"
						actionListener="#\{${model?uncap_first}Controller.prepareEditModel\}"
						oncomplete="dialogGer${model}.show()" />
				</p:column>
				<p:column>
					<f:facet name="header"><h:outputText value="#\{i18n['global.table.delete']\}"/></f:facet>
					<p:commandLink actionListener="#\{${model?uncap_first}Controller.selectModel\}" onclick="deleteConfirm.show()" value="#\{i18n['global.table.delete']\}"/>
				</p:column>
			</p:dataTable>
			<p:confirmDialog id="deleteConfirm" message="#\{i18n['global.confirmation.delete']\}" header="#\{i18n['global.table.delete']\}" severity="alert" widgetVar="deleteConfirm">
				<p:commandButton id="confirm" value="#\{i18n['global.yes.label']\}" update="table,messages" oncomplete="deleteConfirm.hide()" actionListener="#\{${model?uncap_first}Controller.deleteModel\}" />
				<p:commandButton id="decline" value="#\{i18n['global.no.label']\}" onclick="deleteConfirm.hide()" type="button" /> 
			</p:confirmDialog>
		</h:form>
		<p:dialog header="#\{i18n['model.${model?uncap_first}.form.title']\}" widgetVar="dialogGer${model}" resizable="false" closable="false" modal="true" showEffect="fade">
			<h:form prependId="false">
				<h:panelGrid id="${model?uncap_first}Form" columns="3" style="margin-bottom:10px">
					<#list tags as tag>
					<h:outputLabel for="${tag.property}" value="#\{i18n['model.${model?uncap_first}.property.${tag.property}']\}"/>
					${tag}
					<p:message for="${tag.property}" display="icon"/>
					</#list>
				</h:panelGrid>
				<h:panelGrid columns="2">
					<p:commandButton update="table,messages,${model?uncap_first}Form" value="#\{i18n['global.table.save']\}"
						actionListener="#\{${model?uncap_first}Controller.saveModel\}"/>
					<p:commandButton update="table" value="#{i18n['global.table.cancel']}"
						oncomplete="dialogGer${model}.hide()"/>
				</h:panelGrid>
			</h:form>
		</p:dialog>
	</ui:define>
</ui:composition>
</html>