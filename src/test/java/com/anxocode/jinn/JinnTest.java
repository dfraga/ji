package com.anxocode.jinn;

import org.junit.Assert;
import org.junit.Test;

public class JinnTest {
	
	@Test
	public void equals() {
		Assert.assertTrue(Jinn.equals(null, null));
		Assert.assertFalse(Jinn.equals(null, "a"));
		Assert.assertFalse(Jinn.equals("a", null));
		Assert.assertTrue(Jinn.equals("a", "" + 'a'));
		Assert.assertFalse(Jinn.equals("a", "b"));
	}
}
