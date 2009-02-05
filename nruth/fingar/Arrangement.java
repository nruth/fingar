package nruth.fingar;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import nruth.fingar.domain.ArrangedNote;
import nruth.fingar.domain.Score;
import nruth.fingar.domain.Assumptions.STRINGS;

/**
 * holds an individual solution to the guitar fingering problem
 * stores tablature, timing and finger positions
 * @author nicholasrutherford
 *
 */
public class Arrangement implements Iterable<FingeredNote>{
	private final Score score;
	private List<FingeredNote> notes;
	
	public Arrangement(Score score) {
		this.score = score;
		
		this.notes = new LinkedList<FingeredNote>();
		
		for(ArrangedNote note : score){
			notes.add(new FingeredNote(note));
		}
	}
	
	public Score score(){ return score; }

	public int size() { return score.size(); }
	
	public Iterator<FingeredNote> iterator() {
		return new Iterator<FingeredNote>() {
			private int n = 1;
			public FingeredNote next() {
				return new FingeredNote(score.get_nth_note(n++));
			}
		
			public boolean hasNext() {
				return n <= score.size(); 
			}

			public void remove() {
				throw new UnsupportedOperationException("this collection is immutable");
			}
		};
	}	
}
