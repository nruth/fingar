/**
	 
 */
package nruth.fingar.domain.music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
	@author nicholasrutherford
 */
public final class Score implements Iterable<TimedNote>{
	private ArrayList<TimedNote> arranged_notes; //could use a tree sorted by start_beat ?
	
	public Score(TimedNote[] arranged_notes) {
		this.arranged_notes = new ArrayList<TimedNote>(arranged_notes.length);
    	
    	int idx=0;
		for(TimedNote note : arranged_notes){
			if(idx>0 && note.start_beat() < this.arranged_notes.get(idx-1).start_beat()){
    			throw new IndexOutOfBoundsException("Invalid start time: note "+note+" begins at "+note.start_beat()+" and lasts for "+note.duration()+". Previous note began at " + this.arranged_notes.get(idx-1));
    		}
			this.arranged_notes.add(new TimedNote(note.note(), note.start_beat(), note.duration()));
			idx++;
		}
	}

	/**
    	@param index notes in score, indexed from 1
    	@return the note at index
     */
    public TimedNote get_nth_note(int index) {
    	return arranged_notes.get(index-1);
    }

	/**
    	@param beats_from_start
    	@return the note at given beat (time from start)
     */
    public TimedNote getNoteAtTime(float beats_from_start) {
	    for(TimedNote note : arranged_notes){
	    	if(note.start_beat() == beats_from_start) return note;
	    }
	    throw new IndexOutOfBoundsException("no note found at this time");
    }

	/**
    	last modified: 16 Nov 2008
    	@param start_beat
    	@return the duration (beats) of the note found starting at start_beat
     */
    public float get_duration_of_note_at_time(float start_beat) {
	    return getNoteAtTime(start_beat).duration();
    }
	
    /**
     * 
     * @return the number of intervals between the highest and lowest note in the score
     */
    public int getIntervalRange(){
		Note highest = arranged_notes.get(1).note();
		Note lowest = arranged_notes.get(1).note();
		
		for (TimedNote note : arranged_notes){
			if(note.note().compareTo(highest) < 0) highest = note.note();
			if(note.note().compareTo(lowest) > 0) lowest = note.note();
		}
		return lowest.compareTo(highest);
    }

	public int size() {
		return arranged_notes.size();
	}

	public Score clone(){
		Score clone;
		//final class so can catch the error, shouldn't happen
		try { clone = (Score) super.clone(); } catch (CloneNotSupportedException e) { return null; }
		clone.arranged_notes = (ArrayList<TimedNote>) arranged_notes.clone();
		return clone;
	}
	
	public Iterator<TimedNote> iterator() {
		return new Iterator<TimedNote>() {
			private Iterator<TimedNote> itr = arranged_notes.iterator();
			
			/**
			 * not supported for this collection
			 */
			public void remove() { throw new UnsupportedOperationException();	}
		
			public TimedNote next() {	return itr.next();	}
		
			public boolean hasNext() {	return itr.hasNext();	}
		};
	}
}
