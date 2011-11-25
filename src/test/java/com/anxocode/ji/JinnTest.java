package com.anxocode.ji;

import org.junit.Assert;
import org.junit.Test;

import com.anxocode.ji.Ji;

public class JinnTest {
	
	@Test
	public void equals() {
		Assert.assertTrue(Ji.equals(null, null));
		Assert.assertFalse(Ji.equals(null, "a"));
		Assert.assertFalse(Ji.equals("a", null));
		Assert.assertTrue(Ji.equals("a", "" + 'a'));
		Assert.assertFalse(Ji.equals("a", "b"));
	}
}
