package nruth.fingar.ga.evolvers.specs;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.*;

import nruth.fingar.domain.specs.ScoreSpec;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.evolvers.Breeder;
import nruth.fingar.ga.specs.ArrangementSpec;


public class BreederSpec {
	/**
	 * given two individuals (Arrangements) produces two new arrangements
	 * what to crossover?
	 */
	
	@Test 
	public void crossover_two_arrangements(){
		Arrangement a = ArrangementSpec.test_arranger(ScoreSpec.get_test_score());
		a.randomise();
		Arrangement b = a.clone();
		b.randomise();
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(Arrays.toString(Breeder.crossover_arrangements(a, b)));
		
		fail("not specced");
		//form a collection of the elements in a and b and state that the breeding results must not equal a or b but must have the results in the collection once and only once
		//i.e. check for presence of element then remove it, and ensure collection is empty
	}
}
