package org.sharkness.jsf.support;

import java.util.Locale;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.SortOrder;
import org.sharkness.business.entity.Model;
import org.sharkness.helper.DateTimeHelper;
import org.sharkness.helper.I18n;
import org.sharkness.web.component.ControllerComponent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

@SuppressWarnings({"serial","rawtypes"})
public abstract class SimpleController implements ControllerComponent {
	
	public String getLocale() {
		return DateTimeHelper.getDateFormatPattern(getLocaleContext());
	}
	
	protected Locale getLocaleContext() {
		Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
		return locale;
	}
	
	protected void message(I18n i18n) {
		message(i18n, FacesMessage.SEVERITY_INFO);
	}
	
	protected void message(I18n i18n, Severity severity) {
		message(i18n.toString(), severity);
	}
	
	protected void message(String summary) {
		message(summary, FacesMessage.SEVERITY_INFO);
	}
	
	protected void message(String summary, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, null));
	}
	
	protected org.sharkness.jsf.support.SortOrder convertSortOrder(SortOrder sortOrder) {
		if (sortOrder.toString().equalsIgnoreCase(org.sharkness.jsf.support.SortOrder.ASCENDING.toString())) {
			return org.sharkness.jsf.support.SortOrder.ASCENDING;
		} else if (sortOrder.toString().equalsIgnoreCase(org.sharkness.jsf.support.SortOrder.DESCENDING.toString())) {
			return org.sharkness.jsf.support.SortOrder.DESCENDING;
		}
		return org.sharkness.jsf.support.SortOrder.UNSORTED;
	}
	
	protected Model getModelFromAjaxEvent(AjaxBehaviorEvent event) {
		Object objSelect = event.getSource();
		if (objSelect instanceof ValueHolder) {
			ValueHolder select = (ValueHolder) objSelect;
			Object objModel = select.getValue();
			if (objModel instanceof Model) {
				return (Model) objModel;
			}
		}
		return null;
	}
	
	public void refresh() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse();
	}
	
	protected ApplicationContext ctx() {
		return ContextLoader.getCurrentWebApplicationContext();
	}
	
}