package nruth.fingar.domain.specs;

import static org.junit.Assert.*;

import java.util.LinkedList;

import nruth.fingar.domain.guitar.Guitar;
import nruth.fingar.domain.guitar.Position;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;

import org.junit.*;

public class GuitarTest {
	@Test // test positions produced for note
	public void notePositionProduction(){
		Position[] possible_a1_positions = new Position[]{
			new Position(5, GuitarString.LOW_E), 
			new Position(0, GuitarString.A), 
		};
		LinkedList<Position> produced_a1_positions = Guitar.get_positions_for_note(new Note(NamedNote.A, 1));
		assertEquality(possible_a1_positions, produced_a1_positions);
		
		Position[] possible_E2_positions = new Position[]{
				new Position(12, GuitarString.LOW_E), 
				new Position(7, GuitarString.A),
				new Position(2, GuitarString.D)
		};
		LinkedList<Position> produced_E2_positions = Guitar.get_positions_for_note(new Note(NamedNote.E, 2));
		assertEquality(possible_E2_positions, produced_E2_positions);
		
		Position[] possible_A3_positions = new Position[]{
				new Position(12, GuitarString.LOW_E), 
				new Position(7, GuitarString.A),
				new Position(2, GuitarString.D)
		};
		LinkedList<Position> produced_A3_positions = Guitar.get_positions_for_note(new Note(NamedNote.A, 3));
		assertEquality(possible_A3_positions, produced_A3_positions);
		
		
		
		fail("pending, add more boundary checks to be sure");
		
	//visual checking
	//	for(Position p : possible_e_positions){System.out.print(p+", ");}
	//	System.out.println("\nProduced:");
	//	for(Position p : produced_e_positions){System.out.print(p+", ");}
	}
	
	private void assertEquality(Position[] expected, LinkedList<Position> actual){
		//check same number of entries
		assertTrue("different number of fingering positions produced to possibilities: "+expected.length+" vs produced "+actual.size(), expected.length == actual.size());//check the contents are the same, they must be 1:1 equal since the length is the same
		//check each produced position against every possible position for equality.
		//any fails means the array is wrong
		for(Position p_produced : actual){
			boolean match = false;
			for(Position p_possible : expected){
				if(p_possible.equals(p_produced)){
					match = true; break;
				}
			}
			assertTrue(p_produced.toString()+" not possible", match);
		}
	}

	@Test
	public void are_notes_resolved_correctly_on_string(){
		Position pos = Guitar.note_on_string(new Note(NamedNote.A,3),GuitarString.A);
		assertEquals(new Position(12,GuitarString.A),pos);
	}
	
	@Test
	public void areNotesResolvedCorrectlyFromFrets(){
		Position pos_f_4th = new Position(13, GuitarString.HIGH_E);
		Position pos_f = new Position(1, GuitarString.LOW_E);
		Position pos_open_b = new Position(0, GuitarString.B);
		
		assertTrue(Guitar.note_is_found_at(new Note(NamedNote.F, 1), pos_f));
		assertFalse(Guitar.note_is_found_at(new Note(NamedNote.B, 1), pos_f));
		
		assertTrue(Guitar.note_is_found_at(new Note(NamedNote.F, 4), pos_f_4th));
		assertFalse(Guitar.note_is_found_at(new Note(NamedNote.F, 1), pos_f_4th));

		assertTrue(Guitar.note_is_found_at(new Note(NamedNote.B, 2), pos_open_b));
		assertFalse(Guitar.note_is_found_at(new Note(NamedNote.B, 1), pos_open_b));
	}
}
