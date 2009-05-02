package nruth.fingar.ga.cost_functions;

import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;

public class CompositeCostFunction extends CostFunction {

	public CompositeCostFunction(double neckposition_coefficient, double string_change_coefficient, double lhp_coefficient) {

	}

	private double neckposition_coefficient, string_change_coefficient, lhp_coefficient;

	@Override
	public int determine_cost(Arrangement individual) {
		return 0;
	}
	
}