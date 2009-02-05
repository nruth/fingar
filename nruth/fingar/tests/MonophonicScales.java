package nruth.fingar.tests;

import static org.junit.Assert.*;
import static nruth.fingar.domain.NamedNote.*;
import java.util.*;

import nruth.fingar.Arrangement;
import nruth.fingar.FINGAR;
import nruth.fingar.FingeredNote;
import nruth.fingar.domain.*;
import nruth.fingar.domain.Assumptions.STRINGS;

import org.junit.*;

import sun.security.util.PendingException;


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
		FINGAR ga = new FINGAR(c_major_scale);
		List<Arrangement> results = ga.getArrangements();
		assertTrue(results.size() > 0);
		
		fail("pending: check for a known solution");
		
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
		for(NamedNote name : new NamedNote[]{C,D,E,F,G}){
			notes[time] = new TimedNote(new Note(name, 1), time++, 1f);
		}
		for(NamedNote name : new NamedNote[]{A,B,C}){
			notes[time] = new TimedNote(new Note(name, 2), time++, 1f);
		}
				
		return new Score(notes);
	}
	
	private Arrangement known_solution(){
		Arrangement sol = new Arrangement(c_major_scale());
		for(FingeredNote note : sol){
			if(note.note().equals(new Note(NamedNote.C, 1))){	note.setFinger(2); note.setString(STRINGS.A); note.setFret(3);	}
			else if(note.note().equals(new Note(NamedNote.D, 1))){	note.setFinger(2); note.setString(STRINGS.A); note.setFret(5);	}
			else if(note.note().equals(new Note(NamedNote.E, 1))){	note.setFinger(2); note.setString(STRINGS.D); note.setFret(2);	}
			else if(note.note().equals(new Note(NamedNote.F, 1))){	note.setFinger(2); note.setString(STRINGS.D); note.setFret(3);	}
			else if(note.note().equals(new Note(NamedNote.G, 1))){	note.setFinger(2); note.setString(STRINGS.D); note.setFret(5);	}
			else if(note.note().equals(new Note(NamedNote.A, 2))){	note.setFinger(2); note.setString(STRINGS.G); note.setFret(2);	}
			else if(note.note().equals(new Note(NamedNote.B, 2))){	note.setFinger(2); note.setString(STRINGS.G); note.setFret(4);	}
			else if(note.note().equals(new Note(NamedNote.C, 2))){	note.setFinger(2); note.setString(STRINGS.G); note.setFret(5);	}		
		}
		
		return sol;
	}
}
