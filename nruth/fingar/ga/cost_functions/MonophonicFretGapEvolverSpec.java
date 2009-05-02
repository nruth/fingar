package nruth.fingar.ga.cost_functions;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Random;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.Score;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;
import nruth.fingar.ga.evolvers.Breeder;
import nruth.fingar.ga.evolvers.GeneticAlgorithmEvolver;
import nruth.fingar.ga.probability.GoldbergRouletteWheel;
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
		evolver = new GeneticAlgorithmEvolver(15, 6, 0.3, 0.05, new Random(), new GoldbergRouletteWheel.WheelFactory(), new Breeder(), new MonophonicFretGapCostFunction());
		score = MonophonicScales.c_major_scale();
		pop = new Population(score, evolver);
	}
		
	@Test
	public void calculate_fret_gap_between_notes(){
		FingeredNote note_1, note_2, note_3, note_4;
		note_1 = new FingeredNote(1, 1, GuitarString.LOW_E, new TimedNote(new Note(NamedNote.F, 1), 1.0f, 1.0f));
		note_2 = new FingeredNote(1, 3, GuitarString.LOW_E, new TimedNote(new Note(NamedNote.G, 1), 2.0f, 1.0f));
		assertEquals(2, MonophonicFretGapCostFunction.fretgap(note_1, note_2));
		assertEquals(2, MonophonicFretGapCostFunction.fretgap(note_2, note_1)); //magnitude not -ve
		
		note_3 = new FingeredNote(3, 3, GuitarString.D, new TimedNote(new Note(NamedNote.F, 2), 3.0f, 1.0f));
		assertEquals(0, MonophonicFretGapCostFunction.fretgap(note_2, note_3));
		
		note_4 = new FingeredNote(2, 10, GuitarString.B, new TimedNote(new Note(NamedNote.Db, 2), 4.0f, 1.0f));
		assertEquals(7, MonophonicFretGapCostFunction.fretgap(note_2, note_4));
	}
	
	@Test
	public void cost_by_fret_gap(){
		assertTrue(MonophonicFretGapCostFunction.cost_by_fretgap(pop.iterator().next()) > 0);
		
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
		assertEquals(10, MonophonicFretGapCostFunction.cost_by_fretgap(arr));
	}
	
	private Population pop;
	private Score score;
	private GeneticAlgorithmEvolver evolver;
}
