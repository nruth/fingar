package nruth.fingar.specs;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import nruth.fingar.*;
import nruth.fingar.domain.*;
import nruth.fingar.domain.guitar.Guitar;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.Score;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.domain.specs.ScoreSpec;
import nruth.fingar.domain.specs.NoteSpec.NoteFactory;

import org.junit.*;

public class ArrangementSpec {
	/**
	 * given a score
	 */
	@Before
	public void arrangement_constructor_given_a_score(){
		score = ScoreSpec.get_test_score();
		arrangement = new Arrangement(score);
	}
	
	/**
	 * given a score
	 * 	will store string, fret and finger allocations for each note in the score
	 */
	@Test
	public void is_iterable_correctly(){
		int n=1;
		for(FingeredNote note : arrangement){
			assertEquals(score.get_nth_note(n++).note(), note.note());
		}
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void collection_is_immutable(){ arrangement.iterator().remove();	}
	
	/**
	 * given a score
	 * 	will store string, fret and finger allocations for each note in the score
	 */
	@Test
	public void stores_string_allocations(){
		List<GuitarString> strings = new LinkedList<GuitarString>();
		for(FingeredNote note : arrangement){
			GuitarString string = GuitarString.values()[seed.nextInt(GuitarString.values().length)];
			note.setString(string); 
			strings.add(string);
		}
		
		Iterator<GuitarString> string_iterator = strings.iterator();
		for(FingeredNote note : arrangement){
			assertEquals(string_iterator.next(), note.string());
		}
	}
	
	/**
	 * given a score
	 * 	will store string, fret and finger allocations for each note in the score
	 */
	@Test
	public void stores_finger_allocations(){		
		List<Integer> fingers = new LinkedList<Integer>();
		for(FingeredNote note : arrangement){ 
			int finger = seed.nextInt(Guitar.FINGERS.length);
			note.setFinger(finger);
			fingers.add(finger);
		}
		
		Iterator<Integer> finger_iterator = fingers.iterator();
		for(FingeredNote note : arrangement){
			assertEquals(finger_iterator.next(), note.finger());
		}
	}
	
	/**
	 * given a score
	 * 	will store string, fret and finger allocations for each note in the score
	 */
	@Test
	public void stores_fret_allocations(){	
		List<Integer> frets = new LinkedList<Integer>();
		for(FingeredNote note : arrangement){
			int fret = seed.nextInt(Guitar.FRETS+1);
			frets.add(fret);
			note.setFret(fret); 
		}
		
		Iterator<Integer> fret_iterator = frets.iterator();
		for(FingeredNote note : arrangement){
			assertEquals(fret_iterator.next(), note.fret());
		}
	}
	
	/**
	 * can randomise all fingering data, e.g. for an initial population
	 */
	@Test
	public void randomise_entire_fingering_arrangement(){
		Note random_note = NoteFactory.getRandomNote();
		arrangement = new Arrangement(new Score(new TimedNote[]{
				new TimedNote(random_note, 1f, 1f), new TimedNote(random_note, 2f, 1f), new TimedNote(random_note, 3f, 1f)
		}));
		arrangement.randomise();
		FingeredNote[] notes = new FingeredNote[arrangement.size()];
		int n=0;
		for(FingeredNote note : arrangement ){	notes[n++] = note;	}
		
		assertFalse(notes[0].equals(notes[1]) && notes[0].equals(notes[2]));
		assertFalse(notes[1].equals(notes[2]) && notes[0].equals(notes[2]));
	}
	
	private Arrangement arrangement;
	private Score score;
	Random seed = new Random();
}