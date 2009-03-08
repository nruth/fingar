package nruth.fingar.ga;

import java.util.*;

import nruth.fingar.FingeredNote;
import nruth.fingar.domain.music.Score;
import nruth.fingar.domain.music.TimedNote;

/**
 * holds an individual solution to the guitar fingering problem
 * stores tablature, timing and finger positions
 * @author nicholasrutherford
 *
 */
public class Arrangement implements Iterable<FingeredNote>, Cloneable{
	private Score score;
	private TreeMap<Float, FingeredNote> notes_starting_at;
	private int cost=-1;
	
	@Override
	public boolean equals(Object obj_to_check) {
		if(obj_to_check == null) return false;
		if(this == obj_to_check) return true;
		Arrangement arr_to_check = (Arrangement) obj_to_check;
		return (arr_to_check.notes_starting_at.equals(this.notes_starting_at));
	}

	/**
	 * creates a mapping of fingerednotes for the timednotes in the score
	 * does not populate the mapping with values. for an initial population call randomise() on the instance
	 * @param score
	 */
	public Arrangement(Score score) {
		this.score = score;
		
		this.notes_starting_at = new TreeMap<Float, FingeredNote>();
		for(TimedNote note : score){ notes_starting_at.put(note.start_beat(), new FingeredNote(note)); }
	}
	
	/**
	 * @return the number of distinct timed notes in the arrangement 
	 */
	public int size() { return score.size(); }
	
	public Iterator<FingeredNote> iterator() {
		return Collections.unmodifiableMap(notes_starting_at).values().iterator();
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		Float[] keys = notes_starting_at.keySet().toArray(new Float[notes_starting_at.size()]);
		Arrays.sort(keys);
		for(Float key : keys){
			str.append(notes_starting_at.get(key).toString()+"\n");
		}
		return str.toString();
	}

	/**
	 * stochastically allocates valid fingering data for the score
	 */
	public void randomise() {
		for(FingeredNote note : notes_starting_at.values()){	note.randomise_fingering();	}
	}
	
	@Override
	public int hashCode() { return toString().hashCode(); }

	public Arrangement clone(){
		Arrangement clone;
		//final class so can catch the error, it shouldn't happen
		try { clone = (Arrangement)super.clone(); } catch (CloneNotSupportedException e) { return null; }

		clone.notes_starting_at = new TreeMap<Float, FingeredNote>();
		for(FingeredNote note : this.notes_starting_at.values()){
			//makes use of the starting beat (which is the map key) being in each FingeredNote object
			clone.notes_starting_at.put(note.start_beat(), note.clone());
		}
		//may need to reset the cost to -1 at some point, but not now

		//no need to clone score, it's immutable
		return clone;
	}

	public void assign_cost(int cost) { 	this.cost = cost;	}
	
	public int cost(){ 
		if(this.cost == -1) throw new NullPointerException("cost not initialised");
		else return this.cost; 
	}

	/**
	 * - be careful with this! does not prevent list changes
	 * @return mutable access to the notes
	 */
	public SortedMap<Float, FingeredNote> fingered_notes() {
		return notes_starting_at;
	}
}
