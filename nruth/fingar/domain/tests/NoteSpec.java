/**
	 
 */
package nruth.fingar.domain.tests;

import static org.junit.Assert.*;
import java.util.*;
import nruth.fingar.domain.*;
import org.junit.*;

public class NoteSpec {	
	@Test
	public void belongs_to_an_octave(){
		assertEquals(fixture_octave, fixture.getOctave());
	}
	
	@Test
	public void has_a_name_from_named_notes(){
		boolean contains = false;
		for(NamedNote namedNote : NamedNote.values()){
			if(namedNote.toString().equals(fixture.getNote().toString())) contains=true;
		}
		assertTrue("note not valid", contains);
	}
	
	@Test
	public void is_distinct_from_notes_in_other_octaves_with_the_same_name(){
		assertFalse(fixture.equals(new Note(fixture_note, fixture_octave+1)));
		
		//general equality tests
		assertFalse(fixture.equals(NoteFactory.getRandomNote()) && fixture.equals(NoteFactory.getRandomNote()));
		assertEquals(fixture, new Note(fixture_note, fixture_octave));
	}
	
	@Test 
	public void assesses_equality_of_notes(){
		assertEquals(fixture.getNote(), fixture_note);
	}
	
	/**
	 * validates its octave is in a possible range (1..4)
	 */
	@Test
	public void check_each_octave(){
		for(int i=1; i<=Assumptions.OCTAVE_RANGE; i++){ NoteFactory.getRandomNoteInOctave(i); }
		//passes if no exceptions thrown
	}
	
	/**
	 * validates its octave is in a possible range (1..4)
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void octave_out_of_range_0(){ NoteFactory.getRandomNoteInOctave(0); }
	
	/**
	 * validates its octave is in a possible range (1..4)
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void octave_out_of_range_negative(){	NoteFactory.getRandomNoteInOctave(-4); }
	
	/**
	 * validates its octave is in a possible range (1..4)
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void octave_out_of_range_off_end(){ NoteFactory.getRandomNoteInOctave(Assumptions.OCTAVE_RANGE+1); }
	
	@Before
	public void setUp() throws Exception {
		fixture_octave = NoteFactory.getRandomOctave();
		fixture_note = NoteFactory.getRandomNamedNote();
		fixture = new Note(fixture_note, fixture_octave);
	}
	
	private Note fixture;
	private int fixture_octave;
	private NamedNote fixture_note;
	
	public static class NoteFactory{
		public static Note getRandomNoteInOctave(int octave){ return new Note(getRandomNamedNote(), octave);}
		public static Note getRandomNoteWithName(NamedNote note_name){ return new Note(note_name, seed.nextInt(Assumptions.OCTAVE_RANGE)+1);}
		public static Note getRandomNote(){ return new Note(getRandomNamedNote(), seed.nextInt(Assumptions.OCTAVE_RANGE)+1); }

		
		/**
			last modified: 4 Nov 2008
			@return a random note from all available in the enumeration
		 */
		public static NamedNote getRandomNamedNote(){
			NamedNote[] notes = NamedNote.values(); //all notes
			int random_note = seed.nextInt(notes.length); //random notes index
			
			return notes[random_note];
		}
		
		public static int getRandomOctave(){
			return seed.nextInt(Assumptions.OCTAVE_RANGE)+1;
		}
		
		private static Random seed = new Random();
		
		//internal test
		@Test
		public void testRandomNoteGenerator(){
			NamedNote[] notes = new NamedNote[4];
			for(int n=0; n<notes.length; n++){ notes[n] = getRandomNamedNote(); }
			assertFalse(notes[0] == notes[1] && notes[2] == notes[3] && notes[0] == notes[3]);
		}		
	}
}
