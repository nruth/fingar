/**
	 
 */
package nruth;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

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
	
	@Test
	public void content_equality_same_list(){
		List<String> A = new LinkedList<String>();
		A.addAll(Arrays.asList("1","2","3","4","5"));
		assertTrue(Helpers.content_equality(A,A));
	
		List<String> B = new LinkedList<String>();
		B.addAll(A);
		assertTrue("same contents different list objects",Helpers.content_equality(A,B));
		assertTrue("same contents different list objects",Helpers.content_equality(B,A));
	}
	
	@Test
	public void content_equality_same_list_reversed_list(){
		List<String> A = new LinkedList<String>();
		A.addAll(Arrays.asList("1","2","3","4","5"));
		List<String> B = new LinkedList<String>();
		B.addAll(A);
		Collections.reverse(B);
		assertTrue("reversed contents different list objects",Helpers.content_equality(A,B));
		assertTrue("reversed contents different list objects",Helpers.content_equality(B,A));
	}
	
	@Test
	public void content_equality_same_elements_different_counts(){
		List<String> A = new LinkedList<String>();
		A.addAll(Arrays.asList("1","2","3","4","5"));
		
		List<String> B = new LinkedList<String>();
		B.addAll(A);
		Collections.reverse(B);
		assertTrue(Helpers.content_equality(A, B));
		assertTrue(Helpers.content_equality(B, A));
		B.add("3");
		assertFalse(Helpers.content_equality(A, B));
		assertFalse(Helpers.content_equality(B, A));
	}
	
	@Test
	public void content_equality_different_elements(){
		List<String> A = new LinkedList<String>();
		A.addAll(Arrays.asList("1","2","3","4","5"));
		
		List<String> B = new LinkedList<String>();
		B.addAll(Arrays.asList("3"));
		assertFalse(Helpers.content_equality(A, B));
		assertFalse(Helpers.content_equality(B, A));
	}
}
