package nruth.fingar.ga.evolvers;

import java.security.InvalidAlgorithmParameterException;

import nruth.fingar.Arrangement;
import nruth.fingar.ga.Population;

/**
 * for N generations, will create stochastically independent successor generations
 * has no fitness function or breeding concept
 * @author nicholasrutherford
 */
public final class NDeepRandomEvolver extends Evolver {
	/**
	 * @param generations the number of generations to process
	 */
	public NDeepRandomEvolver(int generations) {
		if(generations < 1){ throw new RuntimeException("must be 1 or more generations"); }
		this.generations = generations;
		current_generation = 1;
	}

	@Override
	public Evolver clone() {
		NDeepRandomEvolver clone = new NDeepRandomEvolver(this.generations);
		clone.current_generation = this.current_generation;
		return clone;
	}
	
	@Override
	public Population create_successor_population(Population forebears) {	
		if(current_generation >= generations){ throw new RuntimeException("too many generations produced"); }
		Population successors = forebears.clone();
		
		//repopulate the arrangements
		for(Arrangement arr : successors){ arr.randomise(); }
		
		//cloning created the next evolver with the same details (generation count) so force an update
		NDeepRandomEvolver ev_succ = (NDeepRandomEvolver)successors.evolver();
		if(++ev_succ.current_generation >= generations){ ev_succ.set_have_finished(); } 
		
		return successors;
	}
	
	@Override
	public int generation() {	return current_generation;	}
	
	private final int generations;
	private int current_generation;
}
