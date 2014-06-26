package com.agenievsky.jevercookie.js;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AutoadditionProcessorTest {

	private AutoadditionProcessor aaProcessor;
	
	@Before
	public void setUp() {
		aaProcessor = new AutoadditionProcessor("jevercookie.js");
	}
	
	@Test
	public void testNull() {
		String srcStr = null;
		String newStr = aaProcessor.process(srcStr);
		assertEquals(srcStr, newStr);
	}
	
	@Test
	public void testEmpty() {
		String srcStr = "";
		String newStr = aaProcessor.process(srcStr);
		assertEquals(srcStr, newStr);
	}

	@Test
	public void testNotContainsHtmlNotContainsHead() {
		String srcStr = "<h1>sdwed</h1><p>ok</p>";
		String newStr = aaProcessor.process(srcStr);
		assertEquals(srcStr, newStr);
	}

	@Test
	public void testNotContainsHtmlContainsHead() {
		String srcStr = "<head></head><body><h1>sdwed</h1><p>ok</p></body>";
		String newStr = aaProcessor.process(srcStr);
		assertEquals("<head><script type=\"text/javascript\" src=\"jevercookie.js\"></script></head><body><h1>sdwed</h1><p>ok</p></body>", newStr);
	}

	@Test
	public void testContainsHtmlNotContainsHead() {
		String srcStr = "<html><body><h1>sdwed</h1><p>ok</p></body></html>";
		String newStr = aaProcessor.process(srcStr);
		assertEquals("<html><head><script type=\"text/javascript\" src=\"jevercookie.js\"></script></head><body><h1>sdwed</h1><p>ok</p></body></html>", newStr);
	}

	@Test
	public void testContainsHtmlContainsHead() {
		String srcStr = "<html><head></head><body><h1>sdwed</h1><p>ok</p></body></html>";
		String newStr = aaProcessor.process(srcStr);
		assertEquals("<html><head><script type=\"text/javascript\" src=\"jevercookie.js\"></script></head><body><h1>sdwed</h1><p>ok</p></body></html>", newStr);
	}

	@Test
	public void testUpperCase() {
		String srcStr = "<HTML><HEAD></HEAD><BODY><H1>sdwed</H1><P>ok</P></BODY></HTML>";
		String newStr = aaProcessor.process(srcStr);
		assertEquals("<HTML><HEAD><script type=\"text/javascript\" src=\"jevercookie.js\"></script></HEAD><BODY><H1>sdwed</H1><P>ok</P></BODY></HTML>", newStr);
	}

	@Test
	public void testMixedCases() {
		String srcStr = "<HTml><hEaD></HeAD><BODY><h1>sdwed</H1><P>ok</p></body></HTML>";
		String newStr = aaProcessor.process(srcStr);
		assertEquals("<HTml><hEaD><script type=\"text/javascript\" src=\"jevercookie.js\"></script></HeAD><BODY><h1>sdwed</H1><P>ok</p></body></HTML>", newStr);
	}

	@Test
	public void testJsPathNull() {
		aaProcessor = new AutoadditionProcessor(null);
		String srcStr = "<html><head></head><body><h1>sdwed</h1><p>ok</p></body></html>";
		String newStr = aaProcessor.process(srcStr);
		assertEquals("<html><head><script type=\"text/javascript\" src=\"\"></script></head><body><h1>sdwed</h1><p>ok</p></body></html>", newStr);
	}

	@Test
	public void testJsPathEmpty() {
		aaProcessor = new AutoadditionProcessor("");
		String srcStr = "<html><head></head><body><h1>sdwed</h1><p>ok</p></body></html>";
		String newStr = aaProcessor.process(srcStr);
		assertEquals("<html><head><script type=\"text/javascript\" src=\"\"></script></head><body><h1>sdwed</h1><p>ok</p></body></html>", newStr);
	}

	@Test
	public void testJsPathWith1ForwardSlash() {
		aaProcessor = new AutoadditionProcessor("/jevercookie.js");
		String srcStr = "<html><head></head><body><h1>sdwed</h1><p>ok</p></body></html>";
		String newStr = aaProcessor.process(srcStr);
		assertEquals("<html><head><script type=\"text/javascript\" src=\"jevercookie.js\"></script></head><body><h1>sdwed</h1><p>ok</p></body></html>", newStr);
	}

	@Test
	public void testJsPathWithManyForwardSlashes() {
		aaProcessor = new AutoadditionProcessor("/////jevercookie.js");
		String srcStr = "<html><head></head><body><h1>sdwed</h1><p>ok</p></body></html>";
		String newStr = aaProcessor.process(srcStr);
		assertEquals("<html><head><script type=\"text/javascript\" src=\"jevercookie.js\"></script></head><body><h1>sdwed</h1><p>ok</p></body></html>", newStr);
	}

	@Test
	public void testJsPathAllSlashes() {
		aaProcessor = new AutoadditionProcessor("/////");
		String srcStr = "<html><head></head><body><h1>sdwed</h1><p>ok</p></body></html>";
		String newStr = aaProcessor.process(srcStr);
		assertEquals("<html><head><script type=\"text/javascript\" src=\"\"></script></head><body><h1>sdwed</h1><p>ok</p></body></html>", newStr);
	}


}
