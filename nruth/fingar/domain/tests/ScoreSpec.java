/**
	 
 */
package nruth.fingar.domain.tests;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runner.JUnitCore;
import nruth.fingar.domain.*;
/**
	@author nicholasrutherford
	from spec:
	Each note in the score has a name and octave

score: The input piece of music
	an ordered list of notes, indexed by time played from start
	* timing : each note has a starting time (relative to beginning of piece) and a duration
	* may be played over a set of possible ranges (on a guitar)
	
	Range
		the interval range of the score, determined from its highest and lowest used notes
 */
public class ScoreSpec {
	@Test
	public void ordered_list_of_notes(){
		//check notes match in order
		for(int i=0; i<fixture_notes.length; i++){assertEquals(fixture_notes[i], fixture.getNote(i+1));}		
	}
		
	@Test(expected=IndexOutOfBoundsException.class)
	public void check_list_index_bounds_start(){ fixture.getNote(0); }
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void check_list_index_bounds_negative(){ fixture.getNote(-1); }
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void check_list_index_bounds_end(){fixture.getNote(fixture_notes.length+1);}
	
	@Test
	public void indexed_by_time_played_from_start(){
		assertEquals(fixture_notes[2],fixture.getNoteAtTime(fixture_timing[2][0]));
	}
	
	@Test
	public void note_durations_recorded(){
		assertEquals(fixture_timing[3][1], fixture.getDurationOfNoteAtTime(fixture_timing[3][0]));
	}
	
	@Test
	public void range_determined_from_highest_and_lowest_notes(){
		fail("not implemented");
		int highest = 0;
		int lowest = 0;
		for(Note n : fixture_notes){
			int octave = n.getOctave();
			if(highest < octave) highest=octave;
			if(lowest > octave) lowest=octave;
		}
	}
	
	@Test
	public void may_be_played_over_a_set_of_possible_ranges(){
		fail("not implemented");
	}
	
	private Score fixture;
	Note[] fixture_notes = new Note[5];
	float[][] fixture_timing;
	@Before
	public void setUp(){
		for(int i=0; i<fixture_notes.length; i++){fixture_notes[i] = NoteSpec.NoteFactory.getRandomNote();}
		fixture_timing = new float[][]{
				{0f,3f},
				{4f,2f},
				{6f,4f},
				{10f,0.5f},
				{10.5f,1.5f}
		};
		fixture = new Score(fixture_notes, fixture_timing);
	}
}
