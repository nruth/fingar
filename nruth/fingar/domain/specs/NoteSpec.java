package nruth.fingar.domain.specs;

import static org.junit.Assert.*;
import java.util.*;
import nruth.fingar.domain.*;
import nruth.fingar.domain.guitar.Guitar;
import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;

import org.junit.*;

public class NoteSpec {	
	
	@Test
	public void belongs_to_an_octave(){
		assertEquals(fixture_octave, fixture.octave());
	}
	
	@Test
	public void has_a_name_from_named_notes(){
		boolean contains = false;
		for(NamedNote namedNote : NamedNote.values()){
			if(namedNote.toString().equals(fixture.named_note().toString())) contains=true;
		}
		assertTrue("note not valid", contains);
	}
	
	@Test
	public void is_distinct_from_notes_in_other_octaves_with_the_same_name(){
		assertFalse(fixture.equals(new Note(fixture_note, (fixture_octave%Guitar.OCTAVE_RANGE)+1 )));
		
		//general equality tests
		assertFalse(fixture.equals(NoteFactory.getRandomNote()) && fixture.equals(NoteFactory.getRandomNote()));
		assertEquals(fixture, new Note(fixture_note, fixture_octave));
	}
	
	@Test 
	public void stores_given_named_note(){
		assertEquals(fixture.named_note(), fixture_note);
	}
	
	/**
	 * compares notes and returns the interval difference  
	 */
	@Test(expected=NullPointerException.class)
	public void compare_null_note(){
		fixture.compareTo(null);
	}
	
	/**
	 * compares notes and returns the interval difference  
	 */
	@Test
	public void compare_notes(){
		//same object
		assertEquals(0, fixture.compareTo(fixture));
		
		//same note different object
		assertEquals(0, fixture.compareTo(new Note(fixture.named_note(),fixture.octave())));

		//compare_higher_note
		assertEquals(2, new Note(NamedNote.A, 2).compareTo(new Note(NamedNote.B, 2)));
		
		//	compare_lower_note
		assertEquals(-3, new Note(NamedNote.D, 2).compareTo(new Note(NamedNote.B, 2)));

		//	compare_across_octaves
		assertEquals(14, new Note(NamedNote.A, 2).compareTo(new Note(NamedNote.B, 3)));
		assertEquals(-15, new Note(NamedNote.D, 2).compareTo(new Note(NamedNote.B, 1)));
	}
	
	
	/**
	 * validates its octave is in a possible range (1..4)
	 */
	@Test
	public void check_each_octave(){
		for(int i=1; i<=Guitar.OCTAVE_RANGE; i++){ NoteFactory.getRandomNoteInOctave(i); }
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
	public void octave_out_of_range_off_end(){ NoteFactory.getRandomNoteInOctave(Guitar.OCTAVE_RANGE+1); }
	
	/**
	 * Note equality tests 
	 */
	@Test
	public void note_equality(){
		assertEquals("same object equal",fixture, fixture);
		assertFalse("null object equality fail",fixture.equals(null));
		assertEquals("same values different objects",new Note(NamedNote.F, 1), new Note(NamedNote.F, 1));
		assertFalse("octave difference ignored",new Note(NamedNote.D, 2).equals(new Note(NamedNote.D, 1)));
		assertFalse("note difference ignored",new Note(NamedNote.C, 2).equals(new Note(NamedNote.D, 2)));
	}
	
	/**
	 * Hashcode tests
	 */
	@Test
	public void hashcode_correctness(){
		assertEquals("same object equal",fixture.hashCode(), fixture.hashCode());
		assertEquals("same values different objects",new Note(NamedNote.F, 1).hashCode(), new Note(NamedNote.F, 1).hashCode());
		assertFalse("octave difference ignored",new Note(NamedNote.D, 2).hashCode() == new Note(NamedNote.D, 1).hashCode());
		assertFalse("note difference ignored",new Note(NamedNote.C, 2).hashCode() == new Note(NamedNote.D, 2).hashCode());
	}
	
	@Test
	public void clones_correctly(){
		Note note = NoteFactory.getRandomNote();
		Note clone = note.clone();
		assertEquals(note, clone);
		assertNotSame(note, clone);
		//does not check depth of cloning
	}
	
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
		public static Note getRandomNoteWithName(NamedNote note_name){ return new Note(note_name, seed.nextInt(Guitar.OCTAVE_RANGE)+1);}
		/**
		 * generates a random valid note, within assumptions' octave range
		 * @return a valid random note (namednote & octave)
		 */
		public static Note getRandomNote(){ return new Note(getRandomNamedNote(), seed.nextInt(Guitar.OCTAVE_RANGE)+1); }

		
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
			return seed.nextInt(Guitar.OCTAVE_RANGE)+1;
		}
		
		private static Random seed = new Random();
		
		//internal test
		@Test
		public void testRandomNoteGenerator(){
			NamedNote[] notes = new NamedNote[4];
			for(int n=0; n<notes.length; n++){ notes[n] = getRandomNamedNote(); }
			assertFalse("run again incase of false positive", notes[0] == notes[1] && notes[2] == notes[3] && notes[0] == notes[3]);
		}		
	}
}
