package nruth.fingar.ga.cost_functions.specs;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.*;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.domain.specs.TimedNoteSpec;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;
import nruth.fingar.ga.cost_functions.CostFunction;
import nruth.fingar.ga.cost_functions.HeijinkNeckPositionCostFunction;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(JMock.class)
public class HeijinkNeckPositionCostFunctionSpec {
	private Mockery context = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
	
	@Test
	public void low_neck_solutions_cheaper(){
		TimedNote[] notes = TimedNoteSpec.create_random_monophonic_arranged_notes(3);
		List<FingeredNote> fnotes = Arrays.asList(new FingeredNote(notes[0]), new FingeredNote(notes[1]), new FingeredNote(notes[2]));
		Arrangement high_pos = new Arrangement(fnotes);
		Arrangement low_pos = high_pos.clone();
		for(FingeredNote note : low_pos.fingered_notes().values()){ note.setFret(1); }
		for(FingeredNote note : high_pos.fingered_notes().values()){ note.setFret(10); }
		HeijinkNeckPositionCostFunction.assign_cost_to_arrangement(low_pos);
		HeijinkNeckPositionCostFunction.assign_cost_to_arrangement(high_pos);
		assertTrue(high_pos.cost() > low_pos.cost());
	}
	
	
	@Test
	public void costs_population(){
		TimedNote[] notes = TimedNoteSpec.create_random_monophonic_arranged_notes(3);
		List<FingeredNote> fnotes = Arrays.asList(new FingeredNote(notes[0]), new FingeredNote(notes[1]), new FingeredNote(notes[2]));
		final Arrangement high_pos = new Arrangement(fnotes);
		final Arrangement low_pos = high_pos.clone();
		final Population pop = context.mock(Population.class);
		
    	context.checking(new Expectations() {{
    		allowing (pop).iterator(); will(returnIterator(high_pos, low_pos));   
    	}});
		
    	for(FingeredNote note : low_pos.fingered_notes().values()){ note.setFret(1); }
		for(FingeredNote note : high_pos.fingered_notes().values()){ note.setFret(10); }
		CostFunction cfn = new HeijinkNeckPositionCostFunction();
		cfn.assign_cost(pop);
		assertTrue(high_pos.cost() > low_pos.cost());
	}
}
