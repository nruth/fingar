package nruth.fingar.tests;


/**

 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ArrangementSpec.class,
	FINGAR_Spec.class,
	MonophonicScales.class,
	nruth.fingar.domain.tests.AllDomainTests.class
})

public class AllTests {
    // the class remains completely empty, 
    // being used only as a holder for the above annotations	
}