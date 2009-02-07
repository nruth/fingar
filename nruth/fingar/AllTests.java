package nruth.fingar;


/**

 */


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	nruth.fingar.domain.specs.AllDomainTests.class,
	nruth.fingar.ga.specs.AllGaSpecs.class,
	nruth.fingar.specs.AllFeatureSpecs.class,
})

public class AllTests {
    // the class remains empty, 
    // being used only as a holder for the above annotations	
}