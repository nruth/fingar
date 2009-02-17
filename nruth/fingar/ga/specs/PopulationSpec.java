package nruth.fingar.ga.specs;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.Score;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.domain.specs.ScoreSpec;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;
import nruth.fingar.ga.evolvers.Evolver;
import nruth.fingar.ga.evolvers.MonophonicFretGapEvolver;
import nruth.fingar.ga.evolvers.specs.EvolverSpec;
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
	 * can produce an empty copy of a population (i.e. same size)
	 */
	
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
	
	@Test(expected=UnsupportedOperationException.class)
	public void can_produce_immutable_view(){ test_population().view_arrangements().remove(1); }
	
	//this is not good OOP design but reduces the number of wrapper methods I will have to write to pass evolution requests on to the evolver object
	@Test
	public void gives_current_evolver_access(){
		Population p1 = test_population();
		Population p2 = p1.successor();
		assertFalse(p1.equals(p2));
		assertNotSame(p1.evolver(), p2.evolver());
	}
	
	@Test
	public void can_provide_an_increasing_cost_ranked_view(){
		Population pop = test_population();
		MonophonicFretGapEvolver.rank_population_by_fretgap(pop);
		List<Arrangement> rankview = pop.ranked();
		Iterator<Arrangement> itr = rankview.iterator();
		Arrangement arr1, arr2 = itr.next();
		while(itr.hasNext()){ //increasing, so start with low values
			arr1 = arr2;
			arr2 = itr.next();
			assertTrue("ordering error", arr1.cost() <= arr2.cost());
//			System.out.println(arr1 + "\n cost: "+arr1.cost()+"\n\n");
		}
	}
	
	@Test(expected=NullPointerException.class)
	public void undefined_cost_throws_exception(){
		test_population().ranked();
	}
	
	//helpers
	public static Population test_population(){	return new Population(ScoreSpec.get_test_score(), EvolverSpec.test_evolver()); }
}