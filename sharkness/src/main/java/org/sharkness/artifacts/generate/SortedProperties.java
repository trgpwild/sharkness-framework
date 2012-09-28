package org.sharkness.artifacts.generate;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SortedProperties extends Properties {

	private static final long serialVersionUID = 1L;

	public synchronized Enumeration keys() {
		
		Enumeration keysEnum = super.keys();
		
		Vector keyList = new Vector();
		
		while (keysEnum.hasMoreElements()) {
			keyList.add(keysEnum.nextElement());
		}
		
		Collections.sort(keyList);
		
		return keyList.elements();
		
	}

}