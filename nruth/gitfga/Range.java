/**
	 
 */
package nruth.gitfga;

import nruth.gitfga.Notes.Note;
import static junit.framework.Assert.*;
/** encapsulates the range of notes used in the piece mapped to fingering positions 
 * for a particular octave setting
 * i.e. for a piece with a single octave range, whether it will be played high, middle or low on the neck, and which strings.
	@author nicholasrutherford	
 */
@Deprecated
public final class Range {
	public Position[] getPositions(Note note, int octave){ 
		return range[octave-1].getPositionsForNote(note);
	}
	
	public int getOctaveAtPosition(Position p){
		fail("incomplete");
		return 0;
	}
	
	private Octave[] range;
}
