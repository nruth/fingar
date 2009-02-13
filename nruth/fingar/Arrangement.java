package nruth.fingar;

import java.util.*;
import nruth.fingar.domain.music.Score;
import nruth.fingar.domain.music.TimedNote;

/**
 * holds an individual solution to the guitar fingering problem
 * stores tablature, timing and finger positions
 * @author nicholasrutherford
 *
 */
public final class Arrangement implements Iterable<FingeredNote>, Cloneable{
	private Score score;
	private TreeMap<Float, FingeredNote> notes_starting_at;
//	private TreeSet<FingeredNote> note_fingerings;
	
	@Override
	public boolean equals(Object obj_to_check) {
		if(obj_to_check == null) return false;
		if(this == obj_to_check) return true;
		Arrangement arr_to_check = (Arrangement) obj_to_check;
		return (arr_to_check.score.equals(this.score) && 
				arr_to_check.notes_starting_at.equals(this.notes_starting_at)
		);
	}

	public Arrangement(Score score) {
		this.score = score;
		
		this.notes_starting_at = new TreeMap<Float, FingeredNote>();
		for(TimedNote note : score){ notes_starting_at.put(note.start_beat(), new FingeredNote(note)); }
	}
	
	public int size() { return score.size(); }
	
	public Iterator<FingeredNote> iterator() {
		return Collections.unmodifiableMap(notes_starting_at).values().iterator();
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		for(FingeredNote note : notes_starting_at.values()){
			str.append(note.toString()+"\n");
		}
		return str.toString();
	}

	/**
	 * stochastically allocates valid fingering data for the score
	 */
	public void randomise() {
		for(FingeredNote note : notes_starting_at.values()){	note.randomise_fingering();	}
	}
	
	public Arrangement clone(){
		Arrangement clone;
		//final class so can catch the error, it shouldn't happen
		try { clone = (Arrangement)super.clone(); } catch (CloneNotSupportedException e) { return null; }

		clone.notes_starting_at = new TreeMap<Float, FingeredNote>();
		for(FingeredNote note : this.notes_starting_at.values()){
			//makes use of the starting beat (which is the map key) being in each FingeredNote object
			clone.notes_starting_at.put(note.start_beat(), note.clone());
		}

		//no need to clone score, it's immutable
		return clone;
	}
}
