package nruth.fingar.tests;

import static org.junit.Assert.*;

import java.util.Random;

import nruth.fingar.Arrangement;
import nruth.fingar.FingeredNote;
import nruth.fingar.domain.ArrangedNote;
import nruth.fingar.domain.Assumptions;
import nruth.fingar.domain.Note;
import nruth.fingar.domain.tests.ScoreSpec;

import org.junit.*;

public class ArrangementSpec {
	/**
	 * given a score
	 * 	will store string, fret and finger allocations for each note in the score
	 */
	
	@Test
	public void is_iterable_correctly(){
		int n=1;
		for(FingeredNote note : arrangement){
			assertEquals(arrangement.score().get_nth_note(n++), note.getNote());
		}
	}
	
	@Test
	public void stores_string_allocations(){
		Assumptions.STRINGS string = Assumptions.STRINGS.values()[seed.nextInt(Assumptions.STRINGS.values().length)];
		for(FingeredNote note : arrangement){ arrangement.allocateString(note, string); }
	}
	
	@Test
	public void stores_finger_allocations(){
		int finger = seed.nextInt(Assumptions.FINGERS.length);
		for(FingeredNote note : arrangement){ arrangement.allocateFinger(note, finger); }
	}
	
	@Test
	public void stores_fret_allocations(){
		int fret = seed.nextInt(Assumptions.FRETS+1);
		for(FingeredNote note : arrangement){ arrangement.allocateFret(note, fret); }
	}
	
	/**
	 * given a score
	 */
	@Before
	public void arrangement_constructor_given_a_score(){
		arrangement = new Arrangement(ScoreSpec.get_test_score());
	}
	
	private Arrangement arrangement;
	Random seed = new Random();
}