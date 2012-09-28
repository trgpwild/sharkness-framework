package org.sharkness.artifacts.generate;

import java.util.List;
import java.util.Properties;

public abstract class BundleGenerator {

	protected static List<String> ready;
	
	protected static Properties updateProperty(String key, String valueDefault, Properties update, Properties old) {
		ready.add(key);
		if (old.containsKey(key)) {
			update.put(key, old.getProperty(key));
		} else {
			update.put(key, valueDefault);
		}
		return update;
	}
	
}