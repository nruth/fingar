package nruth.fingar.ga.cost_functions.specs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	HeijinkNeckPositionCostFunctionSpec.class,
	MonophonicFretGapEvolverSpec.class,
	SimpleHandPositionModelGAEvolverSpec.class,
	StringChangeCostFunctionSpec.class,
})

public class AllCostFunctionSpecs {
    // the class remains empty, 
    // being used only as a holder for the above annotations
}
