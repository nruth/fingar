package nruth.fingar.ga.evolvers;

import java.util.Arrays;
import java.util.Random;

import nruth.fingar.FingeredNote;
import nruth.fingar.ga.Arrangement;

public final class Breeder {
	/**
	 * given two individuals (Arrangements) produces two new arrangements
	 * what to crossover?
	 * 
	 * for each note in the arrangement may change :
	 * 	* finger
	 * 	* fret & string
	 * 
	 * where each note is a chromosome in an individual genotype (arrangement):
	 * 	determine a random binary pattern for selecting the chromosome from parent X or Y
	 * 	and create two children, one with the pattern, and one with its complement, so all notes (chromosomes) from both parents are used in the children.
	 */
	public static Arrangement[] crossover_arrangements(Arrangement x, Arrangement y) {
		x = x.clone();
		y = y.clone();
		Random seed = new Random();
		
		boolean[] pattern = new boolean[x.size()];
		for(int n=0; n<x.size(); n++){
			pattern[n] = seed.nextBoolean(); 
		}
		
		int n=0;
		for(float start_beat : x.fingered_notes().keySet()){
			if(pattern[n]){ //interchange x and y
				FingeredNote temp = x.fingered_notes().get(start_beat);
				x.fingered_notes().put(start_beat, y.fingered_notes().get(start_beat));
				y.fingered_notes().put(start_beat, temp);	
			} else {} //leave unchanged
		}
		
		return new Arrangement[]{x, y};
	}
	
}
