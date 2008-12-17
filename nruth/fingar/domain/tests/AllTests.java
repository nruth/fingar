/**
	 
 */
package nruth.fingar.domain.tests;

import nruth.fingar.domain.tests.NoteSpec.NoteFactory;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  NoteSpec.class,
  NamedNoteSpec.class,
  ScoreSpec.class,
  NoteFactory.class,
})


public class AllTests {
    // the class remains completely empty, 
    // being used only as a holder for the above annotations	
}
