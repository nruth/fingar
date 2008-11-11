/**
	 
 */
package nruth.gitfga.tests;

import static org.junit.Assert.*;

import org.junit.*;

import nruth.gitfga.Notes.Note;
import static org.junit.Assert.*;
import static nruth.gitfga.Position.GuitarString.*;
/**
	@author nicholasrutherford
	
 */
public class GuitarStringTest {
	
	/**
		last modified: 11 Nov 2008
		@throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}
	
	/**
	 * Test method for {@link nruth.gitfga.Position.GuitarString#getOpenStringNote()}.
	 */
	@Test
	public void testGetOpenStringNote() {
		assertSame(LOW_E.getOpenStringNote(), Note.E);
		assertSame(HIGH_E.getOpenStringNote(), Note.E);
		assertSame(A.getOpenStringNote(), Note.A);
		assertSame(D.getOpenStringNote(), Note.D);
		assertSame(G.getOpenStringNote(), Note.G);
		assertSame(B.getOpenStringNote(), Note.B);
	}
	
	/**
	 * Test method for {@link nruth.gitfga.Position.GuitarString#get_relative_fret_on_previous_string()}.
	 */
	@Test
	public void testGet_relative_fret_on_previous_string() {
		assertEquals(LOW_E.get_relative_fret_on_previous_string(), 0);
		assertEquals(HIGH_E.get_relative_fret_on_previous_string(), 5);
		assertEquals(A.get_relative_fret_on_previous_string(), 5);
		assertEquals(D.get_relative_fret_on_previous_string(), 5);
		assertEquals(G.get_relative_fret_on_previous_string(), 4);
		assertEquals(B.get_relative_fret_on_previous_string(), 5);
	}
	
	/**
	 * Test method for {@link nruth.gitfga.Position.GuitarString#get_relative_fret_gap(nruth.gitfga.Position.GuitarString)}.
	 */
	@Test
	public void testGet_relative_fret_gap() {
		assertEquals(LOW_E.get_relative_fret_gap(A), 5);
		assertEquals(A.get_relative_fret_gap(LOW_E), -5);
		
		assertEquals(HIGH_E.get_relative_fret_gap(LOW_E), -24);
		assertEquals(LOW_E.get_relative_fret_gap(HIGH_E), 24);
		
		assertEquals(A.get_relative_fret_gap(D), +5);
		assertEquals(A.get_relative_fret_gap(G), +9);
		assertEquals(A.get_relative_fret_gap(B), +14);
		assertEquals(A.get_relative_fret_gap(HIGH_E), +19);	
	}
	
	/**
	 * Test method for {@link nruth.gitfga.Position.GuitarString#get_relative_fret_to_bottom_e()}.
	 */
	@Test
	public void testGet_relative_fret_to_bottom_e() {
		assertEquals(LOW_E.get_relative_fret_to_bottom_e(), 0);
		assertEquals(HIGH_E.get_relative_fret_to_bottom_e(), 24);
		assertEquals(A.get_relative_fret_to_bottom_e(), 5);
		assertEquals(D.get_relative_fret_to_bottom_e(), 10);
		assertEquals(G.get_relative_fret_to_bottom_e(), 14);
		assertEquals(B.get_relative_fret_to_bottom_e(), 19);
	}
	
}
