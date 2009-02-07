package nruth.fingar.ga.specs;

import org.junit.Test;
import nruth.fingar.domain.specs.ScoreSpec;
import nruth.fingar.ga.Population;
import static junit.framework.Assert.*;

public class PopulationSpec {
	/**
	 * given an evolver, can produce a distinct successor population
	 */
	@Test
	public void given_evolver_can_produce_successors(){
		Population pop = test_population();
		assertNotSame(pop, pop.successor());
	}
	
	//helpers
	public static Population test_population(){	return new Population(ScoreSpec.get_test_score(), EvolverSpec.test_evolver()); }
}
