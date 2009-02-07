package nruth.fingar.ga.evolvers;

import java.util.*;

import nruth.fingar.Arrangement;
import nruth.fingar.domain.music.Score;
import nruth.fingar.ga.Population;

public abstract class Evolver {
	
	/**
	 * @param score the domain problem data to base the population on
	 * @return a stochastic initial population of problem solutions
	 */
	public List<Arrangement> initial_population(Score score){
		int POPULATION_SIZE = 20;
		ArrayList<Arrangement> population = new ArrayList<Arrangement>(POPULATION_SIZE);
		
		for(int i=0; i<POPULATION_SIZE; i++){
			Arrangement individual = new Arrangement(score);
			individual.randomise();
			population.add(individual);
		}	
		return population;
	}

	/**
	 * reports whether the current population is the final population for this phenotype
	 * @return
	 */
	public boolean finished(){ return finished; }

	/**
	 * use this to state that your evolver has finished, 
	 * i.e. the current phenotype population is the final generation for this evolver
	 */
	protected void set_have_finished(){	this.finished  = true;	}
	
	/**
	 * create a successor population
	 * @param forebears the parent population to evolve from
	 * @return the child population derived from forebears
	 */
	public abstract List<Arrangement> evolve(Population forebears);
	
	/**
	 * create a copy of the object
	 * deep enough to allow it to run atomically in parallel (i.e. threadsafe)
	 */
	public abstract Evolver clone();
	
	private boolean finished = false;
}
