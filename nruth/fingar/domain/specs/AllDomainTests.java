/**
	 
 */
package nruth.fingar.domain.specs;

import nruth.fingar.domain.specs.NoteSpec.NoteFactory;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	GuitarStringTest.class,
	GuitarTest.class,
	NamedNoteSpec.class,
	NoteSpec.class,
	NoteFactory.class,
	PositionSpec.class,
	ScoreSpec.class,
	TimedNoteSpec.class,
	FingeredNoteSpec.class,
})


public class AllDomainTests {
    // the class remains empty, 
    // being used only as a holder for the above annotations	
}
