package org.sharkness.helper;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.sharkness.business.factory.PropertiesFactory;

public class I18n {

	private StringBuilder str;
	
	private String bundle;
	
	public static I18n get(String s) {
		try {
			return new I18n(s);
		} catch (Exception e) {
			return new I18n(s, "messages");
		}
	}
	
	private I18n(String s) throws Exception {
		this(s, PropertiesFactory.getApplicationI18nFilename().replaceAll("\\/", "."));
	}

	public I18n(String s, String bundle) {
		this.bundle = bundle;
		this.str = new StringBuilder(i18n(bundle,s));
	}
	
	public I18n add(String s) {
		str.append(i18n(bundle,s));
		return this;
	}
	
	@Override
	public String toString() {
		return str.toString();
	}
	
	public static String i18n(String resourceBundleName, String resourceBundleKey) {
		try {
			Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
			ResourceBundle bundle = ResourceBundle.getBundle(resourceBundleName, locale, getCurrentLoader(resourceBundleName));
			return bundle.getString(resourceBundleKey);
		} catch (Exception e) {
			return resourceBundleKey;
		}
	}
	
	private static ClassLoader getCurrentLoader(Object fallbackClass) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) loader = fallbackClass.getClass().getClassLoader();
		return loader;
	}

}