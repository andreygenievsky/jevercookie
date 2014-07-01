package com.agenievsky.jevercookie.js.servlet;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.agenievsky.jevercookie.stringprocessing.ExpressionReplaceProcessor;

public class JevercookieJsServlet extends HttpServlet {

	private static final long serialVersionUID = 3350251897008520599L;

	private static final String JS_PROPERTIES_PATH_PARAM = "PropertiesPath";

	private static final String JS_TEMPLATE_PATH = "/com/agenievsky/jevercookie/js/jevercookie_template.js";
	private static final String DEFAULT_JS_PROPERTIES_PATH = "/com/agenievsky/jevercookie/js/jevercookie.properties";
	
	private String jsContent;
	
	private ExpressionReplaceProcessor expressionReplaceProcessor;
	
    public void init(ServletConfig config) throws ServletException {
    	
		String jsPropertiesPath = config.getInitParameter(JS_PROPERTIES_PATH_PARAM);
		if (jsPropertiesPath == null) {
			jsPropertiesPath = DEFAULT_JS_PROPERTIES_PATH;
		}

		Properties jsProperties = null;
    	try {
			jsProperties = loadJsProperties(jsPropertiesPath);
		} catch (IOException e) {
			throw new ServletException("Cannot load properties : " + jsPropertiesPath, e);
		}
    	
    	expressionReplaceProcessor = new ExpressionReplaceProcessor();
    	expressionReplaceProcessor.setReplaceMap(jsProperties);
    	jsContent = processJsContent(loadJsContent(JS_TEMPLATE_PATH));
    }

    private Properties loadJsProperties(final String jsPropertiesPath) throws IOException {
    	Properties jsProperties = new Properties();
    	jsProperties.load(getClass().getResourceAsStream(jsPropertiesPath));
    	return jsProperties;
    }
    
    private String loadJsContent(final String jsTemplatePath) {
		Scanner scanner = new Scanner(getClass().getResourceAsStream(jsTemplatePath));
		try {
			return scanner.useDelimiter("\\A").next();
		} finally {
			scanner.close();
		}
    }
    
    private String processJsContent(final String srcContent) {
    	return expressionReplaceProcessor.process(srcContent);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/javascript");
		response.getWriter().write(jsContent);
	}

}
