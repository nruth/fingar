package nruth.fingar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import nruth.fingar.domain.music.Score;
import nruth.fingar.ga.Population;
import nruth.fingar.ga.evolvers.Evolver;
import nruth.fingar.ga.evolvers.NDeepRandomEvolver;

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
	
	private final Score score;
	private boolean finished = false;
	private List<Arrangement> results;
	private List<Population> islands;
	private Evolver evolver;
	
	/**
	 * @param score the music to process
	 * @param evolver the evolution mechanism to use (fitness function, crossover, etc)
	 */
	public FINGAR(Score score, Evolver evolver) {
		this.score = score;
	}

	public FINGAR(Score score) {
		this(score, new NDeepRandomEvolver(5)); //TODO: replace this with a simple evolver that uses a fitness function, or a class var for the best one to use.
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
			islands = new LinkedList<Population>();
			islands.add(new Population(score, evolver.clone()));
			
			results = islands.get(0).process();
			
			this.finished = true;
			return true;
		}
		else return false;
	}
}