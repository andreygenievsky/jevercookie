package com.agenievsky.jevercookie.js.servlet;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JevercookieJsServlet extends HttpServlet {

	private static final long serialVersionUID = 3350251897008520599L;

	private static final String JS_TEMPLATE_PATH = "/com/agenievsky/jevercookie/js/jevercookie_template.js";
	
	private byte[] jsContent;
	
    public void init(ServletConfig config) throws ServletException {
    	
		Scanner scanner = new Scanner(getClass().getResourceAsStream(JS_TEMPLATE_PATH));
		try {
			jsContent = scanner.useDelimiter("\\A").next().getBytes();
		} catch (Exception e) {
			throw new ServletException("Error during JevercookieJsServlet initialization", e);
		} finally {
			scanner.close();
		}
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/javascript");
		response.getOutputStream().write(jsContent);
	}

}
