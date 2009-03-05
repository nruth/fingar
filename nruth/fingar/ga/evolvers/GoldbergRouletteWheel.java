package nruth.fingar.ga.evolvers;

import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;

public final class GoldbergRouletteWheel {
	public GoldbergRouletteWheel(Population popl) {
		double total_fitness = 0.0; //w_f in design doc
		for(Arrangement i : popl){ total_fitness += fitness(i); }		
		wheel = new TreeMap<Double, Arrangement>();
		double key = 0.0;  
		for(Arrangement i : popl){
			double likelihood = fitness(i)/total_fitness;
			key += likelihood;
			wheel.put(key, i);
		}	
	}
	
	public Arrangement spin() {
		Double p = spinner.nextDouble(); 
		Arrangement individual = wheel.get(wheel.tailMap(p).firstKey());	
		if(individual == null){ throw new NullPointerException("GoldbergRouletteWheel failed to find element at "+p); }
		return individual;
	}
	
	/**
	 * f(i) in design docs
	 * @param i individual to evaluate fitness of
	 * @return inverse of i's cost
	 */
	private Double fitness(Arrangement i){ return (1.0/i.cost()); }
	
	private SortedMap<Double, Arrangement> wheel;
	private Random spinner = new Random();
	
}