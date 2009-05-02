package nruth.fingar.ga.evolvers;

import java.util.Iterator;
import java.util.Random;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;
import nruth.fingar.ga.probability.GoldbergRouletteWheel;
import nruth.fingar.ga.probability.PdFactory;

public class SimpleHandPositionModelGAEvolver extends GeneticAlgorithmEvolver {

	public SimpleHandPositionModelGAEvolver(int population_size, int target_generations,
			double p_crossover, double p_mutate, Random rand, PdFactory pdfac,
			Breeder breeder) {
		super(population_size, target_generations, p_crossover, p_mutate, rand, pdfac, breeder);
	}

	public SimpleHandPositionModelGAEvolver(int population_size, int target_generations, double p_crossover, double p_mutate) {
		this(population_size, target_generations, p_crossover, p_mutate, new Random(), new GoldbergRouletteWheel.WheelFactory(), new Breeder());
	}

	@Override
	protected void assign_costs_to_population(Population pop) {
		for(Arrangement arr: pop){ assign_simple_hand_model_cost(arr); }
	}
	
	public static void assign_simple_hand_model_cost(Arrangement arr){
		//this is the deltas process described in paper
		Iterator<FingeredNote> itr = arr.iterator();
		FingeredNote note = itr.next();
		FingeredNote previous_note = note;
		int delta_sum = 0;
		
		while (itr.hasNext()){ //the first evaluation is 1 against 2, 1 by itself has 0 cost as a starting point.
			note = itr.next();
			int lhp_this_n = lhp_of_fingered_fret(note.fret(), note.finger());
			int lhp_previous_n = lhp_of_fingered_fret(previous_note.fret(), previous_note.finger());
			if(note.fret()==0){
				// don't cost 0 fret and don't advance previous note, cost across the open string as though it isn't there
				// this is a naive costing method, and is documented as such in the design report
			}else{
				delta_sum += Math.abs(lhp_this_n - lhp_previous_n); 
				previous_note = note;
			}
		}
		
		arr.assign_cost(delta_sum);	
	}
	
	public static int lhp_of_fingered_fret(int fret, int finger){
		return fret - (finger-1);
	}
	
	public String toString(){
		return this.getClass().getSimpleName() + ": " + super.toString();
	}
}