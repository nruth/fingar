/**
	 
 */
package nruth.fingar.domain.tests;


import org.junit.*;
import static org.junit.Assert.*;
import nruth.fingar.domain.NamedNote;
/**
	@author nicholasrutherford
	from spec: 
		chromatic scale
		an ordered list of the named notes
			named notes: {A, Bb, B, C, Db, D, Eb, E, F, Gb, G, Ab}
			For the A chromatic scale, this is 12 semitones (all notes before repetition of A)	
		* of use as an object concept (for enumeration of note names)
		* should be able to resolve the next note
		* can compare notes for equality and relative position
		* can advance by intervals
		* can state the degree of a note in the scale
	
		degree
			the number of a note in a scale (e.g. array index)
 */
public class NamedNoteSpec {
	@Test
	public void test_named_notes_equality(){
		assertEquals(NamedNote.A, NamedNote.A);
		assertFalse(NamedNote.A.equals(NamedNote.B));
	}
	
	@Test
	public void can_state_the_degree_of_a_note_in_the_scale(){
		assertEquals(1, NamedNote.A.getDegree());
		assertEquals(2, NamedNote.Bb.getDegree());
		assertEquals(12, NamedNote.Ab.getDegree());
	}
	
	@Test
	public void named_notes_correct(){
		String[] notes =  {"A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab"};
		
		for(int idx=0; idx<NamedNote.values().length; idx++){
			assertEquals(NamedNote.values()[idx].toString(),notes[idx]);
		}
	}
	
	@Test
	public void intervals_in_octave(){
		assertEquals("number of notes named", NamedNote.values().length, 12);
		
		assertEquals("octave interval increment", NamedNote.A.advance(12), NamedNote.A);
		assertEquals("octave + single interval increment", NamedNote.A.advance(13), NamedNote.Bb);
	}
	
	@Test
	public void able_to_resolve_the_next_note(){
		assertEquals("simple progression check",NamedNote.A.getNext(), NamedNote.Bb);
		assertEquals("new scale octave check",NamedNote.Ab.getNext(), NamedNote.A);
	}
	
	@Test
	public void can_advance_by_intervals(){
		assertEquals("single interval increment", NamedNote.A.advance(1), NamedNote.Bb);
		assertEquals("3 interval increment", NamedNote.Db.advance(3), NamedNote.E);
		assertEquals("wrapping around failed", NamedNote.C.advance(NamedNote.values().length + 1), NamedNote.Db);
	}
}
