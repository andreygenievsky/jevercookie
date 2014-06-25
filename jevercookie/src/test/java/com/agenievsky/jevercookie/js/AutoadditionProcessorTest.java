package com.agenievsky.jevercookie.js;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AutoadditionProcessorTest {

	private AutoadditionProcessor aaProcessor;
	
	@Before
	public void setUp() {
		aaProcessor = new AutoadditionProcessor();
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
	public void testNotContainsHead() {
		
	}

}
