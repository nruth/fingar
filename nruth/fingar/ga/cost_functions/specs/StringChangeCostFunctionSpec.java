package nruth.fingar.ga.cost_functions.specs;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.domain.specs.TimedNoteSpec;
import nruth.fingar.ga.*;
import nruth.fingar.ga.cost_functions.CostFunction;
import nruth.fingar.ga.cost_functions.StringChangeCostFunction;
import org.junit.Test;

import static nruth.fingar.domain.guitar.Guitar.GuitarString.*;
import static org.junit.Assert.*;

public class StringChangeCostFunctionSpec {
    
	@Test
	public void string_gaps_determined_correctly(){
		assertEquals(0, StringChangeCostFunction.string_gap(LOW_E, LOW_E));
		assertEquals(1, StringChangeCostFunction.string_gap(LOW_E, A));
		assertEquals(1, StringChangeCostFunction.string_gap(A, LOW_E));
		assertEquals(5, StringChangeCostFunction.string_gap(LOW_E, HIGH_E));
		assertEquals(5, StringChangeCostFunction.string_gap(HIGH_E,LOW_E));
		assertEquals(2, StringChangeCostFunction.string_gap(D,B));
	}
	
	@Test
	public void arrangement_costed_correctly(){
		TimedNote[] notes = TimedNoteSpec.create_random_monophonic_arranged_notes(3);
		List<FingeredNote> fnotes = Arrays.asList(new FingeredNote(notes[0]), new FingeredNote(notes[1]), new FingeredNote(notes[2]));
		Iterator<FingeredNote> itr = fnotes.iterator();
    	itr.next().setString(GuitarString.A);	itr.next().setString(GuitarString.B);	itr.next().setString(GuitarString.A);    	
    	assertEquals(6, StringChangeCostFunction.cost_notes(fnotes));
	}
	
	@Test
	public void costs_population(){
		TimedNote[] notes = TimedNoteSpec.create_random_monophonic_arranged_notes(3);
		List<FingeredNote> fnotes = Arrays.asList(new FingeredNote(notes[0]), new FingeredNote(notes[1]), new FingeredNote(notes[2]));
		Arrangement high_cost = new Arrangement(fnotes);
		Arrangement low_cost = high_cost.clone();

    	Iterator<FingeredNote> itr = high_cost.fingered_notes().values().iterator();
    	itr.next().setString(GuitarString.A);	itr.next().setString(GuitarString.B);	itr.next().setString(GuitarString.A);
    	
    	itr = low_cost.fingered_notes().values().iterator();
    	itr.next().setString(GuitarString.G);	itr.next().setString(GuitarString.G);	itr.next().setString(GuitarString.G);
    	
		CostFunction cfn = new StringChangeCostFunction();
		assertTrue(cfn.determine_cost(high_cost)> cfn.determine_cost(low_cost));
	}
}
