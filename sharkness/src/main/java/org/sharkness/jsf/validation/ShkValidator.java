package org.sharkness.jsf.validation;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

public abstract class ShkValidator implements Validator {

	protected String ignoreMasks(String masked) {
		String[] strips = new String[] {
			".","-", "\\","/","*","%","&","$","#","@","!","|"
		};
		for (String s : strips) {
			masked = masked.replaceAll("\\" + s, "");
		}
		return masked;
	}
	
	protected String i18n(String message) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String messageBundleName = facesContext.getApplication().getMessageBundle();
		Locale locale = facesContext.getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(messageBundleName, locale);
		if (bundle.containsKey(message)) return bundle.getString(message); else return message;
	}
	
}