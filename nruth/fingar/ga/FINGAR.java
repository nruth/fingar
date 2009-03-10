package nruth.fingar.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import nruth.fingar.domain.music.Score;
import nruth.fingar.ga.evolvers.Breeder;
import nruth.fingar.ga.evolvers.Evolver;
import nruth.fingar.ga.evolvers.MonophonicFretGapEvolver;
import nruth.fingar.ga.evolvers.NDeepRandomEvolver;
import nruth.fingar.ga.evolvers.SimpleHandPositionModelGAEvolver;
import nruth.fingar.ga.probability.GoldbergRouletteWheel;

/**
 * this class sets the GA in motion
 * it is responsible for creating the initial population and setting it running
 * * Ideas: 
 * *	Using an observer to monitor the GA, which will report back when a solution is found will allow plug-in expansion into a distributed GA
 * *	This class could be modified to allow use of other processing types, such as a neural network or hill climber. A simple way to do this would be to override the process method, or to have a processor component given at construction which works out the results, called by the process method	
 * @author nicholasrutherford
 *
 */
public final class FINGAR {
	/**
	 * @param score the music to process
	 * @param evolver the evolution mechanism to use (fitness function, crossover, etc)
	 */
	public FINGAR(Score score, Evolver evolver) {
		this.score = score;
		this.evolver = evolver;
	}

	/**
	 * Uses the default evolultion mechanism
	 * @param score the music to process
	 */
	public FINGAR(Score score) {
		this(score, new SimpleHandPositionModelGAEvolver(400, 30, 0.5, 0.01)); 
	}

	/**
	 * Gets the fingering solutions.
	 * If processing has not yet occured it will be done before results are returned
	 * @return fingering solutions for the Score given at construction
	 */
	public List<Arrangement> results() {
		process();
		return results;
	}

	public Score get_score() {	return score;	}

	public boolean is_processing_finished() {	return finished;	}

	/**
	 * runs the optimisation process, if required
	 * @return true if processing occurred
	 */
	public boolean process() {
		if(!finished){
			Population population = new Population(score, evolver);
			boolean finished = false;
			while(!finished){ 
				population = population.successor();
				finished = population.evolver().is_halted();
			}
			results = population.view_arrangements();
			
			this.finished = true;
			return true;
		}
		else return false;
	}
	
	private final Score score;
	private boolean finished = false;
	private List<Arrangement> results;
	private Evolver evolver;
}