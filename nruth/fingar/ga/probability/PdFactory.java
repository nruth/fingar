package nruth.fingar.ga.probability;

import nruth.fingar.ga.Population;

public interface PdFactory{
	public ProbabilityDistribution probability_distribution(Population population);
}