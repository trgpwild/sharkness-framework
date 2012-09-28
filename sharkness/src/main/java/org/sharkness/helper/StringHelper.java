package org.sharkness.helper;

import java.util.LinkedList;
import java.util.List;

public class StringHelper {

	public static List<String> splitCamelCaseString(String s) {
		
		char[] cArray = s.toCharArray();

		LinkedList<Integer> al = new LinkedList<Integer>();
		al.add(0);

		for (int i = 0; i < cArray.length; i++) {
			char c = cArray[i];
			if (c >= 65 && c <= 90) {
				al.add(i);
			}
		}

		LinkedList<String> strl = new LinkedList<String>();

		if (al.size() == 1) {
			strl.add(s);
			return strl;
		}

		for (int k = 0; k < al.size(); k++) {
			if (k == 0) {
				strl = addInList(strl, s.substring(0, al.get(k + 1)));
			} else if (k == al.size() - 1) {
				strl = addInList(strl, s.substring(al.get(k), s.length()));
			} else {
				strl = addInList(strl, s.substring(al.get(k), al.get(k + 1)));
			}
		}

		return strl;
		
	}
	
	private static LinkedList<String> addInList(LinkedList<String> strl, String s) {
		if (s != null && s.length() > 0) strl.add(s);
		return strl;		
	}
	
}