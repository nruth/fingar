/**
	 
 */
package nruth;

import static org.junit.Assert.*;

import org.junit.Test;

/**
	@author nicholasrutherford
	
 */
public class HelpersTest {
	
	/**
	 * Test method for {@link nruth.Helpers#between(int, int, int)}.
	 */
	@Test
	public void testBetween() {
		assertTrue("0 < 1 < 2", Helpers.between(0, 1, 2));
		assertFalse("NOT 0 < 0 < 2", Helpers.between(0, 0, 2));
		assertFalse("NOT 0 < 2 < 2", Helpers.between(0, 2, 2));
		assertFalse("NOT 0 < -1 < 2", Helpers.between(0, -1, 2));
		assertFalse("NOT 0 < 3 < 2", Helpers.between(0, 3, 2));
	}
	
	/**
	 * Test method for {@link nruth.Helpers#in_range(int, int, int)}.
	 */
	@Test
	public void testIn_range() {
		assertTrue("0 < 1 < 2", Helpers.in_range(0, 1, 2));
		assertTrue("0 < 0 < 2", Helpers.in_range(0, 0, 2));
		assertTrue("0 < 2 < 2", Helpers.in_range(0, 2, 2));
		assertFalse("NOT 0 < -1 < 2", Helpers.in_range(0, -1, 2));
		assertFalse("NOT 0 < 3 < 2", Helpers.in_range(0, 3, 2));
	}
	
}
