/**
	 
 */
package nruth.fingar.domain;

import java.util.HashMap;

/**
	@author nicholasrutherford
 */
public class Score {
	private HashMap<Float, Note> notes; //quick lookup of note at time
	private HashMap<Float, Float> durations; //quick lookup of note duration at time
	private float[] start_beats;//order the starting times for use in the above maps
	/**
    	@param notes
	 * @param timing 2d array of {start_beat, beats_duration}
     */
    public Score(Note[] notes, float[][] timing) {
    	if(timing.length != notes.length){ throw new IndexOutOfBoundsException("notes and durations mismatched, "+notes.length+" notes, "+timing.length +"durations");}
    	start_beats = new float[timing.length];
    	durations = new HashMap<Float, Float>(notes.length, 1.0f);
    	this.notes = new HashMap<Float, Note>(notes.length, 1.0f);
    	for(int idx=0; idx<notes.length; idx++){ 
    		start_beats[idx] = timing[idx][0]; 
    		durations.put(timing[idx][0], timing[idx][1]);
    		this.notes.put(timing[idx][0],notes[idx]);
    	}
    }

	/**
    	@param index notes in score, indexed from 1
    	@return the note at index
     */
    public Note getNote(int index) {
	    return notes.get(start_beats[index-1]);
    }

	/**
    	@param beats_from_start
    	@return the note at given beat (time from start)
     */
    public Note getNoteAtTime(float beats_from_start) {
	    return notes.get(beats_from_start); 
    }

	/**
    	last modified: 16 Nov 2008
    	@param f
    	@return
     */
    public float get_duration_of_note_at_time(float start_beat) {
	    return durations.get(start_beat);
    }
	
    public int getIntervalRange(){
		Note highest = notes.get(start_beats[0]);
		Note lowest = notes.get(start_beats[0]);
		
		for (Note note : notes.values()){
			if(note.compareTo(highest) < 0) highest = note;
			if(note.compareTo(lowest) > 0) lowest = note;
		}
		
		return lowest.compareTo(highest);
    }

    /**
     * get note start times for the score
     * @return float time values (from 0) marking note positions. This is copy by value, not reference, so changes are not passed on to the Score object (which would corrupt it)  
     */
	public float[] get_note_start_times() {	return start_beats.clone();	}
}
