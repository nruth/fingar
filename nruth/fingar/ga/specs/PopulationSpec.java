package nruth.fingar.ga.specs;

import org.junit.Test;

import nruth.fingar.Arrangement;
import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.Score;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.domain.specs.ScoreSpec;
import nruth.fingar.ga.Population;
import nruth.fingar.ga.evolvers.Evolver;
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
	 * is iterable by individual genotypes (Arrangement)
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
		Population clone = initial.clone();
		assertNotSame("cloning returned same object ref", initial, clone);
		assertEquals("cloning changed values",initial, clone);		
	}
	
	/**
	 * can determine equality
	 * considers individuals in the population only
	 */
	@Test
	public void can_determine_equality(){
		//null 
		assertFalse("null object considered equal",test_population().equals(null));
		
		//same object
		Population pop = test_population();
		assertEquals("same object considered different", pop,pop);
		
		//different object same population values
		Population pop_a = new Population(
			new Score(
				new TimedNote[]{	new TimedNote(new Note(NamedNote.A, 1),0f, 1f)	}
			), EvolverSpec.dummy_evolver()
		);
		
		Population pop_b = new Population(
			new Score(
				new TimedNote[]{	new TimedNote(new Note(NamedNote.A, 1),0f, 1f)	}
			), EvolverSpec.dummy_evolver()
		);
		assertEquals(pop_a, pop_b);
		assertEquals(pop_b, pop_a);
		
		//different object different populations
		Population pop_c = new Population(
			new Score(
				new TimedNote[]{	new TimedNote(new Note(NamedNote.C, 1),0f, 1f)	}
			), EvolverSpec.dummy_evolver()
		);
		assertFalse(pop_c.equals(pop_a));
		assertFalse(pop_b.equals(pop_c));
	}
	
	@Test
	public void hashcode_contract_maintained(){
		//different object same population values
		Population pop_a, pop_b;
		pop_a = test_population();
		pop_b = pop_a.clone();
		assertEquals("not equal, cloning or equality is broken",pop_a, pop_b);
		assertNotSame("same object, invalid test",pop_a, pop_b);
		assertEquals(pop_a.hashCode(), pop_b.hashCode());
		
		//different object different populations
		Population pop_c = new Population(
			new Score(
				new TimedNote[]{	new TimedNote(new Note(NamedNote.C, 1),0f, 1f)	}
			), EvolverSpec.dummy_evolver()
		);
		
		assertFalse(pop_a.equals(pop_c));
		assertFalse(pop_a.hashCode() == pop_c.hashCode());
		
		Population successor = pop_a.successor();
		assertFalse(pop_a.equals(successor));
		assertFalse(pop_a.hashCode() == successor.hashCode());
	}
	
	
	//helpers
	public static Population test_population(){	return new Population(ScoreSpec.get_test_score(), EvolverSpec.test_evolver()); }
}