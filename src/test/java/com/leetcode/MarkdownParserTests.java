package com.leetcode;

import static org.junit.Assert.*;
import org.junit.Test;

public class MarkdownParserTests {

	@Test
	  public void basicValidCases() {
	    String expected = "<h2>Lost In Space</h2>";
	    String actual = Challenge.markdownParser("##   Lost In Space");
	    assertEquals(expected, actual);
	    String expected1 = "<h2>smaller header</h2>";
	    String actual1 = Challenge.markdownParser("## smaller header");
	    assertEquals(expected1, actual1);
	  }
	  @Test
	  public void basicInvalidCases() {
	    String expected = "#Invalid";
	    String actual = Challenge.markdownParser("#Invalid");
	    assertEquals(expected, actual);
	  }
}
