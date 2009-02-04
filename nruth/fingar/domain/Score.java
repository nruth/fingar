/**
	 
 */
package nruth.fingar.domain;

import java.util.HashMap;

/**
	@author nicholasrutherford
 */
public final class Score {
	private HashMap<Float, Note> notes; //quick lookup of note at time
	private HashMap<Float, Float> durations; //quick lookup of note duration at time
	private float[] start_beats;//order the starting times for use in the above maps

	public Score(ArrangedNote[] arranged_notes) {
		this.start_beats = new float[arranged_notes.length];
    	this.durations = new HashMap<Float, Float>(arranged_notes.length, 1.0f);    	
    	this.notes = new HashMap<Float, Note>(arranged_notes.length, 1.0f);
    	
    	int idx=0;
		for(ArrangedNote note : arranged_notes){
			if(idx>0 && note.start_beat() < this.start_beats[idx-1]){
    			throw new IndexOutOfBoundsException("Invalid start time: note "+note+" begins at "+start_beats+" and lasts for "+durations+". Previous note began at " + this.start_beats[idx-1]);
    		}
    		
    		this.start_beats[idx] = note.start_beat(); 
    		this.durations.put(note.start_beat(), note.duration());
    		this.notes.put(note.start_beat(),note.note());
			
			idx++;
		}
	}

	/**
    	@param index notes in score, indexed from 1
    	@return the note at index
     */
    public ArrangedNote get_nth_note(int index) {
    	float start_beat = start_beats[index-1];
	    return new ArrangedNote(notes.get(start_beat), start_beat, durations.get(start_beat));
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
    	@param start_beat
    	@return the duration (beats) of the note found starting at start_beat
     */
    public float get_duration_of_note_at_time(float start_beat) {
	    return durations.get(start_beat);
    }
	
    /**
     * 
     * @return the number of intervals between the highest and lowest note in the score
     */
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

	public int size() {
		return notes.size();
	}
}
