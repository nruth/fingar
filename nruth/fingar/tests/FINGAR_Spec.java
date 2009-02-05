package nruth.fingar.tests;

import java.util.List;

import nruth.fingar.Arrangement;
import nruth.fingar.FINGAR;
import nruth.fingar.domain.Score;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * as a guitarist 
 * I want FINGAR to find me the best fingering solutions
 * so that I can play the piece comfortably and efficiently
 * but I want it to be robust against me doing silly things, like running the same calculation twice or losing the results
 */
public class FINGAR_Spec {
	private Score score;
	private FINGAR ga;
	
	@Before 
	public void remember_score_provided(){
		score = nruth.fingar.domain.tests.ScoreSpec.get_test_score();
		ga = new FINGAR(score);
	}
	
	/**
	 * ensure that after any test the score has remained the same, in case the interface is changed to allow it to be mutable
	 */
	@After
	public void check_score_remains_same(){
		assertEquals(score, ga.get_score());
	}
	
	/**
	 * when I create a FINGAR object with a musical score
	 * 	it should know whether it has calculated a result or not, to avoid repeated calculation
	 */
	@Test
	public void get_has_calculated_result(){
		assertFalse(ga.is_processing_finished());
		ga.process();
		assertTrue(ga.is_processing_finished());
	}

	/**
	 * when I ask for results I should be given a non-null list of arrangements
	 */
	@Test
	public void results_give_non_null_list_of_arrangements(){
		List<Arrangement> result = ga.getArrangements();
		assertNotNull(result);
	}
}
