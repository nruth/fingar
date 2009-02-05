package nruth.fingar;

import java.util.ArrayList;
import java.util.List;

import nruth.fingar.domain.Score;

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
	private static final int POPULATION_SIZE = 20;
	
	private final Score score;
	private boolean finished = false;
	private List<Arrangement> results;
	
	public FINGAR(Score score) {
		this.score = score;
		this.results = new ArrayList<Arrangement>(POPULATION_SIZE);
	}

	public List<Arrangement> getArrangements() {
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
			//1. do the processing here
		
			//2. store the results
		
			
			//3. update the processing done flag
			this.finished = true;
			return true;
		}
		else return false;
	}
}