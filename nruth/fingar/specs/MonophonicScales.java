package nruth.fingar.specs;

import static org.junit.Assert.*;
import static nruth.fingar.domain.music.NamedNote.*;

import java.util.*;

import nruth.fingar.Arrangement;
import nruth.fingar.FingeredNote;
import nruth.fingar.domain.*;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.Score;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.ga.FINGAR;
import nruth.fingar.ga.evolvers.Evolver;

import org.junit.*;

/**
 * High level GA prototype feature testing will go here
 * @author nicholasrutherford
 *
 */
public class MonophonicScales {
	/**
	 * given a C major scale
	 * process alternative arrangements
	 * and return a ranked list of the best solutions
	 */
	
	/**
	 * check known solution is included
	 */
	@Test
	public void c_major_scale_alternatives(){
		Score c_major_scale = c_major_scale();
		
		//process alternatives		
		//and check the list contains a known solution
		Evolver evolver = null; //TODO this needs to be the production evolver, whatever that ends up being
		
		FINGAR ga = new FINGAR(c_major_scale, evolver);
		List<Arrangement> results = ga.results();
		assertTrue(results.size() > 0);
		
		boolean found_match=false;
		Arrangement known = known_solution();
		for(Arrangement result : results){
			if(result.equals(known)) found_match = true;
			System.out.println(result+"\n----\n\n");
		}
				
		assertTrue("known result was not found in results",found_match);
	}
	
	/**
	 * check solutions are ranked
	 */
	@Test
	public void are_solutions_ranked(){
		fail("not implemented");
	}
	
	public Score c_major_scale(){
		TimedNote[] notes = new TimedNote[8];
		int time = 0;
		for(NamedNote name : new NamedNote[]{C,D}){
			notes[time] = new TimedNote(new Note(name, 1), time++, 1f);
		}
		for(NamedNote name : new NamedNote[]{E,F,G,A,B,C}){
			notes[time] = new TimedNote(new Note(name, 2), time++, 1f);
		}
		
		return new Score(notes);
	}
	
	private Arrangement known_solution(){
		Arrangement sol = new Arrangement(c_major_scale());
		for(FingeredNote note : sol){
			if(note.note().equals(new Note(NamedNote.C, 1))){	note.setFinger(2); note.setString(GuitarString.A); note.setFret(3);	}
			else if(note.note().equals(new Note(NamedNote.D, 1))){	note.setFinger(2); note.setString(GuitarString.A); note.setFret(5);	}
			else if(note.note().equals(new Note(NamedNote.E, 2))){	note.setFinger(2); note.setString(GuitarString.D); note.setFret(2);	}
			else if(note.note().equals(new Note(NamedNote.F, 2))){	note.setFinger(2); note.setString(GuitarString.D); note.setFret(3);	}
			else if(note.note().equals(new Note(NamedNote.G, 2))){	note.setFinger(2); note.setString(GuitarString.D); note.setFret(5);	}
			else if(note.note().equals(new Note(NamedNote.A, 2))){	note.setFinger(2); note.setString(GuitarString.G); note.setFret(2);	}
			else if(note.note().equals(new Note(NamedNote.B, 2))){	note.setFinger(2); note.setString(GuitarString.G); note.setFret(4);	}
			else if(note.note().equals(new Note(NamedNote.C, 2))){	note.setFinger(2); note.setString(GuitarString.G); note.setFret(5);	}		
		}
		
		return sol;
	}
}
