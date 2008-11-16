/**
	 
 */
package nruth.gitfga.encoding.tests;
import static org.junit.Assert.*;

import nruth.gitfga.encoding.*;
import nruth.gitfga.encoding.tests.NoteSpec.NoteFactory;

import org.junit.*;

/**
	@author nicholasrutherford
	
 */
public class ScoreSpec {
	private Score fixture;
	Note[] fixture_notes = new Note[5];
	@Before
	public void setUp(){
		for(int i=0; i<fixture_notes.length; i++){fixture_notes[i] = NoteFactory.getRandomNote();}
		fixture = new Score(fixture_notes);
	}

	@Test
	public void ordered_list_of_notes(){
		//check notes match in order
		for(int i=0; i<fixture_notes.length; i++){assertEquals(fixture_notes[i], fixture.getNote(i+1));}		
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void negative_list_index(){
		fixture.getNote(-1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void zero_list_index(){
		fixture.getNote(0);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void off_end_list_index(){
		fixture.getNote(fixture_notes.length+1);
	}
	
	//next spec
	@Test
	public void indexed_by_time_played_from_start(){
		fail("not implemented");
	}
	
	@Test
	public void note_durations_recorded(){
		fail("not implemented");
	}
	
	@Test
	public void may_be_played_over_a_set_of_possible_ranges(){
		fail("not implemented");
	}

	@Test
	public void range_from_highest_and_lowest_notes(){
		fail("not implemented");
	}
}
