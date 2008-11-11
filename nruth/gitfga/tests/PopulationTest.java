/**
	 
 */
package nruth.gitfga.tests;


import static org.junit.Assert.*;
import nruth.gitfga.*;
import nruth.gitfga.Notes.Note;
import nruth.gitfga.Position.GuitarString;
import nruth.gitfga.tests.GeneTest.GeneFactory;
import nruth.gitfga.tests.NotesTest.NotesFactory;

import org.junit.*;

/**
	@author nicholasrutherford
	
 */
public class PopulationTest {
	@Test // doesGenotypeRepresetNotes(Genotype g, Notes notes) (tdd)
	public void doesGenotypeRepresetNotes(){
		//create genotype to test with known notes (manually giving positions)
		Gene gene1 = new Gene(1, new Position(1, GuitarString.B));	//Note.C
		Gene gene2 = new Gene(4, new Position(5, GuitarString.HIGH_E));	//Note.A
		Genotype genotype = new Genotype(new Gene[]{gene1, gene2});
		
		//set up 2 lots of notes, 1 that matches the positions and 1 that does not
		Note[] wrong_notes_arry = new Note[] {Note.D, Note.Bb};
		Note[] notes_arry = new Note[] {Note.C, Note.A};
		Notes wrong_notes = new Notes(wrong_notes_arry);
		Notes notes = new Notes(notes_arry);
				
		assertTrue("match of actual notes failed", Population.doesGenotypeRepresetNotes(genotype, notes));
		assertFalse("match of wrong notes passed", Population.doesGenotypeRepresetNotes(genotype, wrong_notes));
	}	
	
	@Test
	public void initialisingPopulation(){
		assertEquals("the note sequence stored doesn't match the ones given: "+fixture.getNotes()+" produced, expected: "+notes,fixture.getNotes(), notes);
		
		//allow 5% of population size to be identical (randomly)
		int sameness = 0;
		Genotype[] genotypes = fixture.getPopulation();
		
		//scan the population for equivalent genotypes, there should be 1 match for each one (itself)
		//count any additional matches
		for(Genotype individual : genotypes){
			int matches = 0;
			for(Genotype others: genotypes){ if(individual.equals(others)) matches++; }
			if(matches>1){ sameness++; }
		}
		//now as we've got 2 hits per pairing, halve the result
		sameness/=2;
		
		assertFalse("The population contains more than 5% duplication", (100*sameness / genotypes.length) > 5);
	}
	
	private final static Notes notes = NotesTest.NotesFactory.getRandomNotes(10);
	private final static Population fixture = new Population(notes, 10);
}
