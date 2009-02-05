/**
	 
 */
package nruth.fingar.domain.specs;


import java.util.LinkedList;

import org.junit.*;
import static org.junit.Assert.*;
import nruth.fingar.domain.NamedNote;

public class NamedNoteSpec {
	/**
	 * can compare notes for equality
	 */
	@Test
	public void test_named_notes_equality(){
		assertEquals(NamedNote.A, NamedNote.A);
		assertFalse(NamedNote.A.equals(NamedNote.B));
	}
	
	/**
	 * can state the degree of a note in the scale
	 */
	@Test
	public void can_state_the_degree_of_a_note_in_the_scale(){
		assertEquals(1, NamedNote.A.getDegree());
		assertEquals(2, NamedNote.Bb.getDegree());
		assertEquals(12, NamedNote.Ab.getDegree());
	}
	
	/**
	 * should be able to resolve the next note
	 */
	@Test
	public void able_to_resolve_the_next_note(){
		assertEquals("simple progression check",NamedNote.A.next(), NamedNote.Bb);
		assertEquals("new scale octave check",NamedNote.Ab.next(), NamedNote.A);
	}
	
	/**
	 * can advance by intervals
	 */
	@Test
	public void can_advance_by_intervals(){
		assertEquals("single interval increment", NamedNote.A.advance(1), NamedNote.Bb);
		assertEquals("3 interval increment", NamedNote.Db.advance(3), NamedNote.E);
		assertEquals("wrapping around failed", NamedNote.C.advance(NamedNote.values().length + 1), NamedNote.Db);
	}
	
	/**
	 * produces correct string representation of all notes
	 */
	@Test
	public void named_note_strings_correct(){
		LinkedList<String> notes =  new LinkedList<String>();
		String[] tmp_notes = {"A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab"};
		for(String note : tmp_notes){ notes.add(note); }
		
		NamedNote start = NamedNote.A;
		NamedNote note = start;
		do{
			assertTrue("failed to pop note "+note, note.toString().equals(notes.remove()));		
			note = note.advance(1);
		} while (!note.equals(start));
		
		assertTrue("a match failed", notes.size() == 0);
	}
	
	/**
	 * interval representation of octave length and looping is correct
	 */
	@Test
	public void intervals_in_octave(){
		assertEquals("number of notes named", NamedNote.values().length, 12);
		
		assertEquals("octave interval increment", NamedNote.A.advance(12), NamedNote.A);
		assertEquals("octave + single interval increment", NamedNote.A.advance(13), NamedNote.Bb);
	}
}
