package com.agenievsky.jevercookie.js.html;

import com.agenievsky.jevercookie.stringprocessing.StringProcessor;

public class AutoadditionProcessor implements StringProcessor {

	private static final String HTML_OPEN_TAG = "<html>";
	private static final String HEAD_OPEN_TAG = "<head>";
	private static final String HEAD_CLOSE_TAG = "</head>";
	private static final String SCRIPT_OPEN_BLOCK = "<script type=\"text/javascript\" src=\"";
	private static final String SCRIPT_CLOSE_BLOCK = "\"></script>";

	private String jevercookieJsPath;
	
	public AutoadditionProcessor(final String jevercookieJsPath) {
		if (jevercookieJsPath != null) {
			this.jevercookieJsPath = getStringWithoutForwardedSlashes(jevercookieJsPath);
		} else {
			this.jevercookieJsPath = "";
		}
	}
	
	public String process(final String srcStr) {
		String newStr = null;
		if (srcStr != null) {
			String lowerCaseSrcStr = srcStr.toLowerCase();
			int headOpenTagIndex = lowerCaseSrcStr.indexOf(HEAD_OPEN_TAG);
			if (headOpenTagIndex != -1) {
				int afterHeadOpenTagIndex = headOpenTagIndex + HEAD_OPEN_TAG.length();
				newStr = srcStr.substring(0, afterHeadOpenTagIndex) + composeJevercookieScriptBlock() + srcStr.substring(afterHeadOpenTagIndex);
			} else {
				int htmlOpenTagIndex = lowerCaseSrcStr.indexOf(HTML_OPEN_TAG);
				if (htmlOpenTagIndex != -1) {
					int afterHtmlOpenTagIndex = htmlOpenTagIndex + HTML_OPEN_TAG.length();
					newStr = srcStr.substring(0, afterHtmlOpenTagIndex) + HEAD_OPEN_TAG + composeJevercookieScriptBlock() + HEAD_CLOSE_TAG + srcStr.substring(afterHtmlOpenTagIndex);
				} else {
					newStr = srcStr;
				}
			}
		}

		return newStr;
	}
	
	private String composeJevercookieScriptBlock() {
		return SCRIPT_OPEN_BLOCK + jevercookieJsPath + SCRIPT_CLOSE_BLOCK;
	}

	private String getStringWithoutForwardedSlashes(final String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != '/') {
				return str.substring(i);
			}
		}
		return "";
	}

}
