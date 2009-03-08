package nruth.fingar.ga.probability;

import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;

public final class GoldbergRouletteWheel implements ProbabilityDistribution{
	public static class WheelFactory implements PdFactory{
		@Override
		public ProbabilityDistribution probability_distribution(
				Population population) {
			return new GoldbergRouletteWheel(population);
		}
	}
	
	
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
	
	/**
	 * get a random individual according to the wheel's probability distribution
	 * @return an individual from the population provided to the wheel at construction, selected randomly according to fitness
	 */
	public Arrangement next_individual() {	return get_individual_at_cpd(spinner.nextDouble()); }
	
	/**
	 * select an individual given a particular cpd value (between 0 and 1)
	 * @param cpd the cumulative probability value to look up, between 0 and 1.
	 * @return the individual whose wheel region the cpd is within
	 */
	public Arrangement get_individual_at_cpd(double cpd){
		Arrangement individual = wheel.get(wheel.tailMap(cpd).firstKey());	
		if(individual == null){ throw new NullPointerException("GoldbergRouletteWheel failed to find element at "+cpd); }
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