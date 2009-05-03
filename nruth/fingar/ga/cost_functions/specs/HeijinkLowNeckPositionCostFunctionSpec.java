package nruth.fingar.ga.cost_functions.specs;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.*;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.domain.specs.TimedNoteSpec;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.cost_functions.HeijinkLowNeckPositionCostFunction;

import org.junit.Test;
public class HeijinkLowNeckPositionCostFunctionSpec {
	@Test
	public void low_neck_solutions_cheaper(){
		TimedNote[] notes = TimedNoteSpec.create_random_monophonic_arranged_notes(3);
		List<FingeredNote> fnotes = Arrays.asList(new FingeredNote(notes[0]), new FingeredNote(notes[1]), new FingeredNote(notes[2]));
		Arrangement high_pos = new Arrangement(fnotes);
		Arrangement low_pos = high_pos.clone();
		for(FingeredNote note : low_pos.fingered_notes().values()){ note.setFret(1); }
		for(FingeredNote note : high_pos.fingered_notes().values()){ note.setFret(10); }
		assertTrue(new HeijinkLowNeckPositionCostFunction().determine_cost(high_pos) > new HeijinkLowNeckPositionCostFunction().determine_cost(low_pos));
	}
	
	
	@Test
	public void costs_population(){
		TimedNote[] notes = TimedNoteSpec.create_random_monophonic_arranged_notes(3);
		List<FingeredNote> fnotes = Arrays.asList(new FingeredNote(notes[0]), new FingeredNote(notes[1]), new FingeredNote(notes[2]));
		Arrangement high_pos = new Arrangement(fnotes);
		Arrangement low_pos = high_pos.clone();
		
    	for(FingeredNote note : low_pos.fingered_notes().values()){ note.setFret(1); }
		for(FingeredNote note : high_pos.fingered_notes().values()){ note.setFret(10); }

		assertTrue(new HeijinkLowNeckPositionCostFunction().determine_cost(high_pos)> new HeijinkLowNeckPositionCostFunction().determine_cost(low_pos));
	}
}
