package nruth.fingar.ga.cost_functions.specs;

import static org.junit.Assert.*;

import org.junit.Test;

import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.cost_functions.CompositeCostFunction;
import nruth.fingar.ga.cost_functions.HeijinkNeckPositionCostFunction;
import nruth.fingar.ga.cost_functions.SimpleHandPositionModelCostFunction;
import nruth.fingar.ga.cost_functions.StringChangeCostFunction;
import nruth.fingar.ga.specs.ArrangementSpec;

public class CompositeCostFunctionSpec {
	@Test
	public void coefficients_available_for_feature_weights(){
		new CompositeCostFunction(1.0, 1.0, 1.0);
	}
	
	@Test
	public void weightings_affect_costs_correctly(){
		Arrangement arr1 = ArrangementSpec.test_arranger(ArrangementSpec.test_score());
		Arrangement arr2 = arr1.clone();
		
		assertEquals(new CompositeCostFunction(1.0, 0, 0).determine_cost(arr1),
		new HeijinkNeckPositionCostFunction().determine_cost(arr2));
		
		assertEquals(new CompositeCostFunction(0, 1.0, 0).determine_cost(arr1),
		new StringChangeCostFunction().determine_cost(arr2));
		
		assertEquals(new CompositeCostFunction(0, 0, 1.0).determine_cost(arr1),
		new SimpleHandPositionModelCostFunction().determine_cost(arr2));
	}
}
