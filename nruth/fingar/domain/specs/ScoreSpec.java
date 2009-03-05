package nruth.fingar.domain.specs;
import static org.junit.Assert.*;
import org.junit.*;
import nruth.fingar.domain.*;
import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.Score;
import nruth.fingar.domain.music.TimedNote;

public class ScoreSpec {
	//there is only one score for the GA currently so no need to test hashcodes or equality
	
	/**
	 * 	Each note in the score has a name and octave
	 */
	@Test
	public void each_note_has_name_and_octave(){
		assertEquals(fixture_notes[1].octave(),fixture.get_nth_note(2).note().octave());
		assertEquals(fixture_notes[2].toString(), fixture.get_nth_note(3).note().toString());
	}
	
	/**
	 * each note has a starting time (relative to beginning of piece) and a duration
	 */
	@Test
	public void note_start_time_and_durations_recorded(){
		assertEquals(fixture_notes[2],fixture.getNoteAtTime(fixture_timing[2][0]).note());
		assertEquals(fixture_timing[3][1], fixture.get_duration_of_note_at_time(fixture_timing[3][0]), 0.01);
	}
	
	/**
	 * returns the number of notes in the score
	 */
	@Test
	public void get_number_of_notes_in_score(){
		assertEquals(40, new Score(TimedNoteSpec.create_random_monophonic_arranged_notes(40)).size());
		assertEquals(1, new Score(TimedNoteSpec.create_random_monophonic_arranged_notes(1)).size());
		assertFalse(12 == new Score(TimedNoteSpec.create_random_monophonic_arranged_notes(6)).size());
	}
	
	/**
	 * an iterable, ordered by start_beat, collection of (note, start beat, duration)
	 */
	@Test
	public void iterable_collection_of_arranged_notes(){		
		//test for a known sequence
		TimedNote[] notes = new TimedNote[] {
				new TimedNote(new Note(NamedNote.A, 1), 0, 1), 
				new TimedNote(new Note(NamedNote.B, 2), 1, 1),
				new TimedNote(new Note(NamedNote.A, 3), 2, 1),
				new TimedNote(new Note(NamedNote.G, 2), 3, 1),
		} ;
		
		Score score = new Score(notes);		
		int n=0;
		for(TimedNote note : score){
			assertEquals("start-time ordering not maintained", notes[n++], note);
		}
	}
	
	/**
	 * an ordered list of notes, indexed by time played from start
	 */
	@Test
	public void ordered_list_of_notes(){
		//check notes match in order
		for(int i=0; i<fixture_notes.length; i++){assertEquals(fixture_notes[i], fixture.get_nth_note(i+1).note());}		
	}

	/**
	 * an ordered list of notes, indexed by time played from start
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void check_list_index_bounds_start(){ fixture.get_nth_note(0); }
	
	/**
	 * an ordered list of notes, indexed by time played from start
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void check_list_index_bounds_negative(){ fixture.get_nth_note(-1); }
	
	/**
	 * an ordered list of notes, indexed by time played from start
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void check_list_index_bounds_end(){fixture.get_nth_note(fixture_notes.length+1);}

	/**
	 * an ordered list of notes, indexed by time played from start
	 */
	@Test
	public void note_start_timings_are_progressive(){
		float previous = 0f;
		for(TimedNote note : fixture){
			assertTrue("timing order failure",previous <= note.start_beat());
		}
	}
	
	/**
	 * an ordered list of notes, indexed by time played from start
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void muddled_note_start_timings_are_rejected(){
		TimedNote[] notes = {
				new TimedNote(NoteSpec.NoteFactory.getRandomNote(), 0f, 3f),
				new TimedNote(NoteSpec.NoteFactory.getRandomNote(), 10f, 0.5f),
				new TimedNote(NoteSpec.NoteFactory.getRandomNote(), 4f, 2f),
				new TimedNote(NoteSpec.NoteFactory.getRandomNote(), 6f, 4f),
				new TimedNote(NoteSpec.NoteFactory.getRandomNote(), 10.5f, 1.5f)
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
		Score score;
		
		//0 range
		lownote = new Note(NamedNote.A, 1);
		highnote = new Note(NamedNote.A, 1);				
		score = new Score( new TimedNote[] {new TimedNote(lownote, 0, 1), new TimedNote(highnote, 1, 1)} );
		assertEquals(0, score.getIntervalRange());
		
		//small range
		lownote = new Note(NamedNote.A, 1);
		highnote = new Note(NamedNote.C, 1);				
		score = new Score( new TimedNote[] {new TimedNote(lownote, 0, 1), new TimedNote(highnote, 1, 1)} );
		assertEquals(3, score.getIntervalRange());
		
		//octave range
		lownote = new Note(NamedNote.A, 1);
		highnote = new Note(NamedNote.A, 2);		
		score = new Score( new TimedNote[] {new TimedNote(lownote, 0, 1), new TimedNote(highnote, 1, 1)} );
		assertEquals(12, score.getIntervalRange());
		
		//mixed range
		lownote = new Note(NamedNote.D, 1);
		highnote = new Note(NamedNote.C, 3);				
		score = new Score( 
				new TimedNote[] {
						new TimedNote(lownote, 0, 1), 
						new TimedNote(new Note(NamedNote.A, 2), 1, 1),
						new TimedNote(highnote, 2, 1),
						new TimedNote(new Note(NamedNote.G, 2), 3, 1),
				} 
		);
		assertEquals(22, score.getIntervalRange());
	}
	
	//not required, removed
//	/**
//	 * can be cloned correctly 
//	 */
//	@Test
//	public void cloned_correctly(){
//		assertEquals(fixture, fixture.clone());
//		assertNotSame(fixture, fixture.clone());
//	}
	
	/**
	 * 
	 */
	
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
	
	/**
	 * produces a random score for testing purposes
	 * @return a random score of internally determined length
	 */
	public static Score get_test_score(){
		for(int i=0; i<fixture_notes.length; i++){fixture_notes[i] = NoteSpec.NoteFactory.getRandomNote();}
		fixture_timing = new float[][]{
				{0f,3f},
				{4f,2f},
				{6f,4f},
				{10f,0.5f},
				{10.5f,1.5f}
		};
		
		TimedNote[] arranged_notes = new TimedNote[5];
		
		for(int i=0; i<arranged_notes.length; i++){
			arranged_notes[i] = new TimedNote(fixture_notes[i], fixture_timing[i][0], fixture_timing[i][1]);
		}
		
		return new Score(arranged_notes);
	}
}
