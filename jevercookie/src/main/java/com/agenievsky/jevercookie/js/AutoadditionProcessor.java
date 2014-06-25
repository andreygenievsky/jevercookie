package com.agenievsky.jevercookie.js;

import java.util.Scanner;

public class AutoadditionProcessor {

	private static final String HEAD_OPEN_TAG = "<head>";
	private static final String SCRIPT_OPEN_TAG = "<script>";
	private static final String SCRIPT_CLOSE_TAG = "</script>";

	public String process(final String src) {
		
		String newStr = null;
		
		Scanner scanner = new Scanner(src);
		try {
			//TODO change the regexp
			newStr = scanner.useDelimiter("\\A").next().replaceAll("(?i)" + HEAD_OPEN_TAG, HEAD_OPEN_TAG + SCRIPT_OPEN_TAG + Consts.STANDARD_JS_NAME + SCRIPT_CLOSE_TAG);
		} catch (Exception e) {
			newStr = src;
		} finally {
			scanner.close();
		}
		return newStr;
	}

}
