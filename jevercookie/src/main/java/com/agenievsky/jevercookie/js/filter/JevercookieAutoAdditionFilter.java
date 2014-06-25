package com.agenievsky.jevercookie.js.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.agenievsky.jevercookie.js.AutoadditionProcessor;

public class JevercookieAutoAdditionFilter implements Filter {

	AutoadditionProcessor autoadditionProcessor;
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		autoadditionProcessor = new AutoadditionProcessor();
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {

		JevercookieAutoadditionResponseWrapper responseWrapper = new JevercookieAutoadditionResponseWrapper((HttpServletResponse) response);
		chain.doFilter(request, responseWrapper);
		response.getWriter().write(autoadditionProcessor.process(responseWrapper.getContent()));
	}

}
