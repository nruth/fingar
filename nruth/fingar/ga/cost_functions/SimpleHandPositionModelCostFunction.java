package nruth.fingar.ga.cost_functions;

import java.util.Iterator;
import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;


public class SimpleHandPositionModelCostFunction extends CostFunction{
	public void assign_cost(Population population) {
		for(Arrangement arr: population){ assign_simple_hand_model_cost(arr); }
	}
	
	public static void assign_simple_hand_model_cost(Arrangement arr){
		//this is the deltas process described in paper
		Iterator<FingeredNote> itr = arr.iterator();
		FingeredNote note = itr.next();
		FingeredNote previous_note = note;
		int delta_sum = 0;
		
		while (itr.hasNext()){ //the first evaluation is 1 against 2, 1 by itself has 0 cost as a starting point.
			note = itr.next();
			int lhp_this_n = lhp_of_fingered_fret(note.fret(), note.finger());
			int lhp_previous_n = lhp_of_fingered_fret(previous_note.fret(), previous_note.finger());
			if(note.fret()==0){
				// don't cost 0 fret and don't advance previous note, cost across the open string as though it isn't there
				// this is a naive costing method, and is documented as such in the design report
			}else{
				delta_sum += Math.abs(lhp_this_n - lhp_previous_n); 
				previous_note = note;
			}
		}
		
		arr.assign_cost(delta_sum);	
	}
	
	public static int lhp_of_fingered_fret(int fret, int finger){
		return fret - (finger-1);
	}
	
	public String toString(){
		return this.getClass().getSimpleName();
	}
}