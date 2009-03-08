package nruth.fingar.ga.evolvers;

import java.util.Random;

import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;
import nruth.fingar.ga.probability.PdFactory;

public class SimpleHandPositionModelGAEvolver extends GeneticAlgorithmEvolver {

	public SimpleHandPositionModelGAEvolver(int target_generations,
			double p_crossover, double p_mutate, Random rand, PdFactory pdfac,
			Breeder breeder) {
		super(target_generations, p_crossover, p_mutate, rand, pdfac, breeder);
	}

	@Override
	protected Population assign_costs_to_population(Population pop) {
		for(Arrangement arr: pop){ assign_simple_hand_model_cost(arr); }
		return pop;
	}
	
	public static void assign_simple_hand_model_cost(Arrangement arr){
		
	}
	
	public static int lhp_of_position(int fret, int finger){
		return fret - (finger-1);
	}

}