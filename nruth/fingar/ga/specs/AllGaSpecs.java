package nruth.fingar.ga.specs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  EvolverSpec.class,
  PopulationSpec.class,
  FINGAR_Spec.class,
})

public class AllGaSpecs {
    // the class remains empty, 
    // being used only as a holder for the above annotations
}
