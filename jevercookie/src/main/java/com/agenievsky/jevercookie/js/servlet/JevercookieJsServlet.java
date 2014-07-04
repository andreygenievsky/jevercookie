package com.agenievsky.jevercookie.js.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.Map.Entry;

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
		Properties jsProperties = null;
		try {
			jsProperties = loadProperties(DEFAULT_JS_PROPERTIES_PATH);
			if (jsPropertiesPath != null) {
				Properties customizedProperties = loadProperties(jsPropertiesPath);
				jsProperties.putAll(customizedProperties);
			}
		} catch (IOException e) {
			throw new ServletException("Cannot load default properties", e);
		}
    	
    	expressionReplaceProcessor = new ExpressionReplaceProcessor();
    	expressionReplaceProcessor.setReplaceMap(jsProperties);
    	jsContent = processJsContent(loadJsContent(JS_TEMPLATE_PATH));
    }

    private Properties loadProperties(final String jsPropertiesPath) throws IOException {
    	Properties properties = new Properties();
    	InputStream in = getClass().getResourceAsStream(jsPropertiesPath);
    	try {
    		properties.load(in);
    		return properties;
    	} finally {
    		in.close();
    	}
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
