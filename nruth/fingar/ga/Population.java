package nruth.fingar.ga;

import java.util.*;

import nruth.Helpers;
import nruth.fingar.domain.music.Score;
import nruth.fingar.ga.evolvers.Evolver;

/**
 * a population of genetic individuals
 * i.e. encapsulates a set of atomic solutions to the domain problem
 */
public class Population implements Iterable<Arrangement>, Cloneable{
	/**
	 * seeds an initial population
	 * @param score
	 * @param evolver
	 */
	public Population(Score score, Evolver evolver) {
		this(evolver,  evolver.initial_population(score), score);
	}
	
	/**
	 * used for repopulating
	 * @param evolver
	 * @param population
	 * @param score
	 */
	public Population(Evolver evolver, List<Arrangement> population, Score score){
		this.evolver = evolver;
		this.population = population;
		this.score = score;
	}
	
	public Population successor() {	return evolver.create_successor_population(this); }
	
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
	public List<Arrangement> view_arrangements() {	return Collections.unmodifiableList(population);	}
	
	public List<Arrangement> ranked() {
		List<Arrangement> view = new ArrayList<Arrangement>(population.size());
		view.addAll(population);
		Collections.sort(view, new Comparator<Arrangement>() {
			@Override
			public int compare(Arrangement o1, Arrangement o2) {
				return o1.cost() - o2.cost();
			}
		});
		return view;
	}
	
	public Score score() { return score; }
	
	private List<Arrangement> population;
	private Evolver evolver;
	private Score score;
}
