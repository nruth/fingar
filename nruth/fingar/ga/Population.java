package nruth.fingar.ga;

import nruth.fingar.Arrangement;
import nruth.fingar.domain.music.Score;

public class Population {

	/**
	 * seeds an initial population
	 * @param score
	 */
	public Population(Score score, int successor_generations) {
		population = new Arrangement(score);
		population.randomise();
	}

	/**
	 * creates a successor generation for the given parent generation
	 * @param parent
	 */
	private Population(Population parent, int successor_generations){
		population.evolve(new FitnessFunction());
	}
	
	private Arrangement population;
}
