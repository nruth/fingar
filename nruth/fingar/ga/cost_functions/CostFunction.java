package nruth.fingar.ga.cost_functions;

import nruth.fingar.ga.Population;

public abstract class CostFunction {
	/**
	 * assign cost values to each individual in population
	 * @param population the population to be costed (which will mutate its objects using their assign cost methods)
	 */
	public abstract void assign_cost(Population population);
}
