/**
	 
 */
package nruth.gitfga2.tests;


import nruth.gitfga2.AChromatic;
import nruth.gitfga2.AChromatic.NamedNote;

import org.junit.*;

import static org.junit.Assert.*;
/**
	@author nicholasrutherford
	
 */
public class AChromaticSpec {
	@Test
	public void test_named_notes_equality(){
		assertEquals(NamedNote.A, NamedNote.A);
		assertFalse(NamedNote.A.equals(NamedNote.B));
	}
	
	@Test
	public void named_notes_correct(){
		String[] notes =  {"A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab"};
		
		for(int idx=0; idx<AChromatic.NamedNote.values().length; idx++){
			assertEquals(AChromatic.NamedNote.values()[idx].toString(),notes[idx]);
		}
	}
	
}
