package nruth.fingar.ga.evolvers.specs;

import static org.junit.Assert.*;

import java.util.Arrays;

import nruth.fingar.domain.music.Score;
import nruth.fingar.domain.specs.ScoreSpec;
import nruth.fingar.ga.evolvers.Evolver;
import nruth.fingar.ga.evolvers.GoldbergRouletteWheel;
import nruth.fingar.ga.evolvers.NDeepRandomEvolver;
import nruth.fingar.ga.specs.ArrangementSpec;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;
import org.junit.Test;
import org.junit.runner.RunWith;

public class GoldbergRouletteWheelTest {	
	@Test
	public void test_cpd_selection() {
		//set up some arrangements first
		Arrangement arr1, arr2, arr3;
		Score score = ScoreSpec.get_test_score();
		arr1 = new Arrangement(score);
		arr2 = new Arrangement(score);
		arr3 = new Arrangement(score);
		
		//make them different
		arr1.randomise();
		arr2.randomise();
		arr3.randomise();
		
		//allocate some known costs
		arr1.assign_cost(1);
		arr2.assign_cost(2);
		arr3.assign_cost(3);

		//set up the population with them
		Population popl = new Population(new NDeepRandomEvolver(3), Arrays.asList(arr1, arr2, arr3), score);		
		GoldbergRouletteWheel wheel = new GoldbergRouletteWheel(popl);		
		
		//the pd will be
		// 1: 6/11 = 0.545..
		// 2: 3/11 = 0.272..
		// 3: 2/11 = 0.1818..
		
		//check the boundaries are working as described in the design
		assertEquals(wheel.get_individual_at_cpd(0.0),arr1);
		assertEquals(wheel.get_individual_at_cpd(0.4),arr1);
		assertEquals(wheel.get_individual_at_cpd(6/11),arr1);
		assertEquals(wheel.get_individual_at_cpd(0.6),arr2);
		assertEquals(wheel.get_individual_at_cpd(0.82),arr3);
		assertEquals(wheel.get_individual_at_cpd(1.0),arr3);
	}
}
