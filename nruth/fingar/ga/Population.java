package nruth.fingar.ga;

import java.util.ArrayList;
import java.util.List;

import nruth.fingar.Arrangement;
import nruth.fingar.domain.music.Score;

public class Population {

	/**
	 * seeds an initial population
	 * @param score
	 */
	public Population(Score score, int successor_generations) {
		this.generations_remaining = successor_generations;
		population = new ArrayList<Arrangement>(POPULATION_SIZE);
		
		for(int i=0; i<POPULATION_SIZE; i++){
			Arrangement individual = new Arrangement(score);
			individual.randomise();
			population.add(individual);
		}		
	}

	public List<Arrangement> process(){
		if(generations_remaining > 0){
			return new Population(this, generations_remaining-1).process();
		} else return results();
	}
	
	/**
	 * creates a successor generation for the given parent generation
	 * @param parent
	 */
	private Population(Population forebears, int successor_generations){
		generations_remaining = successor_generations;
		population = new ArrayList<Arrangement>(POPULATION_SIZE);

		// TODO this needs to be replaced with the crossover / cost function based evolution
		for(Arrangement parent : forebears.population){
			parent.randomise();
			population.add(parent);
		}
	}
	
	public List<Arrangement> results(){ return population; }
	private List<Arrangement> population;
	private final static int POPULATION_SIZE = 20;
	private final int generations_remaining;
}
