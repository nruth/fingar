package nruth.fingar.ga.cost_functions;

import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;

public class CompositeCostFunction extends CostFunction {



	public CompositeCostFunction(int neckposition_coefficient,
			int string_change_coefficient, int lhp_coefficient) {
		this.neckposition_coefficient = neckposition_coefficient;
		this.string_change_coefficient = string_change_coefficient;
		this.lhp_coefficient = lhp_coefficient;
	}

	private float neckposition_coefficient, string_change_coefficient, lhp_coefficient;

	@Override
	public int determine_cost(Arrangement individual) {
		return 	Math.round(
			neckposition_coefficient * (new HeijinkPenaliseHighNeckPositionCostFunction().determine_cost(individual))
			+ string_change_coefficient*(new StringChangeCostFunction().determine_cost(individual))
			+ lhp_coefficient*(new SimpleHandPositionModelCostFunction().determine_cost(individual))
			);
	}
	
}