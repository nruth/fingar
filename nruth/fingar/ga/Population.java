package nruth.fingar.ga;

import java.util.*;

import nruth.fingar.Arrangement;
import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.Score;
import nruth.fingar.ga.evolvers.Evolver;

/**
 * a population of genetic individuals
 * i.e. encapsulates a set of atomic solutions to the domain problem
 */
public final class Population implements Iterable<Arrangement>{
	/**
	 * seeds an initial population
	 * @param score
	 */
	public Population(Score score, Evolver evolver) {
		this.evolver = evolver;
		this.score = score;
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
	
	@Override
	public Iterator<Arrangement> iterator() {	return population.iterator();	}
	
	@Override
	public Population clone() {
		return new Population(this.score, evolver.clone());
	}

	@Override
	public boolean equals(Object object_to_check) {
		if(object_to_check == null) return false;
    	if(object_to_check == this) return true;
    	Population pop_to_check = (Population) object_to_check;
    	
    	//TODO: this doesn't work because it relies on list order equality and this is not required for the population. List is the wrong collection type.
    	return pop_to_check.population.equals(this.population); 
	}

	private Collection<Arrangement> population;
	private Evolver evolver;
	private Score score;
}
