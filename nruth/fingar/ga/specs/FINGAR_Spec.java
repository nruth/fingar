package nruth.fingar.ga.specs;

import java.util.List;

import nruth.fingar.Arrangement;
import nruth.fingar.domain.music.Score;
import nruth.fingar.ga.FINGAR;
import nruth.fingar.ga.evolvers.Evolver;
import nruth.fingar.ga.evolvers.NDeepRandomEvolver;
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
		score = nruth.fingar.domain.specs.ScoreSpec.get_test_score();
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
		assertTrue(ga.process());
		assertTrue(ga.is_processing_finished());
		assertFalse(ga.process());
	}
	
	/**
	 * 	returned results set should be nonzero size
	 */
	@Test
	public void nonzero_results_size(){
		List<Arrangement> results = ga.results();
		assertTrue(results.size() > 0);
	}
	
	/**
	 * when I ask for results I should be given a non-null list of arrangements
	 */
	@Test
	public void results_give_non_null_list_of_arrangements(){
		List<Arrangement> result = ga.results();
		assertNotNull(result);
	}
	
	/**
	 * I should be able to specify which evolution mechanism to use
	 */
	@Test
	public void specify_evolution_mechanism(){
		Evolver evolver = new NDeepRandomEvolver(5);
		new FINGAR(score, evolver);
	}
}
