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
  TimedNoteSpec.class,
})


public class AllDomainTests {
    // the class remains empty, 
    // being used only as a holder for the above annotations	
}
