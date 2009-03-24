package nruth.fingar;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import nruth.fingar.domain.music.Score;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.FINGAR;
import nruth.fingar.ga.evolvers.Evolver;
import nruth.fingar.ga.evolvers.SimpleHandPositionModelGAEvolver;
import nruth.fingar.specs.MonophonicScales;

public class Run {
	
	private static Score score(){
		return MonophonicScales.c_major_scale();
	}
	
	public static void main(String[] args){
		//process alternatives		
		//and check the list contains a known solution
		Evolver evolver = new SimpleHandPositionModelGAEvolver(100000, 50, 0.15, 0.05); //TODO this needs to be the production evolver, whatever that ends up being
		
		FINGAR ga = new FINGAR(score(), evolver);
		List<Arrangement> results = ga.results();
		
		//print out results section, may be removed from the test
		HashSet<Arrangement> results_set = new HashSet<Arrangement>() ;
		results_set.addAll(results);
		Arrangement[] sorted_results_set =results_set.toArray(new Arrangement[results_set.size()]); 
		Arrays.sort(sorted_results_set, new Comparator<Arrangement>() {
			@Override
			public int compare(Arrangement o1, Arrangement o2) {
				return ((Integer)o2.cost()).compareTo(o1.cost());
			}
		});
		
		for(Arrangement result : sorted_results_set){
			System.out.println(result+"Cost: "+result.cost()+"\n----\n\n");
		}
	}
}
