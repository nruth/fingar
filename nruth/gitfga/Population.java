/**
	 
 */
package nruth.gitfga;

import nruth.gitfga.Notes.Note;
import nruth.gitfga.tests.GeneTest.GeneFactory;
import nruth.gitfga.tests.NotesTest.NotesFactory;

/** The population of alternative solutions to the fingering problem
 * contains a reference copy of the musical notes the individuals will call back to.
	@author nicholasrutherford
 */
public final class Population {
	public Population(Notes notes, int size){
		if(Population.music == null) Population.music = notes;
		else throw new RuntimeException("Cannot re-assign notes for a population");
		
		population = generateRandomPopulation(size);
	}
	
	/**
    	last modified: 8 Nov 2008
    	@param size
    	@return
     */
    private Genotype[] generateRandomPopulation(int size) {
	    // TODO tests for random population generation
	    Genotype[] genotypes = new Genotype[size];
    	
	    for(int idx=0; idx<size; idx++){ 	    	
	    	genotypes[idx] = new Genotype(MadScientist.generateRandomChromosomeForNotes(getNotes()));
	    }
	    
	    return genotypes;
    }



	/** callback provided for the individuals to see the notes from a single store
    	@return the musical notes in the piece encoded
     */
    public static Notes getNotes() { 	return music;   }
    
    public static boolean doesGenotypeRepresetNotes(Genotype g, Notes notes) {
    	//run through genotype positions and notes in parallel
    	Note[] note_arry = notes.getNotes();
    	
    	for(int idx = 0; idx<note_arry.length; idx++){
    		Note n = note_arry[idx];
    		Position p = g.getGeneAtLocus(idx+1).getPosition();
    		
    		//fail on the first mismatched note, 1 is sufficient to invalidate the genotype
    		if(! n.isFoundAt(p)){ return false; } 
    	}

    	//all passed
    	return true;
    }

	/**
    	last modified: 8 Nov 2008
    	@return
     */
    public Genotype[] getPopulation() { return population;  }
    
    private final Genotype[] population;
    private static Notes music; //static or final tradeoff in memory / reconstructing. Islanding also affects
    
    public static void main(String[] args) {
	    Notes notes = NotesFactory.getRandomNotes(6);
	    System.out.println("Notes sequence: "+notes);
	    
	    Population p = new Population(notes, 10);
	    for(Genotype individual : p.getPopulation()){
	    	System.out.println(individual);
	    }
    }
}
