package nruth.fingar.ga.evolvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
		rank_population_by_fretgap(forebears);
		
		//TODO this should be stochastic, not fixed to pick the best
		ArrayList<Arrangement> successors = new ArrayList<Arrangement>(forebears.size());
		int fittest_half = forebears.size()/2;
		List<Arrangement> ranked = forebears.ranked();
		successors.addAll(ranked.subList(0, fittest_half));
		
		//randomise the other half of the array
		//TODO this isn't being done right either, pairs should be random not by ordering
		for(int n=fittest_half; (n+1)<forebears.size(); n+=2){
//			Arrangement arr = new Arrangement(forebears.score());
//			arr.randomise();
			Arrangement x = ranked.get(n);
			Arrangement y = ranked.get(n+1);
			successors.addAll(Arrays.asList(Breeder.crossover_arrangements(x, y)));
		}
		
		//check sizes match because of the +2step may have issues with odd/even size lists
		if(successors.size()<forebears.size()){
			
			Arrangement arr = new Arrangement(forebears.score());
			arr.randomise();
			successors.add(arr);
		}
		
		MonophonicFretGapEvolver ev = this.clone();
		if(++ev.current_generation >= target_generations){ ev.set_have_finished(); }
		Population successor_pop = new Population(ev, successors, forebears.score()); 
		rank_population_by_fretgap(successor_pop); //TODO: optimise this, it's being done twice for all in the middle of the population chain, once at their creation and once when their successors are created
		return successor_pop;
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
	public static void rank_population_by_fretgap(Population pop){
		for(Arrangement arr : pop){
			arr.assign_cost(cost_by_fretgap(arr));
		}
	}
	
	private int current_generation;
	private int target_generations;
}
