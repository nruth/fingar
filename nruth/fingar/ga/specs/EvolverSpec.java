package nruth.fingar.ga.specs;

import static org.junit.Assert.*;

import org.junit.*;

import nruth.fingar.ga.Population;
import nruth.fingar.ga.evolvers.Evolver;
import nruth.fingar.ga.evolvers.NDeepRandomEvolver;

public class EvolverSpec {
	/**
	 * given a forebear population, can create a successor population 
	 * which is atomically distinct from the forebear population (ignoring the contents)
	 */
	@Test
	public void can_create_a_successor_population(){
		Evolver evolver = test_evolver();
		Population forebears = null;
		assertNotSame(forebears, evolver.create_successor_population(forebears));
	}
	
	/**
	 * knows whether it has halted or is still processing
	 */
	@Test
	public void knows_whether_halted_or_processing(){
		assertFalse(new NDeepRandomEvolver(3).is_halted());
		assertTrue(new NDeepRandomEvolver(0).is_halted());
	}
	
	//helper factory
	public static Evolver test_evolver(){ return new NDeepRandomEvolver(3); } 
}
