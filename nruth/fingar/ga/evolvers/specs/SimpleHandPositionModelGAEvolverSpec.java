package nruth.fingar.ga.evolvers.specs;

import static org.junit.Assert.*;
import static nruth.fingar.ga.evolvers.SimpleHandPositionModelGAEvolver.*;

import java.util.Arrays;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.ga.Arrangement;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class SimpleHandPositionModelGAEvolverSpec {
	private Mockery context = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};

	/**
	 * this model is described in report chapter "Genetic Algorithm Design" section "Cost Functions and Fitness" subsection "Simple Hand Position Model"
	 */
	
	@Test
	public void testAssign_simple_hand_model_cost() {
		final FingeredNote note1 = context.mock(FingeredNote.class, "note1");
		final FingeredNote note2 = context.mock(FingeredNote.class, "note2");
		final Arrangement arr = context.mock(Arrangement.class);
		
    	context.checking(new Expectations() {{
    		oneOf (arr).iterator(); will(returnIterator(note1, note2));    		
    		
    		//lhp 3
    		oneOf (note1).finger(); will(returnValue(1));
    		oneOf (note1).fret(); will(returnValue(3));    		
    		
    		//lhp 5, delta from lhp3 = 2
    		oneOf (note2).finger(); will(returnValue(1));
    		oneOf (note2).fret(); will(returnValue(5));    		
    		oneOf (arr).assign_cost(2);
    	}});
		
    	assign_simple_hand_model_cost(arr);
    	
    	final FingeredNote note3 = context.mock(FingeredNote.class, "note3");
		final FingeredNote note4 = context.mock(FingeredNote.class, "note4");
    	context.checking(new Expectations() {{
    		oneOf (arr).iterator(); will(returnIterator(note1, note2, note3, note4));
    		
    		//lhp 3
    		atLeast(1).of (note1).finger(); will(returnValue(1));
    		atLeast(1).of (note1).fret(); will(returnValue(3));    		
    		
    		//lhp 5, delta from lhp3 = 2
    		atLeast(1).of (note2).finger(); will(returnValue(1));
    		atLeast(1).of (note2).fret(); will(returnValue(5)); 
    		
    		//lhp -2, delta from lhp5 = 7
    		atLeast(1).of (note3).finger(); will(returnValue(4));
    		atLeast(1).of (note3).fret(); will(returnValue(1));
    		
    		//lhp 10, delta from lhp-2 = 12
    		atLeast(1).of (note4).finger(); will(returnValue(3));
    		atLeast(1).of (note4).fret(); will(returnValue(12));
    		
    		oneOf (arr).assign_cost(2+7+12);
    	}});
		
    	assign_simple_hand_model_cost(arr);
    	
    	
	}

	@Test
	public void testLhp_of_position() {
		//test index finger positions
		assertEquals(3, lhp_of_fingered_fret(3, 1));
		assertEquals(12, lhp_of_fingered_fret(12, 1));
		
		//test 4th finger positions, -ves are valid (though somewhat unintuitive they do reflect the hand position)
		assertEquals(0, lhp_of_fingered_fret(3, 4));
		assertEquals(9, lhp_of_fingered_fret(12, 4));
		
		//test negative lhp positions
		assertEquals(0, lhp_of_fingered_fret(2, 3));
		assertEquals(-3, lhp_of_fingered_fret(0, 4));
	}
	
	@Test
	public void values_in_paper(){
		int[] fret = new int[]{3, 7, 1, 0};
		int[] finger = new int[]{4, 3, 3, 4};
		int[] lhps = new int[]{0, 5, -1, -3};
		
		for(int n=0; n<fret.length; n++){
			assertEquals("tab:simple_hand_position_model_with_zeros",lhps[n], lhp_of_fingered_fret(fret[n], finger[n]));
		}
		
		fret = new int[]{5, 7, 3, 10};
		finger = new int[]{1, 3, 3, 2};
		lhps = new int[]{5, 5, 1, 9};
		for(int n=0; n<fret.length; n++){
			assertEquals("tab:simple_hand_position_model_run",lhps[n], lhp_of_fingered_fret(fret[n], finger[n]));
		}
	}
}
