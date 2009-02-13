package nruth.fingar.ga;

import java.util.*;

import nruth.Helpers;
import nruth.fingar.Arrangement;
import nruth.fingar.domain.music.Score;
import nruth.fingar.ga.evolvers.Evolver;

/**
 * a population of genetic individuals
 * i.e. encapsulates a set of atomic solutions to the domain problem
 */
public final class Population implements Iterable<Arrangement>, Cloneable{
	/**
	 * seeds an initial population
	 * @param score
	 */
	public Population(Score score, Evolver evolver) {
		this.evolver = evolver;
		this.population = evolver.initial_population(score);		
	}
	
//	public List<Arrangement> results(){ return population; }
	
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
	public Population clone() {	return new Population(this); }
	
	/**
	 * this contstructor is used internally in cloning
	 * @param to_clone
	 */
	private Population(Population to_clone){ 
		this.evolver = to_clone.evolver.clone();
		this.population = new ArrayList<Arrangement>(to_clone.population.size());
		for(Arrangement arr : to_clone.population ){ this.population.add(arr.clone()); }
	}

	@Override
	public boolean equals(Object object_to_check) {
		if(object_to_check == null) return false;
    	if(object_to_check == this) return true;
    	Population pop_to_check = (Population) object_to_check;
    	return Helpers.content_equality(this.population, pop_to_check.population);
	}

	@Override
	public int hashCode() {	return population.toString().hashCode(); }
	
	public String toString(){ return population.toString(); }

	public Evolver evolver(){ return evolver; }
	
	/**
	 * creates an immutable view of the population's contents
	 * @return
	 */
	public List<Arrangement> view_arrangements() {
		return Collections.unmodifiableList(population);
	}
	
	private List<Arrangement> population;
	private Evolver evolver;
}
