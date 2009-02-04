package nruth.fingar.tests;

import nruth.fingar.Arrangement;
import nruth.fingar.domain.tests.ScoreSpec;

import org.junit.*;

public class ArrangementSpec {
	/**
	 * given a score
	 * 	will store string, fret and finger allocations for each note in the score
	 * 	
	 */
	
	/**
	 * given a score
	 */
	@Before
	public void arrangement_constructor_given_a_score(){
		arrangement = new Arrangement(ScoreSpec.get_test_score());
	}
	
	private Arrangement arrangement;
}