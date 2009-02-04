/**
	 
 */
package nruth.fingar.domain.tests;
import static org.junit.Assert.*;
import org.junit.*;
import nruth.fingar.domain.*;

public class ScoreSpec {
	/**
	 * 	Each note in the score has a name and octave
	 */
	@Test
	public void each_note_has_name_and_octave(){
		assertEquals(fixture_notes[1].getOctave(),fixture.getNote(2).getOctave());
		assertEquals(fixture_notes[2].toString(), fixture.getNote(3).toString());
	}
	
	/**
	 * each note has a starting time (relative to beginning of piece) and a duration
	 */
	@Test
	public void note_start_time_and_durations_recorded(){
		assertEquals(fixture_notes[2],fixture.getNoteAtTime(fixture_timing[2][0]));
		assertEquals(fixture_timing[3][1], fixture.get_duration_of_note_at_time(fixture_timing[3][0]));
	}
	
	/**
	 * an ordered list of notes, indexed by time played from start
	 */
	@Test
	public void ordered_list_of_notes(){
		//check notes match in order
		for(int i=0; i<fixture_notes.length; i++){assertEquals(fixture_notes[i], fixture.getNote(i+1));}		
	}

	/**
	 * an ordered list of notes, indexed by time played from start
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void check_list_index_bounds_start(){ fixture.getNote(0); }
	
	/**
	 * an ordered list of notes, indexed by time played from start
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void check_list_index_bounds_negative(){ fixture.getNote(-1); }
	
	/**
	 * an ordered list of notes, indexed by time played from start
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void check_list_index_bounds_end(){fixture.getNote(fixture_notes.length+1);}

	/**
	 * an ordered list of notes, indexed by time played from start
	 */
	@Test
	public void note_start_timings_are_progressive(){
		float[] times = fixture.get_note_start_times();
		for(int n=1; n<times.length; n++){	assertTrue(times[n] > times[n-1]);	}	
	}
	
	/**
	 * an ordered list of notes, indexed by time played from start
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void muddled_note_start_timings_are_rejected(){
		ArrangedNote[] notes = {
				new ArrangedNote(NoteSpec.NoteFactory.getRandomNote(), 0f, 3f),
				new ArrangedNote(NoteSpec.NoteFactory.getRandomNote(), 10f, 0.5f),
				new ArrangedNote(NoteSpec.NoteFactory.getRandomNote(), 4f, 2f),
				new ArrangedNote(NoteSpec.NoteFactory.getRandomNote(), 6f, 4f),
				new ArrangedNote(NoteSpec.NoteFactory.getRandomNote(), 10.5f, 1.5f)
		};
		
		fixture = new Score(notes);
	}
	
	/**
	 * the interval range of the score, determined from its highest and lowest used notes
	 */
	@Test
	public void range_determined_from_highest_and_lowest_notes(){
		//replace the fixtures with a known range
		//and test the calculation code gives the same result
		
		Note highnote, lownote;		
		Note[] notes;
		float[][] timing;
		Score score;
		
		//0 range
		lownote = new Note(NamedNote.A, 1);
		highnote = new Note(NamedNote.A, 1);				
		score = new Score( new ArrangedNote[] {new ArrangedNote(lownote, 0, 1), new ArrangedNote(highnote, 1, 1)} );
		assertEquals(0, score.getIntervalRange());
		
		//small range
		lownote = new Note(NamedNote.A, 1);
		highnote = new Note(NamedNote.C, 1);				
		score = new Score( new ArrangedNote[] {new ArrangedNote(lownote, 0, 1), new ArrangedNote(highnote, 1, 1)} );
		assertEquals(3, score.getIntervalRange());
		
		//octave range
		lownote = new Note(NamedNote.A, 1);
		highnote = new Note(NamedNote.A, 2);		
		score = new Score( new ArrangedNote[] {new ArrangedNote(lownote, 0, 1), new ArrangedNote(highnote, 1, 1)} );
		assertEquals(12, score.getIntervalRange());
		
		//mixed range
		lownote = new Note(NamedNote.D, 1);
		highnote = new Note(NamedNote.C, 3);				
		score = new Score( 
				new ArrangedNote[] {
						new ArrangedNote(lownote, 0, 1), 
						new ArrangedNote(new Note(NamedNote.A, 2), 1, 1),
						new ArrangedNote(highnote, 2, 1),
						new ArrangedNote(new Note(NamedNote.G, 2), 3, 1),
				} 
		);
		assertEquals(22, score.getIntervalRange());
	}
	
//	TODO: design decision, may leave this out, stick to same frequency range as original input	
//	/**
//	 * may be played over a set of possible ranges (on a guitar)
//	 */
//	@Test
//	public void may_be_played_over_a_set_of_possible_ranges(){
//		fail("not implemented");
//	}
	
	private Score fixture;
	static Note[] fixture_notes = new Note[5];
	static float[][] fixture_timing;
	@Before
	public void setUp(){ this.fixture = get_test_score(); }
	
	public static Score get_test_score(){
		for(int i=0; i<fixture_notes.length; i++){fixture_notes[i] = NoteSpec.NoteFactory.getRandomNote();}
		fixture_timing = new float[][]{
				{0f,3f},
				{4f,2f},
				{6f,4f},
				{10f,0.5f},
				{10.5f,1.5f}
		};
		
		ArrangedNote[] arranged_notes = new ArrangedNote[5];
		
		for(int i=0; i<arranged_notes.length; i++){
			arranged_notes[i] = new ArrangedNote(fixture_notes[i], fixture_timing[i][0], fixture_timing[i][1]);
		}
		
		return new Score(arranged_notes);
	}
}
