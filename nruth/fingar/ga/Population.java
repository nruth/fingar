package nruth.fingar.ga;

import java.util.ArrayList;
import java.util.List;

import nruth.fingar.Arrangement;
import nruth.fingar.domain.music.Score;
import nruth.fingar.ga.evolvers.Evolver;

public final class Population {
	/**
	 * seeds an initial population
	 * @param score
	 */
	public Population(Score score, Evolver evolver) {
		this.evolver = evolver;
		this.population = evolver.initial_population(score);	
	}

	public List<Arrangement> process(){
		if(evolver.finished()) return results();
		else return new Population(this, evolver).process();
	}
	
	/**
	 * creates a successor generation for the given parent generation
	 * @param parent
	 */
	private Population(Population forebears, Evolver evolver){
		population = evolver.evolve(forebears);
	}
	
	public int size(){ return population.size(); } 
	
	public List<Arrangement> results(){ return population; }
	private List<Arrangement> population;
	private Evolver evolver;
}
