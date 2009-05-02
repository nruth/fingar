package nruth.fingar.ga.cost_functions;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;

public class HeijinkNeckPositionCostFunction extends CostFunction {

	@Override
	public void assign_cost(Population population) {
		for(Arrangement arr : population){
			assign_cost_to_arrangement(arr);
		}
	}
	
	public static void assign_cost_to_arrangement(Arrangement arr){
		int fretsum=0;
		for(FingeredNote n : arr){	fretsum += n.fret(); }
		arr.assign_cost(fretsum);
	}

}
