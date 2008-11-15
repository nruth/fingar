/**
	 
 */
package nruth.gitfga.encoding.tests;

import static org.junit.Assert.*;

import java.util.*;

import nruth.gitfga.encoding.*;

import org.junit.*;

/**
	@author nicholasrutherford
	
 */
public class NoteSpec {
	
	private Note fixture;
	private int fixture_octave;
	private AChromatic.NamedNote fixture_note;
	
	public static class NoteFactory{
		public static Note getRandomNoteInOctave(int octave){ return new Note(getRandomNamedNote(), octave);}
		public static Note getRandomNoteWithName(AChromatic.NamedNote note_name){ return new Note(note_name, seed.nextInt());}
		public static Note getRandomNote(){ return new Note(getRandomNamedNote(), seed.nextInt()); }

		
		/**
			last modified: 4 Nov 2008
			@return a random note from all available in the enumeration
		 */
		public static AChromatic.NamedNote getRandomNamedNote(){
			AChromatic.NamedNote[] notes = AChromatic.NamedNote.values(); //all notes
			int random_note = seed.nextInt(notes.length); //random notes index
			
			return notes[random_note];
		}
		
		public static int getRandomOctave(int range){
			return seed.nextInt(range);
		}
		
		private static Random seed = new Random();
		
		//internal test
		@Test
		public void testRandomNoteGenerator(){
			AChromatic.NamedNote[] notes = new AChromatic.NamedNote[4];
			for(int n=0; n<notes.length; n++){ notes[n] = getRandomNamedNote(); }
			assertFalse(notes[0] == notes[1] && notes[2] == notes[3] && notes[0] == notes[3]);
		}		
	}
	
	@Before
	public void setUp() throws Exception {
		fixture_octave = NoteFactory.getRandomOctave(6);
		fixture_note = NoteFactory.getRandomNamedNote();
		fixture = new Note(fixture_note, fixture_octave);
	}
	
	@Test
	public void belongs_to_an_octave(){
		assertEquals(fixture_octave, fixture.getOctave());
	}
	
	@Test
	public void has_a_name_from_named_notes(){
		boolean contains = false;
		for(AChromatic.NamedNote namedNote : AChromatic.NamedNote.values()){
			if(namedNote.toString().equals(fixture.getNote().toString())) contains=true;
		}
		assertTrue("note not valid", contains);
	}
	
	@Test 
	public void note_given_matches_note_returned(){
		assertEquals(fixture.getNote(), fixture_note);
	}
	
	@Test
	public void is_distinct_from_notes_in_other_octaves_with_the_same_name(){
		assertFalse(fixture.equals(new Note(fixture_note, fixture_octave+1)));
		
		//general equality tests
		assertFalse(fixture.equals(NoteFactory.getRandomNote()) && fixture.equals(NoteFactory.getRandomNote()));
		assertEquals(fixture, new Note(fixture_note, fixture_octave));
	}
}
