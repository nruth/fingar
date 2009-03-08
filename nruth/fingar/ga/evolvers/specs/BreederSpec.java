package nruth.fingar.ga.evolvers.specs;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.*;

import nruth.fingar.domain.music.Score;
import nruth.fingar.domain.specs.ScoreSpec;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.evolvers.Breeder;
import nruth.fingar.ga.specs.ArrangementSpec;


public class BreederSpec {
	
	/**
	 * given two arrangements and a boolean mask array, produces two new arrangements where the genes have been crossed over according to the pattern 
	 */
	@Test
	public void crossover_by_pattern(){
		Breeder breeder = new Breeder();
		Score score = ScoreSpec.get_test_score();
		//ensure the score is long enough for testing crossover effectively
		while(score.size() < 5) score = ScoreSpec.get_test_score();
		
		Arrangement a = ArrangementSpec.test_arranger(score);
		
		a.randomise();
		Arrangement b = a.clone();
		b.randomise();
		assertFalse(a.equals(b));
		
		Arrangement[] children;
		boolean[] pattern = new boolean[a.size()];
		Arrays.fill(pattern, false);
		children = breeder.masked_switch(a, b, pattern);
		assertTrue("no switching should give back identical arrangements", 
			(a.equals(children[0]) && b.equals(children[1])) 
			|| (a.equals(children[1]) && b.equals(children[0]))
		);
		
		Arrays.fill(pattern, true);
		children = breeder.masked_switch(a, b, pattern);
		assertTrue("all switching should give back identical arrangements",  
			(a.equals(children[0]) && b.equals(children[1])) 
			|| (a.equals(children[1]) && b.equals(children[0]))
		);
		
		Arrays.fill(pattern, false);
		Arrays.fill(pattern, 0, 3, true);
//		System.out.println(Arrays.toString(pattern));
		children = breeder.masked_switch(a, b, pattern);
		assertNotSame(children[0], a); assertNotSame(children[1], a);
		assertNotSame(children[0], b); assertNotSame(children[1], b);
		
//		System.out.println(Arrays.deepToString(children));
		
		assertFalse("results should have been mixed, they were not, originals were returned", 
			(a.equals(children[0]) && b.equals(children[1])) 
			|| (a.equals(children[1]) && b.equals(children[0]))
		);
		
		//check elements 1 2 3 from child are from one parent, and the rest of the genotype from the other parent 		
		float locus_key = score.get_nth_note(4).start_beat();		
		if(children[0].fingered_notes().headMap(locus_key).equals(a.fingered_notes().headMap(locus_key))){
			//child0 head == a head
			assertTrue("child0 given the wrong post-crossover-locus tail", //tail0 = tailb
				children[0].fingered_notes().tailMap(locus_key).equals(b.fingered_notes().tailMap(locus_key))
			);
			assertTrue("child1 given the wrong pre-crossover-locus head", //head1 = headb
				children[1].fingered_notes().headMap(locus_key).equals(b.fingered_notes().headMap(locus_key))
			);
			assertTrue("child1 given the wrong post-crossover-locus tail", // tail1 = taila
				children[1].fingered_notes().tailMap(locus_key).equals(a.fingered_notes().tailMap(locus_key))
			);
		} else if (children[0].fingered_notes().headMap(locus_key).equals(b.fingered_notes().headMap(locus_key))){
			//child0 head == b head
			assertTrue("child0 given the wrong post-crossover-locus tail", //tail0 = taila
				children[0].fingered_notes().tailMap(locus_key).equals(a.fingered_notes().tailMap(locus_key))
			);
			assertTrue("child1 given the wrong pre-crossover-locus head", //head1 = heada
				children[1].fingered_notes().headMap(locus_key).equals(a.fingered_notes().headMap(locus_key))
			);
			assertTrue("child1 given the wrong post-crossover-locus tail", // tail1 = tailb
				children[1].fingered_notes().tailMap(locus_key).equals(b.fingered_notes().tailMap(locus_key))
			);
		} else {
			fail("child0 has pre-crossover-locus head from neither parent a nor b");
		}
	}
}