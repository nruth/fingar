package nruth.fingar.ga.evolvers.specs;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import nruth.fingar.FingeredNote;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.Score;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;
import nruth.fingar.ga.evolvers.Evolver;
import nruth.fingar.ga.evolvers.MonophonicFretGapEvolver;
import nruth.fingar.specs.MonophonicScales;

import org.junit.*;

public class MonophonicFretGapEvolverSpec {
	/**
	 * given a population of arrangements for a score
	 * I want to rank the arrangements
	 * 
	 * Ranking involves giving each arrangement a cost
	 * 
	 * The cost value will be the sum of distances between adjacent note frets in this simple case
	 */
	
	@Before
	public void given_a_population_for_a_score(){
		evolver = new MonophonicFretGapEvolver(6);
		score = MonophonicScales.c_major_scale();
		pop = new Population(score, evolver);
	}
	
	@Test
	public void calculate_fret_gap_between_notes(){
		FingeredNote note_1, note_2, note_3, note_4;
		note_1 = new FingeredNote(1, 1, GuitarString.LOW_E, new TimedNote(new Note(NamedNote.F, 1), 1.0f, 1.0f));
		note_2 = new FingeredNote(1, 3, GuitarString.LOW_E, new TimedNote(new Note(NamedNote.G, 1), 2.0f, 1.0f));
		assertEquals(2, MonophonicFretGapEvolver.fretgap(note_1, note_2));
		assertEquals(2, MonophonicFretGapEvolver.fretgap(note_2, note_1)); //magnitude not -ve
		
		note_3 = new FingeredNote(3, 3, GuitarString.D, new TimedNote(new Note(NamedNote.F, 2), 3.0f, 1.0f));
		assertEquals(0, MonophonicFretGapEvolver.fretgap(note_2, note_3));
		
		note_4 = new FingeredNote(2, 10, GuitarString.B, new TimedNote(new Note(NamedNote.Db, 2), 4.0f, 1.0f));
		assertEquals(7, MonophonicFretGapEvolver.fretgap(note_2, note_4));
	}
	
	@Test
	public void cost_by_fret_gap(){
		assertTrue(MonophonicFretGapEvolver.cost_by_fretgap(pop.iterator().next()) > 0);
		
		Arrangement arr = new Arrangement(
				new Score(new TimedNote[] {
					new TimedNote(new Note(NamedNote.E, 1), 1.0f, 1.0f),
					new TimedNote(new Note(NamedNote.G, 1),2.0f, 1.0f)
				}
			)	
		);
		arr.randomise(); //initialise before overriding values
		
		//dont care if they are logically/musically the right way around, as long as the frets resolve correctly in the test
		Iterator<FingeredNote> itr = arr.iterator();
		FingeredNote note1 = itr.next();
		note1.setString(GuitarString.LOW_E); note1.setFret(1);
		FingeredNote note2 = itr.next();
		note2.setString(GuitarString.LOW_E); note2.setFret(11);
		assertEquals(10, MonophonicFretGapEvolver.cost_by_fretgap(arr));
	}
	
	@Test
	public void rank_population(){
		MonophonicFretGapEvolver.assign_costs_to_population_by_fretgap(pop);
		List<Arrangement> rankview = pop.ranked();
		Iterator<Arrangement> itr = rankview.iterator();
		Arrangement arr1, arr2 = itr.next();
		while(itr.hasNext()){
			arr1 = arr2;
			arr2 = itr.next();
			assertTrue("ordering error, see population spec", arr1.cost() <= arr2.cost());
		}
	}
	
	@Test
	public void produce_successor_using_rankings(){
		//this is not an easy test because of the random nature of the ga
		//a rough test (which may fail occasionally without being broken) is to use a total cost and state that it should decrease in the successor population
		
		//ranking is done when a population's successor is created, otherwise it will have -1 values
		Population pop2 = pop.successor();
		Population pop5 = pop2.successor().successor().successor();
		int gen1_cost = 0;
		for(Arrangement arr : pop.ranked()){ gen1_cost += arr.cost();}
		int gen2_cost = 0;
		for(Arrangement arr : pop2.ranked()){ gen2_cost += arr.cost();}	
		
		assertTrue("cost should reduce",gen1_cost > gen2_cost);
		//System.out.println(gen1_cost);
		//System.out.println(gen2_cost);
		
		int gen5_cost = 0;
		
		MonophonicFretGapEvolver.assign_costs_to_population_by_fretgap(pop5);
		for(Arrangement arr : pop5.ranked()){ gen5_cost += arr.cost();}
		assertTrue("cost should reduce",gen2_cost > gen5_cost);
		//System.out.println(gen5_cost);
	}
	
	private Population pop;
	private Score score;
	private MonophonicFretGapEvolver evolver;
}
