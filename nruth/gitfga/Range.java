/**
	 
 */
package nruth.gitfga;

import nruth.gitfga.Notes.Note;
import static junit.framework.Assert.*;
/** The range of notes used in the piece mapped to fingering positions for a particular octave setting
	@author nicholasrutherford
	
 */
public final class Range {
	public Position[] getPositions(Note note, int octave){ //TODO test
		return range[octave-1].getPositionsForNote(note);
	}
	
	public int getOctaveAtPosition(Position p){
		fail("incomplete");
		return 0;
	}
	
	private Octave[] range;
}
