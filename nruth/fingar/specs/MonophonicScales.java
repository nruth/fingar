package nruth.fingar.specs;

import static org.junit.Assert.*;
import static nruth.fingar.domain.music.NamedNote.*;

import java.util.*;

import nruth.fingar.domain.*;
import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.Score;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.FINGAR;
import nruth.fingar.ga.evolvers.Evolver;
import nruth.fingar.ga.evolvers.MonophonicFretGapEvolver;
import nruth.fingar.ga.evolvers.SimpleHandPositionModelGAEvolver;

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
		Evolver evolver = new MonophonicFretGapEvolver(10, 2); //TODO this needs to be the production evolver, whatever that ends up being
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
		
		//process alternatives		
		//and check the list contains a known solution
		Evolver evolver = new SimpleHandPositionModelGAEvolver(10000, 50, 0.7, 0.2); //TODO this needs to be the production evolver, whatever that ends up being
		
		FINGAR ga = new FINGAR(c_major_scale, evolver);
		List<Arrangement> results = ga.results();
		assertTrue(results.size() > 0);
		
		
			
		List<Arrangement> rev_results = new ArrayList<Arrangement>(results.size());
		rev_results.addAll(results);
		Collections.sort(rev_results, new Comparator<Arrangement>() {
			@Override
			public int compare(Arrangement o1, Arrangement o2) {
				return ((Integer)o1.cost()).compareTo(o2.cost());
			}
		});
		
		rev_results = rev_results.subList(0, 20);
		Collections.reverse(rev_results);
		boolean found_match=false;
		for(Arrangement result : rev_results){
			System.out.println(result+"Cost: "+result.cost()+"\n----\n\n");
			if(match_known_result(result)) found_match = true;	
		}
		
		assertTrue("known result was not found in results",found_match);
	}
	
	public static Score c_major_scale(){
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
