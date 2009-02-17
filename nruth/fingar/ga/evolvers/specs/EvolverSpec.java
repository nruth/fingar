package nruth.fingar.ga.evolvers.specs;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.*;

import nruth.fingar.FingeredNote;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.Score;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;
import nruth.fingar.ga.evolvers.Evolver;
import nruth.fingar.ga.evolvers.NDeepRandomEvolver;
import nruth.fingar.ga.specs.PopulationSpec;

public class EvolverSpec {
	/**
	 * given a forebear population, can create a successor population 
	 * which is atomically distinct from the forebear population (ignoring the contents)
	 */
	@Test
	public void can_create_a_successor_population(){
		Evolver evolver = test_evolver();
		Population forebears = PopulationSpec.test_population();
		assertNotSame(forebears, evolver.create_successor_population(forebears));
	}
	
	/**
	 * when producing a successor the associated evolver knows its state in the evolution history
	 */
	@Test
	public void evolver_knows_state_in_evolution(){
		Evolver evolver = test_evolver();
		Population forebears = PopulationSpec.test_population();
		assertFalse(forebears.evolver().generation() == evolver.create_successor_population(forebears).evolver().generation());
	}
	
	/**
	 * knows whether it has halted or is still processing
	 */
	@Test
	public void knows_whether_halted_or_processing(){
		assertFalse(new NDeepRandomEvolver(3).is_halted());
		
		Evolver finished = new Evolver() {
			public Population create_successor_population(Population forebears) {	set_have_finished(); return null;	}
			public Evolver clone() {return null;}
			public int generation() {return 0;	}
		};
		finished.create_successor_population(null);
		assertTrue(finished.is_halted());		
	}
	
	//helper factory
	public static Evolver test_evolver(){ return new NDeepRandomEvolver(3); } 
	/**
	 * an evolver which always creates the same initial population
	 * however, this population is junk data (not musically correct)
	 * @return an evolver which always creates the same initial population
	 */
	public static Evolver dummy_evolver(){ 
		return new Evolver(){
			@Override
			public Population create_successor_population(Population forebears) {  
				Population pop = forebears.clone();
				for(Arrangement arr : pop){ arr.randomise(); }
				return pop;
			}
		
			@Override
			public List<Arrangement> initial_population(Score score) {
				List<Arrangement> pop = Arrays.asList(new Arrangement(score), new Arrangement(score), new Arrangement(score));
				for(Arrangement arr : pop){
					for(FingeredNote note : arr){
						note.setFret(3);
						note.setFinger(3);
						note.setString(GuitarString.A);
					}
				}
				return pop;
			}
			
			public int generation() {return 0;	}
			
			@Override
			public Evolver clone() { return this; }
		};
	}
}
