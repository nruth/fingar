package nruth.fingar.specs;

import static org.junit.Assert.*;
import static nruth.fingar.domain.music.NamedNote.*;

import java.util.*;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.Score;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.FINGAR;
import nruth.fingar.ga.cost_functions.MonophonicFretGapCostFunction;
import nruth.fingar.ga.cost_functions.SimpleHandPositionModelCostFunction;
import nruth.fingar.ga.evolvers.Breeder;
import nruth.fingar.ga.evolvers.Evolver;
import nruth.fingar.ga.evolvers.GeneticAlgorithmEvolver;
import nruth.fingar.ga.probability.GoldbergRouletteWheel;

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
	 * and return a list of costed solutions
	 */
	
	/**
	 * check solutions are costed
	 */
	@Test
	public void are_solutions_costed(){
		Evolver evolver = new GeneticAlgorithmEvolver(new GoldbergRouletteWheel.WheelFactory(), new Breeder() ,new MonophonicFretGapCostFunction()); //TODO this needs to be the production evolver, whatever that ends up being
		FINGAR ga = new FINGAR(c_major_scale(), evolver);
		for(Arrangement arr: ga.results()){
			assertTrue("cost not assigned",arr.cost()>=0);
		}
	}
	
	/**
	 * check known solution is included
	 */
	@Test
	public void c_major_scale_alternatives(){
		Score c_major_scale = c_major_scale();
		boolean found_match=false;
		for(int i=0; i<20; i++){ //check several runs for the result
			//process alternatives		
			//and check the list contains a known solution
			Evolver evolver = new GeneticAlgorithmEvolver(20000, 40, 1.00/c_major_scale.size(), 0.02,new Random(), new GoldbergRouletteWheel.WheelFactory(), new Breeder(), new SimpleHandPositionModelCostFunction()); //TODO this needs to be the production evolver, whatever that ends up being
			
			FINGAR ga = new FINGAR(c_major_scale, evolver, 30);
			List<Arrangement> results = ga.results();
			assertTrue(results.size() > 0);
			
			for(Arrangement result : results){
				if(match_known_result(result)){ 
					found_match = true;
					break;
				}
			}
			if(found_match){ break; }
		}
		assertTrue("known result was not found in results",found_match);
	}
	public static Score c_major_scale(){ return c_major_scale(1); }
	
	public static Score c_major_scale(int start_octave){
		TimedNote[] notes = new TimedNote[8];
		int time = 0;
		for(NamedNote name : new NamedNote[]{C,D}){
			notes[time] = new TimedNote(new Note(name, start_octave), time++, 1f);
		}
		for(NamedNote name : new NamedNote[]{E,F,G,A,B,C}){
			notes[time] = new TimedNote(new Note(name, start_octave+1), time++, 1f);
		}
		
		return new Score(notes);
	}
	
	public static Score a_minor_scale(int octave){
		float time = 0.0f;		
		TimedNote[] notes = new TimedNote[]{
				new TimedNote(new Note(A, octave), time++, 1f)
			,	new TimedNote(new Note(C, octave), time++, 1f)
			,	new TimedNote(new Note(D, octave), time++, 1f)
			,	new TimedNote(new Note(Eb, octave), time++, 1f)
			,	new TimedNote(new Note(E, ++octave), time++, 1f)
			,	new TimedNote(new Note(G, octave), time++, 1f)
			,	new TimedNote(new Note(A, octave), time++, 1f)
			,	new TimedNote(new Note(C, octave), time++, 1f)
			,	new TimedNote(new Note(D, octave), time++, 1f)
			,	new TimedNote(new Note(Eb, octave), time++, 1f)
			,	new TimedNote(new Note(E, ++octave), time++, 1f)
			,	new TimedNote(new Note(G, octave), time++, 1f)
			,	new TimedNote(new Note(A, octave), time++, 1f)
		};
		
		return new Score(notes);
	}
	
	public static Score sailors_hornpipe(){
		LinkedList<TimedNote> notes = new LinkedList<TimedNote>();
		float start = 0.0f;
		
		//bar 1
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(G, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(Gb, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 1.0f, new Note(G, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 1.0f, new Note(G, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 1.0f, new Note(G, 2));		
		
		//bar 2
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(D, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(C, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(B, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(D, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(G, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(Gb, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(G, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(Gb, 3));
		
		//bar 3
		start = add_note_and_return_its_ending_beat(notes, start, 1.0f, new Note(G, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 1.0f, new Note(A, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 1.0f, new Note(A, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 1.0f, new Note(A, 2));
		
		//bar 4
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(A, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(G, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 1.0f, new Note(Gb, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 1.0f, new Note(D, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 1.0f, new Note(D, 2));
		
		//bar 5
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(E, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(Gb, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(G, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(E, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(D, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(E, 3));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(D, 2));
		
		//bar 6
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(C, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(B, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(C, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(B, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(A, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(G, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(Gb, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(E, 2));
		
		//bar 7
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(D, 1));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(G, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(Gb, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(A, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(G, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(B, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(A, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 0.5f, new Note(C, 2));
		
		//bar 8
		start = add_note_and_return_its_ending_beat(notes, start, 1.0f, new Note(B, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 1.0f, new Note(G, 2));
		start = add_note_and_return_its_ending_beat(notes, start, 1.0f, new Note(G, 2));
		
		return new Score(notes.toArray(new TimedNote[notes.size()]));
	}

	/**
	 * @param notes collection to add to
	 * @param start start beat
	 * @param duration note duration (beats)
	 * @param note note to use
	 * @return ending beat for the note (start+duration)
	 */
	private static float add_note_and_return_its_ending_beat(List<TimedNote> notes, float start, float duration, Note note){
		notes.add(new TimedNote(note, start, duration));
		return start+duration;
	}
	
	private boolean match_known_result(Arrangement arr){
		boolean ret = true;
		
		Iterator<FingeredNote> itr = arr.iterator();
		for(int n=0; n < solution.length; n++){
			if(!solution[n].equals(itr.next())) ret = false;
		}
		
		return ret;
	}
	
	private FingeredNote[] solution = new FingeredNote[]{
			new FingeredNote(2, 3, GuitarString.A, c_major_scale().get_nth_note(1)), 
			new FingeredNote(4, 5, GuitarString.A, c_major_scale().get_nth_note(2)), 
			new FingeredNote(1, 2, GuitarString.D, c_major_scale().get_nth_note(3)), 
			new FingeredNote(2, 3, GuitarString.D, c_major_scale().get_nth_note(4)), 
			new FingeredNote(4, 5, GuitarString.D, c_major_scale().get_nth_note(5)), 
			new FingeredNote(1, 2, GuitarString.G, c_major_scale().get_nth_note(6)), 
			new FingeredNote(3, 4, GuitarString.G, c_major_scale().get_nth_note(7)), 
			new FingeredNote(4, 5, GuitarString.G, c_major_scale().get_nth_note(8))
	};
}