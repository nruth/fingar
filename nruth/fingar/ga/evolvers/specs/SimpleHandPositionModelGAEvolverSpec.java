package nruth.fingar.ga.evolvers.specs;

import static org.junit.Assert.*;

import java.util.Arrays;

import nruth.fingar.ga.evolvers.SimpleHandPositionModelGAEvolver;

import org.junit.Test;
import static nruth.fingar.ga.evolvers.SimpleHandPositionModelGAEvolver.lhp_of_position;
public class SimpleHandPositionModelGAEvolverSpec {

	@Test
	public void testAssign_simple_hand_model_cost() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testLhp_of_position() {
		//test index finger positions
		assertEquals(3, lhp_of_position(3, 1));
		assertEquals(12, lhp_of_position(12, 1));
		
		//test 4th finger positions, -ves are valid (though somewhat unintuitive they do reflect the hand position)
		assertEquals(0, lhp_of_position(3, 4));
		assertEquals(9, lhp_of_position(12, 4));
		
		//test negative lhp positions
		assertEquals(0, lhp_of_position(2, 3));
		assertEquals(-3, lhp_of_position(0, 4));
	}
	
	@Test
	public void open_string_is_free(){
		//the open string does not change the lhp, so lhp(n+1) = lhp(n)
		fail("not ready to test yet");
	}
	
	@Test
	public void values_in_paper(){
		int[] fret = new int[]{3, 7, 1, 0};
		int[] finger = new int[]{4, 3, 3, 4};
		int[] lhps = new int[]{0, 5, -1, -3};
		
		for(int n=0; n<fret.length; n++){
			assertEquals("tab:simple_hand_position_model_with_zeros",lhps[n], lhp_of_position(fret[n], finger[n]));
		}
		
		fret = new int[]{5, 7, 3, 10};
		finger = new int[]{1, 3, 3, 2};
		lhps = new int[]{5, 5, 1, 9};
		for(int n=0; n<fret.length; n++){
			assertEquals("tab:simple_hand_position_model_run",lhps[n], lhp_of_position(fret[n], finger[n]));
		}
	}
}
