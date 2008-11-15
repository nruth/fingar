/**
	 
 */
package nruth.gitfga.tests;

import static org.junit.Assert.*;

import java.util.*;

import nruth.gitfga.*;
import nruth.gitfga.Notes.Note;
import nruth.gitfga.Position.GuitarString;

import org.junit.*;

/**
	@author nicholasrutherford
	
 */
public class NotesTest {
	/**
		last modified: 4 Nov 2008
		@throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		notes = NotesFactory.getRandomNotes(5);
	}
	
	private Notes notes;
	
	/**
	 * Test method for {@link nruth.gitfga.Notes#getNotes()}.
	 */
	@Test
	public void testGetNotes() {
		Note[] notes_used = new Note[4];
		for(int idx=0; idx<notes_used.length; idx++){
			notes_used[idx] = NotesFactory.getRandomNote();
		}
		notes = new Notes(notes_used);		
		assertArrayEquals(notes_used, notes.getNotes());
	}
	
	@Test // test positions produced for note
	public void notePositionProduction(){
		Position[] possible_e_positions = new Position[]{
				new Position(0, Position.GuitarString.LOW_E), new Position(12, Position.GuitarString.LOW_E), new Position(24, Position.GuitarString.LOW_E),
				new Position(7, Position.GuitarString.A), new Position(19, Position.GuitarString.A),
				new Position(2, Position.GuitarString.D), new Position(14, Position.GuitarString.D),
				new Position(9, Position.GuitarString.G), new Position(21, Position.GuitarString.G),
				new Position(5, Position.GuitarString.B), new Position(17, Position.GuitarString.B), 
				new Position(0, Position.GuitarString.HIGH_E), new Position(12, Position.GuitarString.HIGH_E), new Position(24, Position.GuitarString.HIGH_E)
		};
		
		Position[] produced_e_positions = Note.E.getPositionsForNote();
		
		//check same number of entries
		assertTrue("different number of fingering positions produced to possibilities: "+possible_e_positions.length+" vs produced "+produced_e_positions.length, produced_e_positions.length == possible_e_positions.length);
		
		//now check the contents are the same, they must be 1:1 equal since the length is the same
		//check each produced position against every possible position for equality.
		//any fails means the array is wrong
		for(Position p_produced : produced_e_positions){
			boolean match = false;
			for(Position p_possible : possible_e_positions){
				if(p_possible.equals(p_produced)){
					match = true; break;
				}
			}
			assertTrue(p_produced.toString()+" not possible", match);
		}

		//visual checking
//		for(Position p : possible_e_positions){System.out.print(p+", ");}
//		System.out.println("\nProduced:");
//		for(Position p : produced_e_positions){System.out.print(p+", ");}
	}
	
	@Test
	public void areNotesResolvedCorrectlyFromFrets(){
		Position pos_f_2nd = new Position(13, GuitarString.HIGH_E);
		Position pos_f = new Position(1, GuitarString.LOW_E);
		Position pos_open_b = new Position(0, GuitarString.B);
		
		assertTrue(Note.F.isFoundAt(pos_f));
		assertFalse(Note.B.isFoundAt(pos_f));
		
		assertTrue(Note.F.isFoundAt(pos_f_2nd));
		assertFalse(Note.B.isFoundAt(pos_f_2nd));
		
		assertTrue(Note.B.isFoundAt(pos_open_b));
		assertFalse(Note.F.isFoundAt(pos_open_b));
	}
	
	public static class NotesFactory{		
		/**
		 * creates a random note sequence of fixed length
			last modified: 4 Nov 2008
			@param length how many notes in the sequence created
			@return an array of random notes, of specified length
		 */
		public static Notes getRandomNotes(int length){
			Note[] notes = new Note[length];
			for(int idx=0; idx<notes.length; idx++){
				notes[idx] = getRandomNote();
			}
			return new Notes(notes);
		}
		
		/**
			last modified: 4 Nov 2008
			@return a random note from all available in the enumeration
		 */
		public static Note getRandomNote(){
			Note[] notes = Note.values(); //all notes
			int random_note = seed.nextInt(notes.length); //random notes index
			
			return notes[random_note];			
		}
		
		//internal test
		@Test
		public void testRandomNoteGenerator(){
			Note[] notes = new Note[4];
			for(int n=0; n<notes.length; n++){ notes[n] = getRandomNote(); }
			assertFalse(notes[0] == notes[1] && notes[2] == notes[3] && notes[0] == notes[3]);
		}
		
		//internal test	
		@Test
		public void testRandomNotesGenerator(){			
			//try 2 against 1
			Notes[] notes = new Notes[3];
			for(int n=0; n<notes.length; n++){ notes[n] = getRandomNotes(4); }
			
			assertFalse(notes[0].equals(notes[1]) && notes[0].equals(notes[2]));
		}
		
		private static Random seed = new Random();
	}
}
