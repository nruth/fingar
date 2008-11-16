/**
	 
 */
package nruth.gitfga.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  GeneTest.class, GeneTest.GeneFactory.class,
  NotesTest.class, NotesTest.NotesFactory.class,
  PopulationTest.class,
  GenotypeTest.class
})
@Deprecated
public class AllTests {
    // the class remains completely empty, 
    // being used only as a holder for the above annotations	
}
