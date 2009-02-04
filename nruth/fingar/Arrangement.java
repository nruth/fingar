package nruth.fingar;

import java.util.Collection;
import java.util.Iterator;
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
public class Arrangement implements Iterable<ArrangedNote>{
	private ArrangedNote[] notes; 
	
	public Arrangement(Score score) {
		for()
	}

	public Iterator<ArrangedNote> iterator() {
		return new Iterator<ArrangedNote>() {
			public void remove() {
				// TODO Auto-generated method stub
		
			}
		
			public ArrangedNote next() {
				// TODO Auto-generated method stub
				return null;
			}
		
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
}
