package nruth.fingar.tests;

import java.util.Random;

import nruth.fingar.Arrangement;
import nruth.fingar.domain.ArrangedNote;
import nruth.fingar.domain.Assumptions;
import nruth.fingar.domain.tests.ScoreSpec;

import org.junit.*;

public class ArrangementSpec {
	/**
	 * given a score
	 * 	will store string, fret and finger allocations for each note in the score
	 */
	
	@Test
	public void stores_string_allocations(){
		Assumptions.STRINGS string = Assumptions.STRINGS.values()[seed.nextInt(Assumptions.STRINGS.values().length)];
		for(ArrangedNote note : arrangement){ arrangement.allocateString(note, string); }
	}
	
	@Test
	public void stores_finger_allocations(){
		int finger = seed.nextInt(Assumptions.FINGERS.length);
		for(ArrangedNote note : arrangement){ arrangement.allocateFinger(note, finger); }
	}
	
	@Test
	public void stores_fret_allocations(){
		int fret = seed.nextInt(Assumptions.FRETS+1);
		for(ArrangedNote note : arrangement){ arrangement.allocateFret(note, fret); }
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