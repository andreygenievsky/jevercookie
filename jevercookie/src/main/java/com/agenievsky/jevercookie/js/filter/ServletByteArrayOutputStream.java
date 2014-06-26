package com.agenievsky.jevercookie.js.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

public class ServletByteArrayOutputStream extends ServletOutputStream {

	private ByteArrayOutputStream out = new ByteArrayOutputStream();

	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}

	public byte[] getBytes() {
		return out.toByteArray();
	}

}
