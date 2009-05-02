package nruth.fingar.ga.evolvers.specs;

import nruth.fingar.ga.cost_functions.MonophonicFretGapEvolverSpec;
import nruth.fingar.ga.cost_functions.SimpleHandPositionModelGAEvolverSpec;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  EvolverSpec.class,
  BreederSpec.class, 
  MonophonicFretGapEvolverSpec.class,
  GoldbergRouletteWheelTest.class,
  GeneticAlgorithmEvolverSpec.class, 
  SimpleHandPositionModelGAEvolverSpec.class,
})

public class AllEvolverSpecs {
    // the class remains empty, 
    // being used only as a holder for the above annotations
}
