package nruth.fingar.domain.specs;

import static org.junit.Assert.*;

import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;

import org.junit.*;


import static org.junit.Assert.*;
import static nruth.fingar.domain.guitar.Guitar.GuitarString; 
import static nruth.fingar.domain.music.NamedNote.*;

public class GuitarStringTest {
	
	/**
		last modified: 11 Nov 2008
		@throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testGetOpenStringNote() {
		assertEquals(GuitarString.LOW_E.open_string_note() , new Note(E,1));
		assertEquals(GuitarString.A.open_string_note() , new Note(A,1));
		assertEquals(GuitarString.D.open_string_note() , new Note(D,1));
		assertEquals(GuitarString.G.open_string_note() , new Note(G,2));
		assertEquals(GuitarString.B.open_string_note() , new Note(B,2));
		assertEquals(GuitarString.HIGH_E.open_string_note() , new Note(E,3));
	}
	
	@Test
	public void testGet_relative_fret_on_previous_string() {
		assertEquals(GuitarString.LOW_E.relative_fret_on_previous_string(), 0);
		assertEquals(GuitarString.HIGH_E.relative_fret_on_previous_string(), 5);
		assertEquals(GuitarString.A.relative_fret_on_previous_string(), 5);
		assertEquals(GuitarString.D.relative_fret_on_previous_string(), 5);
		assertEquals(GuitarString.G.relative_fret_on_previous_string(), 4);
		assertEquals(GuitarString.B.relative_fret_on_previous_string(), 5);
	}
	
	@Test
	public void testGet_relative_fret_gap() {
		assertEquals(GuitarString.LOW_E.get_relative_fret_gap(GuitarString.A), 5);
		assertEquals(GuitarString.A.get_relative_fret_gap(GuitarString.LOW_E), -5);
		
		assertEquals(GuitarString.HIGH_E.get_relative_fret_gap(GuitarString.LOW_E), -24);
		assertEquals(GuitarString.LOW_E.get_relative_fret_gap(GuitarString.HIGH_E), 24);
		
		assertEquals(GuitarString.A.get_relative_fret_gap(GuitarString.D), +5);
		assertEquals(GuitarString.A.get_relative_fret_gap(GuitarString.G), +9);
		assertEquals(GuitarString.A.get_relative_fret_gap(GuitarString.B), +14);
		assertEquals(GuitarString.A.get_relative_fret_gap(GuitarString.HIGH_E), +19);	
	}
	
	@Test
	public void testGet_relative_fret_to_bottom_e() {
		assertEquals(GuitarString.LOW_E.get_relative_fret_to_bottom_e(), 0);
		assertEquals(GuitarString.HIGH_E.get_relative_fret_to_bottom_e(), 24);
		assertEquals(GuitarString.A.get_relative_fret_to_bottom_e(), 5);
		assertEquals(GuitarString.D.get_relative_fret_to_bottom_e(), 10);
		assertEquals(GuitarString.G.get_relative_fret_to_bottom_e(), 14);
		assertEquals(GuitarString.B.get_relative_fret_to_bottom_e(), 19);
	}
	
	/**
	 * equality and hashcodes
	 * done after-the-fact and implementation is via enumeration so should pass trivially
	 */
	@Test
	public void equality_and_hashcodes(){
		assertEquals(GuitarString.A, GuitarString.A);
		assertFalse(GuitarString.A.equals(GuitarString.B));
		assertFalse(GuitarString.B.equals(GuitarString.A));
		
		assertEquals(GuitarString.A.hashCode(), GuitarString.A.hashCode());
		assertFalse(GuitarString.A.hashCode() == GuitarString.B.hashCode());
	}
	
}
