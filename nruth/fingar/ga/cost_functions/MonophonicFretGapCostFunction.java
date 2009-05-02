package nruth.fingar.ga.cost_functions;

import java.util.Iterator;
import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.ga.Arrangement;

public final class MonophonicFretGapCostFunction extends CostFunction {

	/**
	 * @param note_1
	 * @param note_2
	 * @return the absolute fret distance (magnitude) from fret of note_1 to note_2
	 */
	public static int fretgap(FingeredNote note_1, FingeredNote note_2) {
		return Math.abs(note_1.fret() - note_2.fret());
	}

	public int determine_cost(Arrangement individual) {
		int cost = 0;
		Iterator<FingeredNote> itr = individual.iterator();  
		FingeredNote n;
		FingeredNote n2 = itr.next() ;
		while(itr.hasNext()){
			n = n2;
			n2 = itr.next();
			cost += fretgap(n, n2);
		}
		return cost;
	}
}
