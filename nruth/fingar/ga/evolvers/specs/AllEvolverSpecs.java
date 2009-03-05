package nruth.fingar.ga.evolvers.specs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  EvolverSpec.class,
  MonophonicFretGapEvolverSpec.class,
  GoldbergRouletteWheelTest.class,
})

public class AllEvolverSpecs {
    // the class remains empty, 
    // being used only as a holder for the above annotations
}
