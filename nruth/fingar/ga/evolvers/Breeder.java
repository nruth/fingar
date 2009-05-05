package nruth.fingar.ga.evolvers;

import java.util.Arrays;
import java.util.Random;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.ga.Arrangement;

public class Breeder {
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
	public Arrangement[] random_mask_crossover(Arrangement x, Arrangement y) {
		Random seed = new Random();
		
		boolean[] pattern = new boolean[x.size()];
		for(int n=0; n<x.size(); n++){
			pattern[n] = seed.nextBoolean(); 
		}
		
		return masked_switch(x, y, pattern);
	}
	
	public Arrangement[] twist_about_random_locus(Arrangement x, Arrangement y){
		int num_start_beats = x.fingered_notes().keySet().size();
		int twist_locus = new Random().nextInt(num_start_beats);  
		
		//child 1 = x until locus, y after locus
		//child 2 = y until locus, x after locus
		boolean[] pattern = new boolean[x.size()];
		Arrays.fill(pattern, false);
		Arrays.fill(pattern, 0, twist_locus, true); //in case of twist_locus == 0 no vals are true
		
		return masked_switch(x, y, pattern);
	}
	
	/**
	 * returns clones of a and b with crossover applied according to the boolean mask
	 * @param a parent
	 * @param b parent 
	 * @param mask boolean mask to apply in deciding whether to take a gene from a or b
	 * @return clone pair of [a][b] where a and b have had crossover applied
	 */
	public Arrangement[] masked_switch(Arrangement a, Arrangement b, boolean[] mask){
		Arrangement x = a.clone();
		Arrangement y = b.clone();
		
		int n=0;
		for(float start_beat : x.fingered_notes().keySet()){
			if(mask[n++]){ //interchange x and y
				FingeredNote temp = x.fingered_notes().get(start_beat);
				x.fingered_notes().put(start_beat, y.fingered_notes().get(start_beat));
				y.fingered_notes().put(start_beat, temp);	
			} else {} //leave unchanged
		}
		
		return new Arrangement[]{x, y};
	}
	
	/**
	 * given an arrangement mutates each constituent note according to p_mutate
	 * @param arrangement
	 * @param rand random number generator
	 * @param p_mutate the likelihood of a note being mutated, tested against each note individually
	 * @return the (destructively) mutated arrangement
	 */
	public Arrangement mutate(Arrangement arrangement, Random rand, double p_mutate){
		if(rand.nextDouble() < p_mutate){
			arrangement.randomise();
		}
		
		return arrangement; 
	}
}
