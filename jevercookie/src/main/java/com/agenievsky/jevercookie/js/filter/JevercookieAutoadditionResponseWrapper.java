package com.agenievsky.jevercookie.js.filter;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class JevercookieAutoadditionResponseWrapper extends HttpServletResponseWrapper {

	CharArrayWriter contentWriter;

	public JevercookieAutoadditionResponseWrapper(HttpServletResponse response) {
		super(response);
		contentWriter = new CharArrayWriter();
	}

	@Override
	public PrintWriter getWriter() {
		return new PrintWriter(contentWriter);
	}

	public String getContent() {
		return contentWriter.toString();
	}

}
