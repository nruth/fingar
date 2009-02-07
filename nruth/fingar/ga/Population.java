package nruth.fingar.ga;

import java.util.*;

import nruth.fingar.Arrangement;
import nruth.fingar.domain.music.Score;
import nruth.fingar.ga.evolvers.Evolver;

/**
 * a population of genetic individuals
 * i.e. encapsulates a set of atomic solutions to the domain problem
 */
public final class Population {
	/**
	 * seeds an initial population
	 * @param score
	 */
	public Population(Score score, Evolver evolver) {
		this.evolver = evolver;
		this.population = evolver.initial_population(score);	
	}

//	public List<Arrangement> results(){ return population; }
	
//	public List<Arrangement> process(){
//		if(evolver.finished()) return results();
//		else return successor().process();
//	}
	
	public Population successor() {	return evolver.create_successor_population(this); }
	
//	/**
//	 * creates a successor generation for the given parent generation
//	 * @param parent
//	 */
//	private Population(Population forebears, Evolver evolver){
//		population = evolver.evolve(forebears);
//	}
	
	public int size(){ return population.size(); } 
	
	
	private List<Arrangement> population;
	private Evolver evolver;
}
