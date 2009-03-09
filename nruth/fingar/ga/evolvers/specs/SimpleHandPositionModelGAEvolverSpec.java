package nruth.fingar.ga.evolvers.specs;

import static org.junit.Assert.*;

import java.util.Arrays;

import nruth.fingar.FingeredNote;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.evolvers.SimpleHandPositionModelGAEvolver;
import nruth.fingar.ga.specs.ArrangementSpec;
import nruth.fingar.specs.FingeredNoteSpec;

import org.junit.Test;
import static nruth.fingar.ga.evolvers.SimpleHandPositionModelGAEvolver.lhp_of_position;
public class SimpleHandPositionModelGAEvolverSpec {

	/**
	 * this model is described in report chapter "Genetic Algorithm Design" section "Cost Functions and Fitness" subsection "Simple Hand Position Model"
	 */
	
	@Test
	public void testAssign_simple_hand_model_cost() {
		FingeredNote note_1 = new FingeredNote(1, 3, GuitarString.LOW_E, new TimedNote(new Note(NamedNote.F, 1), 1.0f, 1.0f));
		FingeredNote note_2 = new FingeredNote(1, 3, GuitarString.LOW_E, new TimedNote(new Note(NamedNote.G, 1), 2.0f, 1.0f));
		Arrangement arr = new Arrangement()
		assign_simple_hand_model_cost(note_1);
		
		
	}

	@Test
	public void testLhp_of_position() {
		//test index finger positions
		assertEquals(3, lhp_of_position(3, 1));
		assertEquals(12, lhp_of_position(12, 1));
		
		//test 4th finger positions, -ves are valid (though somewhat unintuitive they do reflect the hand position)
		assertEquals(0, lhp_of_position(3, 4));
		assertEquals(9, lhp_of_position(12, 4));
		
		//test negative lhp positions
		assertEquals(0, lhp_of_position(2, 3));
		assertEquals(-3, lhp_of_position(0, 4));
	}
	
	@Test
	public void values_in_paper(){
		int[] fret = new int[]{3, 7, 1, 0};
		int[] finger = new int[]{4, 3, 3, 4};
		int[] lhps = new int[]{0, 5, -1, -3};
		
		for(int n=0; n<fret.length; n++){
			assertEquals("tab:simple_hand_position_model_with_zeros",lhps[n], lhp_of_position(fret[n], finger[n]));
		}
		
		fret = new int[]{5, 7, 3, 10};
		finger = new int[]{1, 3, 3, 2};
		lhps = new int[]{5, 5, 1, 9};
		for(int n=0; n<fret.length; n++){
			assertEquals("tab:simple_hand_position_model_run",lhps[n], lhp_of_position(fret[n], finger[n]));
		}
	}
}
