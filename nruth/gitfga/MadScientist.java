/**
	 
 */
package nruth.gitfga;

import java.util.Random;

import nruth.gitfga.Notes.Note;

/**
	@author nicholasrutherford
 */
@Deprecated
public final class MadScientist { 
	static final Random seed = new Random();
	
	//TODO tests for gene generator
	public static Gene generateRandomGeneForNote(Note n){ //random finger, random position
		int finger = seed.nextInt(Assumptions.FINGERS)+1;
		Position[] possible_positions = n.getPositionsForNote();
		Position p = possible_positions[seed.nextInt(possible_positions.length)];
		
		return new Gene(finger, p);
	}
	
	//TODO tests for chromosome generator
	public static Gene[] generateRandomChromosomeForNotes(Notes ns){
		Gene[] chromosome = new Gene[ns.getNotes().length];
		for(int idx=0; idx<chromosome.length; idx++){
			chromosome[idx] = generateRandomGeneForNote(ns.getNotes()[idx]);
		}
		return chromosome;
	}
}
