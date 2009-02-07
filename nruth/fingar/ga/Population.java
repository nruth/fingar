package nruth.fingar.ga;

import java.util.ArrayList;
import java.util.List;

import nruth.fingar.Arrangement;
import nruth.fingar.domain.music.Score;

public final class Population {

	private Evolver evolver;
	/**
	 * seeds an initial population
	 * @param score
	 */
	public Population(Score score, Evolver evolver) {
		this.evolver = evolver;
		this.population = evolver.initial_population();
//		population = new ArrayList<Arrangement>(POPULATION_SIZE);
//		
//		for(int i=0; i<POPULATION_SIZE; i++){
//			Arrangement individual = new Arrangement(score);
//			individual.randomise();
//			population.add(individual);
//		}		
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
	
	public List<Arrangement> results(){ return population; }
	private List<Arrangement> population;
}
