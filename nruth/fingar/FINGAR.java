package nruth.fingar;

import java.util.List;

import nruth.fingar.domain.Score;

/**
 * this class sets the GA in motion
 * it is responsible for creating the initial population and setting it running within an observer, which will report back when a solution is found
 * using the observer model will allow plug-in expansion into a distributed GA 
 * @author nicholasrutherford
 *
 */
public final class FINGAR {
	private final Score score;
	private boolean finished = false;
	
	public FINGAR(Score score) {
		this.score = score;
	}

	public List<Arrangement> getArrangements() {
		if(!finished){	process();	}
		
		return null;
	}

	public Score getScore() {	return score;	}

	public boolean is_processing_finished() {	return finished;	}

	public void process() {
		//1. do the processing here
		//2. store the results
		//3. update the processing done flag
		finished = true;
	}

}