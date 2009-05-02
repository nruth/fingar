package nruth.fingar.ga.cost_functions;

import java.util.Collection;
import java.util.Iterator;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;

public class StringChangeCostFunction extends CostFunction {

	@Override
	public void assign_cost(Population population) {
		for(Arrangement i : population){ i.assign_cost(cost_notes(i.fingered_notes().values())); }

	}

	public static int string_gap(GuitarString s1, GuitarString s2){
		return Math.abs(s1.compareTo(s2));
	}
	
	public static int cost_notes(Collection<FingeredNote> notes){
		Iterator<FingeredNote> itr = notes.iterator();
		FingeredNote note = itr.next();
		FingeredNote previous_note = note;
		int string_sum = 0;
		
		while (itr.hasNext()){ //the first evaluation is 1 against 2, 1 by itself has 0 cost as a starting point.
			note = itr.next();
			string_sum += string_gap(note.string(), previous_note.string()); 
			previous_note = note;
		}
		return string_sum;
	}
	
}
