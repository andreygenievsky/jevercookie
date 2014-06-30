package com.agenievsky.jevercookie.js.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.agenievsky.jevercookie.js.html.AutoadditionProcessor;

public class JevercookieAutoadditionFilter implements Filter {

	private AutoadditionProcessor autoadditionProcessor;
	
	public void setAutoadditionProcessor(AutoadditionProcessor autoadditionProcessor) {
		this.autoadditionProcessor = autoadditionProcessor;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String jevercookieJsPath = filterConfig.getInitParameter("JevercookieJsPath");
		if (jevercookieJsPath == null) {
			throw new ServletException("Cannot find \"JevercookieJsPath\" parameter");
		}
		autoadditionProcessor = new AutoadditionProcessor(jevercookieJsPath);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {

		OutputStreamResponseWrapper responseWrapper = new OutputStreamResponseWrapper((HttpServletResponse) response);
		chain.doFilter(request, responseWrapper);
		byte[] newContent = autoadditionProcessor.process(new String(responseWrapper.getContent())).getBytes();
		response.setContentLength(newContent.length);
		response.getOutputStream().write(newContent);
	}

}
