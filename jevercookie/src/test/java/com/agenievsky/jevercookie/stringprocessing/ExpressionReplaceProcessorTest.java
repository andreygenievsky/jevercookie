package com.agenievsky.jevercookie.stringprocessing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class ExpressionReplaceProcessorTest {

	private ExpressionReplaceProcessor processor;
	
	@Before
	public void setUp() {
		processor = new ExpressionReplaceProcessor();
	}
	
	@Test
	public void testNullReplaceMapNullSrc() {
		processor.setReplaceMap(null);
		String res = processor.process(null);
		assertNull(res);
	}
	
	@Test
	public void testNullReplaceMap() {
		String src = "src string";
		processor.setReplaceMap(null);
		String res = processor.process(src);
		assertEquals(src, res);
	}
	
	@Test
	public void testNullSrcStr() {
		String srcStr = null;
		Properties replaceMap = new Properties();
		replaceMap.put("key", "value");
		processor.setReplaceMap(replaceMap);
		String res = processor.process(srcStr);
		assertNull(res);
	}
	
	@Test
	public void testEmptyReplaceMapEmptySrcStr() {
		String srcStr = "";
		Properties replaceMap = new Properties();
		processor.setReplaceMap(replaceMap);
		String resStr = processor.process(srcStr);
		assertEquals(srcStr,  resStr);
	}
	
	@Test
	public void testEmptyReplaceMap() {
		String srcStr = "src str";
		Properties replaceMap = new Properties();
		processor.setReplaceMap(replaceMap);
		String resStr = processor.process(srcStr);
		assertEquals(srcStr,  resStr);
	}
	
	@Test
	public void testEmptyStrStr() {
		String srcStr = "";
		Properties replaceMap = new Properties();
		replaceMap.setProperty("key", "value");
		processor.setReplaceMap(replaceMap);
		String resStr = processor.process(srcStr);
		assertEquals(srcStr,  resStr);
	}

	@Test
	public void test0Replacements() {
		String srcStr = "src str";
		Properties replaceMap = new Properties();
		replaceMap.setProperty("key", "value");
		processor.setReplaceMap(replaceMap);
		String resStr = processor.process(srcStr);
		assertEquals(srcStr,  resStr);
	}
	
	@Test
	public void test1Replacement() {
		String srcStr = "src ${key} str";
		Properties replaceMap = new Properties();
		replaceMap.setProperty("key", "value");
		processor.setReplaceMap(replaceMap);
		String resStr = processor.process(srcStr);
		assertEquals("src value str",  resStr);
	}
	
	@Test
	public void test2Replacements() {
		String srcStr = "src ${key1} str ${key2}";
		Properties replaceMap = new Properties();
		replaceMap.setProperty("key1", "value1");
		replaceMap.setProperty("key2", "value2");
		processor.setReplaceMap(replaceMap);
		String resStr = processor.process(srcStr);
		assertEquals("src value1 str value2",  resStr);
	}
	
	@Test
	public void test2SameReplacements() {
		String srcStr = "src ${key1} str ${key1}";
		Properties replaceMap = new Properties();
		replaceMap.setProperty("key1", "value1");
		processor.setReplaceMap(replaceMap);
		String resStr = processor.process(srcStr);
		assertEquals("src value1 str value1",  resStr);
	}
	
}
