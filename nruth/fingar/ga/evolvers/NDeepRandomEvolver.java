package nruth.fingar.ga.evolvers;

import java.util.ArrayList;
import java.util.List;

import nruth.fingar.Arrangement;
import nruth.fingar.ga.Population;

/**
 * for N generations, will create stochastically independent successor generations
 * has no fitness function or breeding concept
 * @author nicholasrutherford
 */
public class NDeepRandomEvolver extends Evolver {
	/**
	 * @param generations the number of generations to process
	 */
	public NDeepRandomEvolver(int generations) {
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
	public List<Arrangement> evolve(Population forebears) {		
		for(Arrangement arr : forebears){ arr.randomise(); }		
		return forebears;
	}

	private final int generations;
	private int current_generation;
}
