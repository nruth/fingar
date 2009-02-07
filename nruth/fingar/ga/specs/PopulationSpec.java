package nruth.fingar.ga.specs;

import org.junit.Test;

import nruth.fingar.Arrangement;
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
	
	/**
	 * is iterable by individual phenotype (Arrangement)
	 */
	@Test
	public void iterable_by_individual(){
		for(Arrangement arr : test_population()){	assertNotNull(arr);	}
	}
	
	/**
	 * can be cloned
	 */
	@Test
	public void can_be_cloned(){
		Population initial = test_population();
		Population clone = test_population().clone();
		assertEquals("cloning changed values",initial, clone);
		assertNotSame("cloning returned same object ref", initial, clone);
	}
	
	//helpers
	public static Population test_population(){	return new Population(ScoreSpec.get_test_score(), EvolverSpec.test_evolver()); }
}