package com.agenievsky.jevercookie.js.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class OutputStreamResponseWrapper extends HttpServletResponseWrapper {

	ServletByteArrayOutputStream contentOutputStream;

	public OutputStreamResponseWrapper(HttpServletResponse response) {
		super(response);
		contentOutputStream = new ServletByteArrayOutputStream();
	}

	@Override
	public ServletOutputStream getOutputStream() {
		return contentOutputStream;
	}
	
	public byte[] getContent() {
		return contentOutputStream.getBytes();
	}

}
