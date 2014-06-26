package com.agenievsky.jevercookie.js;

public class AutoadditionProcessor {

	private static final String HTML_OPEN_TAG = "<html>";
	private static final String HEAD_OPEN_TAG = "<head>";
	private static final String HEAD_CLOSE_TAG = "</head>";
	private static final String JEVERCOOKIE_SCRIPT_BLOCK = "<script type=\"text/javascript\" src=\"" + Consts.STANDARD_JS_NAME + "\"></script>";

	public String process(final String srcStr) {
		String newStr = null;
		if (srcStr != null) {
			String lowerCaseSrcStr = srcStr.toLowerCase();
			int headOpenTagIndex = lowerCaseSrcStr.indexOf(HEAD_OPEN_TAG);
			if (headOpenTagIndex != -1) {
				int afterHeadOpenTagIndex = headOpenTagIndex + HEAD_OPEN_TAG.length();
				newStr = srcStr.substring(0, afterHeadOpenTagIndex) + JEVERCOOKIE_SCRIPT_BLOCK + srcStr.substring(afterHeadOpenTagIndex);
			} else {
				int htmlOpenTagIndex = lowerCaseSrcStr.indexOf(HTML_OPEN_TAG);
				if (htmlOpenTagIndex != -1) {
					int afterHtmlOpenTagIndex = htmlOpenTagIndex + HTML_OPEN_TAG.length();
					newStr = srcStr.substring(0, afterHtmlOpenTagIndex) + HEAD_OPEN_TAG + JEVERCOOKIE_SCRIPT_BLOCK + HEAD_CLOSE_TAG + srcStr.substring(afterHtmlOpenTagIndex);
				} else {
					newStr = srcStr;
				}
			}
		}

		return newStr;
	}

}
