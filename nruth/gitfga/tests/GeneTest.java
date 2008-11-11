/**
	 
 */
package nruth.gitfga.tests;

import java.util.Random;

import org.junit.*;
import static org.junit.Assert.*;
import nruth.gitfga.*;
import nruth.gitfga.Gene.*;
import static nruth.gitfga.tests.GeneTest.GeneFactory.*;


/**
	@author nicholasrutherford
	
 */
public class GeneTest {
	/**
		last modified: 30 Oct 2008
		@throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {//Shouldn't build implementation immutable knowledge into the tests, so new fixture per test
		fixture = new Gene(testGuitarFinger, testGuitarString, testGuitarFret); 
	}
	
	@Test
	public void doesItReturnCorrectGuitarString(){
		assertTrue("does the returned guitar string match the one used to create the gene", fixture.getString()==testGuitarString);
	}
	
	@Test
	public void doesItReturnCorrectFretPosition(){
		assertTrue("does the returned fret position match the one used to create the gene", fixture.getFret()==testGuitarFret);
	}
	
	@Test
	public void doesItReturnCorrectFingerUsed(){
		assertTrue("does the returned finger match the one used to create the gene", fixture.getFinger()==testGuitarFinger);
	}
	
	//test finger validation
	
	public void testInvalidFinger0() {}
	
	public void testInvalidFinger5() {createDefaultGeneWithFinger(Assumptions.FINGERS+1);}
	
	public void testInvalidFingerNeg() {createDefaultGeneWithFinger(-3);}
	
	@Test
	public void valid_finger_validation(){
		assertTrue(createDefaultGeneWithFinger(1).is_valid() &&
				createDefaultGeneWithFinger(3).is_valid() &&
				createDefaultGeneWithFinger(Assumptions.FINGERS).is_valid());
				
				//now test failures
		String error_expected_fail_string = "Exception not raised for invalid finger";
				try{createDefaultGeneWithFinger(0); fail(error_expected_fail_string);} catch(RuntimeException should_happen){}
				try{createDefaultGeneWithFret(-12); fail(error_expected_fail_string);} catch(RuntimeException should_happen){}
				try{createDefaultGeneWithFret(Assumptions.FRETS+1); fail(error_expected_fail_string);} catch(RuntimeException should_happen){}
			}	
	//end test finger validation
	
	@Test //fret validation
	public void valid_frets_validation(){ 		
		assertTrue(createDefaultGeneWithFret(0).is_valid() &&
		createDefaultGeneWithFret(12).is_valid() &&
		createDefaultGeneWithFret(Assumptions.FRETS).is_valid());
		
		//now test failures
		try{createDefaultGeneWithFret(-1); fail("Exception not raised for invalid fret");} catch(RuntimeException should_happen){}
		try{createDefaultGeneWithFret(-12); fail("Exception not raised for invalid fret");} catch(RuntimeException should_happen){}
		try{createDefaultGeneWithFret(Assumptions.FRETS+1); fail("Exception not raised for invalid fret");} catch(RuntimeException should_happen){}
	}	
	//end test fret validation  

	@Test //for Object::equal override
	public void equal_override(){
		// x = x
		assertTrue(fixture.equals(fixture));
		
		try { // x = y | y = x.clone
			assertTrue(fixture.equals(fixture.clone()));
        } catch (CloneNotSupportedException e) {
	        fail("cloning of gene failed");
	        e.printStackTrace();
        } 
		
        //check not equal against 3 random genes to avoid small chance of random duplication
        assertFalse(fixture.equals(createRandomGene()) && fixture.equals(createRandomGene()) && fixture.equals(createRandomGene()));
	}
	
	@Test //clone test
	public void test_cloning(){
		try {
			Gene clone = fixture.clone();
	        assertNotSame(clone, fixture);
	        assertEquals(clone, fixture);
        } catch (CloneNotSupportedException e) {
	        fail(e.getMessage());
        }
	}
	
	private Gene fixture;
	private final static int testGuitarFret = 12; 
	private final static Position.GuitarString testGuitarString = Position.GuitarString.B;
	private final static int testGuitarFinger = 2;
	
	
	public static class GeneFactory{
		//helpers
		public static Gene createDefaultGeneWithString(Position.GuitarString string) { return new Gene(testGuitarFinger, string, testGuitarFret);}
		public static Gene createDefaultGeneWithFinger(int finger) { return new Gene(finger, testGuitarString, testGuitarFret);}
		public static Gene createDefaultGeneWithFret(int fret) { return new Gene(testGuitarFinger, testGuitarString, fret);}
		public static Gene createDefaultGeneWithFret(Position position) { return new Gene(testGuitarFinger, position);}
		
		//internal test	
		@Test
		public void testRandomGenerator(){
			assertTrue(createRandomGene().is_valid());
			
			//try 3 against one another
			Gene[] genes = new Gene[3];
			for(int n=0; n<genes.length; n++){ genes[n] = createRandomGene(); }
			
			assertFalse(genes[0].equals(genes[1]) && genes[0].equals(genes[2]));
		}
		
	    public static Gene createRandomGene() {
	    	Random seed = new Random();
	    	
	        switch(seed.nextInt(3)){
	        	case 0:		return createDefaultGeneWithFinger(seed.nextInt(Assumptions.FINGERS)+1);
	        	case 1:		return createDefaultGeneWithFret(seed.nextInt(Assumptions.FRETS));
	        	case 2:		
	        		//get the array of enumerations and pick one from it randomly to use
	        		Position.GuitarString[] strings = Position.GuitarString.values();	        		
	        		return createDefaultGeneWithString(strings[seed.nextInt(strings.length)]); 
	        	default:	return null;  
	        }
	    }
	}
}