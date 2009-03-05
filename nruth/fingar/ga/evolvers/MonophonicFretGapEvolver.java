package nruth.fingar.ga.evolvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import nruth.fingar.FingeredNote;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;

public final class MonophonicFretGapEvolver extends Evolver {
	public MonophonicFretGapEvolver(int generations) {
		if(generations < 1){ throw new RuntimeException("must be 1 or more generations"); }
		this.target_generations = generations;
		current_generation = 1;
	}

	@Override
	public MonophonicFretGapEvolver clone() {
		MonophonicFretGapEvolver clone = new MonophonicFretGapEvolver(target_generations);
		clone.current_generation = current_generation;
		return clone;
	}

	@Override
	public Population create_successor_population(Population forebears) {
		if(current_generation >= target_generations){ throw new RuntimeException("too many generations produced"); }
		
		Random rand = new Random();
		
		//1. allocate cost to each individual
		assign_costs_to_population_by_fretgap(forebears);
		
		//2. use weights to construct Goldberg's roulette wheel
		//this should be delegated to a selection object which constructs the wheel once only, or *is* the wheel
		GoldbergRouletteWheel wheel = new GoldbergRouletteWheel(forebears);
		
	/*	3. produce parent pairs by:
		while pairs < (popsize/2) 
			3.1. spin the wheel twice to get parents X and Y 
			3.2. crossover X and Y to produce offspring M and N.
			3.3 add M and N to the successor population
	*/
		int popsize = forebears.size();
		LinkedList<Arrangement> successors = new LinkedList<Arrangement>();
		while(successors.size() < popsize){
			Arrangement y = wheel.spin();
			Arrangement x = wheel.spin();	
			successors.addAll((rand.nextDouble() < p_crossover) ? Arrays.asList(Breeder.twist_about_random_locus(x, y)) : Arrays.asList(x, y));
		}
		
		//4. if size(sucessor_population) > fixed population size then drop 1 individual at random from successor_population
		if(successors.size() > popsize ){ //got an even number, need the odd number 1 below it
			successors.remove(new Random().nextInt(successors.size())); //drop 1 randomly with uniform distribution over successor population
		}
		
		//5. mutation, or move it into 3

//		int n_mutated = 0;
//		int n_notmutated = 0;
		for(Arrangement arr : successors){
			for(FingeredNote note : arr){
				if(rand.nextDouble() < p_mutate){ 
//					System.out.println("before randomise "+note); 
					note.randomise_fingering(); 
//					System.out.println("after randomise: "+note); 
//					n_mutated++;
				}
//				else{ n_notmutated++; }
			}
		}
		
//		System.out.println("mutated: "+n_mutated);
//		System.out.println("not mutated: "+n_notmutated);
		MonophonicFretGapEvolver ev = this.clone();
		if(++ev.current_generation >= target_generations){ ev.set_have_finished(); }
		return new Population(ev, successors, forebears.score()); 
	}
	
	
	/**
	 * the population is the nth generation, starting from 1
	 */
	@Override
	public int generation() {	return current_generation; }
	
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

	/**
	 * assigns cost values to each individual in the population
	 * @param pop
	 */
	public static void assign_costs_to_population_by_fretgap(Population pop){
		for(Arrangement arr : pop){
			arr.assign_cost(cost_by_fretgap(arr));
		}
	}
	
	private int current_generation;
	private int target_generations;
	private double p_crossover = 0.5;
	private double p_mutate = 0.05;
}
