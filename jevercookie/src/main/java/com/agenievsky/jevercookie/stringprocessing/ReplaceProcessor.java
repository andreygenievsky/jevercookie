package com.agenievsky.jevercookie.stringprocessing;

import java.util.Map.Entry;
import java.util.Properties;


public class ReplaceProcessor implements StringProcessor {

	private Properties replaceMap;
	
	public void setReplaceMap(Properties replaceMap) {
		this.replaceMap = replaceMap;
	}

	@Override
	public String process(String src) {
		if (src == null)
			return null;
		
		String curStr = src;
		if (replaceMap != null) {
			for (Entry<Object, Object> curEntry : replaceMap.entrySet()) {
				curStr = curStr.replaceAll((String) curEntry.getKey(), (String)curEntry.getValue());
			}
		}
		
		return curStr;
	}

}
