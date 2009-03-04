package nruth.fingar.ga.specs;

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
import nruth.fingar.ga.Arrangement;

import org.junit.*;

public class ArrangementSpec {
	/**
	 * given a score
	 */
	@Before
	public void arrangement_constructor_given_a_score(){
		score = ScoreSpec.get_test_score();
		arrangement = test_arranger(score);
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
	
	/**
	 * can be tested for equality against other arrangements
	 */
	@Test
	public void equality_against_other_arrangements(){
		/*
		 * a score object
		 * a collection of fingered notes
		 * 
		 * equality if score same & fingered notes same
		 */
		Score score1, score2;
		score1 = ScoreSpec.get_test_score();
		score2 = ScoreSpec.get_test_score();
		Arrangement arr1, arr2, arr3;
		arr1 = new Arrangement(score1);
		arr2 = new Arrangement(score2);
		
		arr3 = arr1.clone();
		assertFalse("different score, but considered equal", arr1.equals(arr2));
		assertEquals("same object equality fail", arr1, arr1);
		assertEquals("same object equality fail", arr2, arr2);
		
		assertEquals("different object same arrangement", arr1, arr3);
		arr3.randomise();
		assertFalse("different values, randomised (rerun to check), considered equal ",arr3.equals(arr1));
	}
	
	@Test
	public void clones_correctly(){
		Arrangement a, b;
		a = arrangement; b = arrangement.clone();
		assertNotSame(a,b);
		assertEquals(a,b);
		
		b.randomise();
		assertFalse("fingered note collection has been shallow copied, so arrangements are not atomic as intended",a.equals(b));
		b.randomise();
		assertFalse("fingered note collection has been shallow copied, so arrangements are not atomic as intended",b.equals(a));
	}
	
	/**
	 * creates hashcodes distinct from nonequal arrangements
	 */
	@Test
	public void hashcode_distinctness(){
		assertEquals("same object hashcode production inconsistent",arrangement.hashCode(), arrangement.hashCode());
		assertEquals("clone object hashcode production inconsistent",arrangement.hashCode(), arrangement.clone().hashCode());
		Arrangement a2 = arrangement.clone();	a2.randomise();
		assertFalse("different values producing same hashcode", arrangement.equals(a2));
	}
	
	/**
	 * holds some ranking metadata for the evolution algorithms
	 */
	@Test
	public void holds_ranking_metadata_for_evolution(){
		arrangement.assign_cost(100);
		assertEquals(100, arrangement.cost());
	}
	
	/**
	 * provides access to fingered notes for replacement by crossover/breeding mechanism
	 */
	@Test
	public void provides_list_access_to_fingered_notes(){
		assertTrue(arrangement.fingered_notes().size()>0);
	}
	
	private Arrangement arrangement;
	private Score score;
	Random seed = new Random();
	
	public static Arrangement test_arranger(Score score){
		return new Arrangement(score);
	}
}