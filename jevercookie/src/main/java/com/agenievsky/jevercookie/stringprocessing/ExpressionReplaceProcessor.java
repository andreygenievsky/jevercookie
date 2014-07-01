package com.agenievsky.jevercookie.stringprocessing;

import java.util.Map.Entry;
import java.util.Properties;

public class ExpressionReplaceProcessor implements StringProcessor {

	public static final String EXPRESSION_OPEN_TAG = "\\$\\{";
	public static final String EXPRESSION_CLOSE_TAG = "\\}";
	
	private ReplaceProcessor replaceProcessor;

	public void setReplaceMap(final Properties replaceMap) {
		Properties expressionReplaceMap = new Properties();
		if (replaceMap != null) {
			for (Entry<Object, Object> curEntry : replaceMap.entrySet()) {
				expressionReplaceMap.setProperty(EXPRESSION_OPEN_TAG + curEntry.getKey() + EXPRESSION_CLOSE_TAG, (String) curEntry.getValue());
			}
		}
		
		replaceProcessor = new ReplaceProcessor();
		replaceProcessor.setReplaceMap(expressionReplaceMap);
	}
	
	@Override
	public String process(String src) {
		if (replaceProcessor == null)
			return null;
		return replaceProcessor.process(src);
	}

}
