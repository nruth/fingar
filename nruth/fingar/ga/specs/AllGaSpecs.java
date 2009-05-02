package nruth.fingar.ga.specs;

import nruth.fingar.ga.cost_functions.specs.AllCostFunctionSpecs;
import nruth.fingar.ga.evolvers.specs.AllEvolverSpecs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ArrangementSpec.class,
	AllCostFunctionSpecs.class,
	AllEvolverSpecs.class,
	PopulationSpec.class,
	FINGAR_Spec.class,
	BestResultSetSpec.class,
})

public class AllGaSpecs {
    // the class remains empty, 
    // being used only as a holder for the above annotations
}
