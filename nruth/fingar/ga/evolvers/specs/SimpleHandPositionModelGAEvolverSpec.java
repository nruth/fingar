package nruth.fingar.ga.evolvers.specs;

import static org.junit.Assert.*;
import static nruth.fingar.ga.evolvers.SimpleHandPositionModelGAEvolver.*;

import java.util.Arrays;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;
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
    		atLeast(1).of (note1).finger(); will(returnValue(1));
    		atLeast(1).of (note1).fret(); will(returnValue(3));    		
    		
    		//lhp 5, delta from lhp3 = 2
    		atLeast(1).of (note2).finger(); will(returnValue(1));
    		atLeast(1).of (note2).fret(); will(returnValue(5));    		
    		oneOf (arr).assign_cost(2);
    	}});
		
    	assign_simple_hand_model_cost(arr);
	}
	
	@Test
	public void assign_simple_hand_model_cost2(){
		final FingeredNote note1 = context.mock(FingeredNote.class, "note1");
		final FingeredNote note2 = context.mock(FingeredNote.class, "note2");
    	final FingeredNote note3 = context.mock(FingeredNote.class, "note3");
		final FingeredNote note4 = context.mock(FingeredNote.class, "note4");
		final Arrangement arr = context.mock(Arrangement.class);
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
	
	@Test
	public void perfect_c_major_costed_as_0(){
		final FingeredNote note1 = context.mock(FingeredNote.class, "note1");
		final FingeredNote note2 = context.mock(FingeredNote.class, "note2");
		final FingeredNote note3 = context.mock(FingeredNote.class, "note3");
		final FingeredNote note4 = context.mock(FingeredNote.class, "note4");
		final FingeredNote note5 = context.mock(FingeredNote.class, "note5");
		final FingeredNote note6 = context.mock(FingeredNote.class, "note6");
		final FingeredNote note7 = context.mock(FingeredNote.class, "note7");
		final FingeredNote note8 = context.mock(FingeredNote.class, "note8");
		
		final Arrangement arr = context.mock(Arrangement.class);
		
    	context.checking(new Expectations() {{
    		oneOf (arr).iterator(); will(returnIterator(note1, note2, note3, note4, note5, note6, note7, note8));    		
    	
    		atLeast(1).of (note1).finger(); will(returnValue(2)); atLeast(1).of (note1).fret(); will(returnValue(3));    		    		
    		atLeast(1).of (note2).finger(); will(returnValue(4)); atLeast(1).of (note2).fret(); will(returnValue(5));
    		atLeast(1).of (note3).finger(); will(returnValue(1)); atLeast(1).of (note3).fret(); will(returnValue(2));    		
    		atLeast(1).of (note4).finger(); will(returnValue(2)); atLeast(1).of (note4).fret(); will(returnValue(3));    		
    		atLeast(1).of (note5).finger(); will(returnValue(4)); atLeast(1).of (note5).fret(); will(returnValue(5));    		
    		atLeast(1).of (note6).finger(); will(returnValue(1)); atLeast(1).of (note6).fret(); will(returnValue(2));    		
    		atLeast(1).of (note7).finger(); will(returnValue(3)); atLeast(1).of (note7).fret(); will(returnValue(4));    		
    		atLeast(1).of (note8).finger(); will(returnValue(4)); atLeast(1).of (note8).fret(); will(returnValue(5));    		
    		
    		oneOf (arr).assign_cost(0);
    	}});
		
    	assign_simple_hand_model_cost(arr);
	}
	
	@Test
	public void catch_buggy_cost_eval1(){
//		{[C1]|st:0.0|dr:1.0} at fi4;fr8; str:LOW_E
//		{[D1]|st:1.0|dr:1.0} at fi1;fr5; str:A
//		{[E2]|st:2.0|dr:1.0} at fi3;fr7; str:A
//		{[F2]|st:3.0|dr:1.0} at fi4;fr8; str:A
//		{[G2]|st:4.0|dr:1.0} at fi1;fr5; str:D lhp 5
//		{[A2]|st:5.0|dr:1.0} at fi3;fr7; str:D lhp 5
//		{[B2]|st:6.0|dr:1.0} at fi1;fr4; str:G lhp 4
//		{[C2]|st:7.0|dr:1.0} at fi1;fr1; str:B lhp 1
		//was given cost 1, should be 4
		
		final FingeredNote note1 = context.mock(FingeredNote.class, "note1");
		final FingeredNote note2 = context.mock(FingeredNote.class, "note2");
		final FingeredNote note3 = context.mock(FingeredNote.class, "note3");
		final FingeredNote note4 = context.mock(FingeredNote.class, "note4");
		final FingeredNote note5 = context.mock(FingeredNote.class, "note5");
		final FingeredNote note6 = context.mock(FingeredNote.class, "note6");
		final FingeredNote note7 = context.mock(FingeredNote.class, "note7");
		final FingeredNote note8 = context.mock(FingeredNote.class, "note8");
		
		final Arrangement arr = context.mock(Arrangement.class);
		
    	context.checking(new Expectations() {{
    		oneOf (arr).iterator(); will(returnIterator(note1, note2, note3, note4, note5, note6, note7, note8));    		
    	
    		atLeast(1).of (note1).finger(); will(returnValue(4)); atLeast(1).of (note1).fret(); will(returnValue(8));    		    		
    		atLeast(1).of (note2).finger(); will(returnValue(1)); atLeast(1).of (note2).fret(); will(returnValue(5));
    		atLeast(1).of (note3).finger(); will(returnValue(3)); atLeast(1).of (note3).fret(); will(returnValue(7));    		
    		atLeast(1).of (note4).finger(); will(returnValue(4)); atLeast(1).of (note4).fret(); will(returnValue(8));    		
    		atLeast(1).of (note5).finger(); will(returnValue(1)); atLeast(1).of (note5).fret(); will(returnValue(5));    		
    		atLeast(1).of (note6).finger(); will(returnValue(3)); atLeast(1).of (note6).fret(); will(returnValue(7));    		
    		atLeast(1).of (note7).finger(); will(returnValue(1)); atLeast(1).of (note7).fret(); will(returnValue(4));    		
    		atLeast(1).of (note8).finger(); will(returnValue(1)); atLeast(1).of (note8).fret(); will(returnValue(1));    		
    		
    		oneOf (arr).assign_cost(4);
    	}});
		
    	assign_simple_hand_model_cost(arr);
	}
	@Test
	public void catch_buggy_cost_eval2(){
//		{[C1]|st:0.0|dr:1.0} at fi1;fr8; str:LOW_E 	lhp 8
//		{[D1]|st:1.0|dr:1.0} at fi3;fr10; str:LOW_E lhp8
//		{[E2]|st:2.0|dr:1.0} at fi3;fr7; str:A 		lhp5
//		{[F2]|st:3.0|dr:1.0} at fi2;fr13; str:LOW_E	lhp12
//		{[G2]|st:4.0|dr:1.0} at fi1;fr5; str:D		lhp5
//		{[A2]|st:5.0|dr:1.0} at fi4;fr7; str:D		lhp4
//		{[B2]|st:6.0|dr:1.0} at fi1;fr4; str:G		lhp4
//		{[C2]|st:7.0|dr:1.0} at fi2;fr5; str:G		lhp4 total 3+7+7+1 = 18
		
		final FingeredNote note1 = context.mock(FingeredNote.class, "note1");
		final FingeredNote note2 = context.mock(FingeredNote.class, "note2");
		final FingeredNote note3 = context.mock(FingeredNote.class, "note3");
		final FingeredNote note4 = context.mock(FingeredNote.class, "note4");
		final FingeredNote note5 = context.mock(FingeredNote.class, "note5");
		final FingeredNote note6 = context.mock(FingeredNote.class, "note6");
		final FingeredNote note7 = context.mock(FingeredNote.class, "note7");
		final FingeredNote note8 = context.mock(FingeredNote.class, "note8");
		
		final Arrangement arr = context.mock(Arrangement.class);
		
    	context.checking(new Expectations() {{
    		oneOf (arr).iterator(); will(returnIterator(note1, note2, note3, note4, note5, note6, note7, note8));    		
    	
    		atLeast(1).of (note1).finger(); will(returnValue(1)); atLeast(1).of (note1).fret(); will(returnValue(8));    		    		
    		atLeast(1).of (note2).finger(); will(returnValue(3)); atLeast(1).of (note2).fret(); will(returnValue(10));
    		atLeast(1).of (note3).finger(); will(returnValue(3)); atLeast(1).of (note3).fret(); will(returnValue(7));    		
    		atLeast(1).of (note4).finger(); will(returnValue(2)); atLeast(1).of (note4).fret(); will(returnValue(13));    		
    		atLeast(1).of (note5).finger(); will(returnValue(1)); atLeast(1).of (note5).fret(); will(returnValue(5));    		
    		atLeast(1).of (note6).finger(); will(returnValue(4)); atLeast(1).of (note6).fret(); will(returnValue(7));    		
    		atLeast(1).of (note7).finger(); will(returnValue(1)); atLeast(1).of (note7).fret(); will(returnValue(4));    		
    		atLeast(1).of (note8).finger(); will(returnValue(2)); atLeast(1).of (note8).fret(); will(returnValue(5));    		
    		
    		oneOf (arr).assign_cost(18);
    	}});
		
    	assign_simple_hand_model_cost(arr);
	}
	
	@Test
	public void dont_assign_lhp_to_open_string(){
		final FingeredNote note1 = context.mock(FingeredNote.class, "note1");
		final FingeredNote note2 = context.mock(FingeredNote.class, "note2");
		final FingeredNote note3 = context.mock(FingeredNote.class, "note3");
		final Arrangement arr = context.mock(Arrangement.class);
		
		context.checking(new Expectations() {{
    		oneOf (arr).iterator(); will(returnIterator(note1, note2, note3));    		
    	
    		atLeast(1).of (note1).finger(); will(returnValue(1)); atLeast(1).of (note1).fret(); will(returnValue(8));    		    		
    		atLeast(1).of (note2).finger(); will(returnValue(0)); atLeast(1).of (note2).fret(); will(returnValue(0));
    		atLeast(1).of (note3).finger(); will(returnValue(1)); atLeast(1).of (note3).fret(); will(returnValue(5));    		
    		oneOf (arr).assign_cost(3);
    	}});
		
		assign_simple_hand_model_cost(arr);
	}
}
