<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:p="http://primefaces.org/ui">
<h:head>
	<link type="text/css" rel="stylesheet" href="#\{request.contextPath\}/css/main.css" />
	<link rel="shortcut icon" href="#\{request.contextPath\}/img/sharkness.ico" />
	<title>${sharknessApplicationName}</title>
</h:head>
<h:body>
	<p:growl id="messages" />
	<ui:insert name="content"/>
</h:body>
</html>