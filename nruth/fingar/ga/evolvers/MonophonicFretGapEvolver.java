package nruth.fingar.ga.evolvers;

import java.util.Iterator;
import java.util.Random;

import nruth.fingar.FingeredNote;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;
import nruth.fingar.ga.probability.PdFactory;

public final class MonophonicFretGapEvolver extends GeneticAlgorithmEvolver {
	public MonophonicFretGapEvolver(int generations, double p_crossover, double p_mutate, Random rand, PdFactory pdfac, Breeder breeder) {
		super(generations, p_crossover, p_mutate, rand, pdfac, breeder);
		if(generations < 1){ throw new RuntimeException("must be 1 or more generations"); }		
	}

	/**
	 * @param note_1
	 * @param note_2
	 * @return the absolute fret distance (magnitude) from fret of note_1 to note_2
	 */
	public static int fretgap(FingeredNote note_1, FingeredNote note_2) {
		return Math.abs(note_1.fret() - note_2.fret());
	}

	public static int cost_by_fretgap(Arrangement individual) {
		int cost = 0;
		Iterator<FingeredNote> itr = individual.iterator();  
		FingeredNote n;
		FingeredNote n2 = itr.next() ;
		while(itr.hasNext()){
			n = n2;
			n2 = itr.next();
			cost += fretgap(n, n2);
		}
		return cost;
	}

	@Override
	protected Population assign_costs_to_population(Population pop) {
		for(Arrangement arr : pop){
			arr.assign_cost(cost_by_fretgap(arr));
		}
		return pop;
	}
}
