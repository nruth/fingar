package nruth.fingar.ga.evolvers;

import java.util.*;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.domain.music.Score;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;

public abstract class Evolver implements Cloneable {
	
	/**
	 * @param score the domain problem data to base the population on
	 * @return a stochastic initial population of problem solutions
	 */
	public List<Arrangement> initial_population(Score score){
		ArrayList<Arrangement> population = new ArrayList<Arrangement>(population_size);
		
		for(int i=0; i<population_size; i++){
			Arrangement individual = new Arrangement(score);
			individual.randomise();
			population.add(individual);
		}	
		return population;
	}

	protected void set_population_size(int population_size){
		this.population_size = population_size;
	}
	
	protected int population_size = 200;
	
	/**
	 * reports whether the current population is the final population for this island
	 * @return
	 */
	public boolean is_halted(){ return this.finished; }

	/**
	 * use this to state that your evolver has finished, 
	 * i.e. the current island population is the final generation for this evolver
	 */
	protected void set_has_finished(){	this.finished  = true;	}
	
	/**
	 * create a successor population. 
	 * Do not keep references to the parent population else garbage collection will not occur, which will create a memory leak 
	 * (unless you need the references for something, in which case expect to reduce your population size + generation total)
	 * @param forebears the parent population to evolve from
	 * @return the child population derived from forebears
	 */
	public abstract Population create_successor_population(Population forebears);
	
	/**
	 * gives some indication of the state of the population, e.g. its generation gap from the original population
	 * @return a number representing the population's evolver state history in some way
	 */
	public abstract int generation();
	
	/**
	 * create a copy of the object
	 * deep enough to allow it to run atomically in parallel (i.e. threadsafe)
	 * throws CloneNotSupportedException if subclass cannot be cloned
	 */
	public Evolver clone() { 
		Object clone = null;
		try {	clone = super.clone();	} 
		catch (CloneNotSupportedException e) {	e.printStackTrace(); }
		return (Evolver) clone;
	}
	
	private boolean finished = false;
}
