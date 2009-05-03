package nruth.fingar.ga.cost_functions;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;

public class HeijinkLowNeckPositionCostFunction extends CostFunction {	
	public int determine_cost(Arrangement arr){
		int fretsum=0;
		for(FingeredNote n : arr){	fretsum += n.fret(); }
		return fretsum;
	}
}
