package nruth.fingar.ga.cost_functions;

import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;

public abstract class CostFunction {
	public abstract int determine_cost(Arrangement individual);
	
	/**
	 * mutates each individual in population p to have a cost allocated
	 * @param p
	 */
	public void evaluate(Population p){
		for (Arrangement a : p){ a.assign_cost(determine_cost(a));	}
	}
}
