package com.agenievsky.jevercookie.js.filter;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.agenievsky.jevercookie.js.AutoadditionProcessor;

public class JevercookieAutoadditionFilterTest {

	private String originalResponseContent;
	private String newResponseContent;

	private AutoadditionProcessor aaProc;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletByteArrayOutputStream responseContentOutputStream;
	private FilterChain chain;

	@Before
	public void setUp() throws IOException, ServletException {

		aaProc = mock(AutoadditionProcessor.class);
		doAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				return newResponseContent;
			}
			
		}).when(aaProc).process(any(String.class));
		
		request = mock(HttpServletRequest.class);

		responseContentOutputStream = new ServletByteArrayOutputStream();
		response = mock(HttpServletResponse.class);
		when(response.getOutputStream()).thenReturn(responseContentOutputStream);
	
		chain = mock(FilterChain.class);
		doAnswer(new Answer<Void>() {
					@Override
					public Void answer(InvocationOnMock invocation) throws Throwable {
						HttpServletResponse response = (HttpServletResponse) invocation.getArguments()[1];
						response.getOutputStream().write(originalResponseContent.getBytes());
						return null;
					}}).when(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));
	}
	
	@Test
	public void test() throws IOException, ServletException { 

		originalResponseContent = "originalResponseContent";
		newResponseContent = "newResponseContent";

		JevercookieAutoadditionFilter filter = new JevercookieAutoadditionFilter();
		filter.setAutoadditionProcessor(aaProc);
		filter.doFilter(request, response, chain);
		
		assertEquals(newResponseContent, new String(responseContentOutputStream.getBytes()));
		
	}
	
}
