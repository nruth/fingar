package nruth.fingar.specs;

import static org.junit.Assert.*;

import nruth.fingar.FingeredNote;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.domain.specs.NoteSpec;
import nruth.fingar.domain.specs.TimedNoteSpec;
import nruth.fingar.domain.specs.NoteSpec.NoteFactory;

import org.junit.Before;
import org.junit.Test;

public class FingeredNoteTest {
	/**
	 * can create for a note with blank default fingering values
	 */
	@Test
	public void fingered_note_blank_fingering_values() {	uninitialised_fingered_note();	}
	
	/**
	 * causes errors if read before values assigned
	 */
	@Test(expected=NullPointerException.class)
	public void finger_not_initialised_error(){	this.fixture.finger(); }
	@Test(expected=NullPointerException.class)
	public void string_not_initialised_error(){	this.fixture.string(); }
	@Test(expected=NullPointerException.class)
	public void fret_not_initialised_error(){	this.fixture.fret(); }
	
	
	/**
	 * can create with known fingering data
	 */
	@Test
	public void testFingeredNoteIntIntGuitarStringTimedNote() {
		FingeredNote fixture = new FingeredNote(1, 2, GuitarString.A, TimedNoteSpec.create_random_monophonic_arranged_notes(1)[0]); 
		try{		//should be initialised, so try and pull values without exceptions thrown
			assertEquals(fixture.finger(),1);
			assertEquals(fixture.string(),GuitarString.A);
			assertEquals(fixture.fret(),2);
		}catch (NullPointerException e){
			fail(e.toString());
		}
	}

	@Test
	public void testNote() {
		Note note = NoteSpec.NoteFactory.getRandomNote();
		assertEquals(note, new FingeredNote(new TimedNote(note, 1f, 2f)).note());
	}

	/**
	 * the finger can be set
	 */
	@Test
	public void finger_can_be_set() {
		FingeredNote note = uninitialised_fingered_note();
		note.setFinger(1);
		assertEquals(1, note.finger());
	}

	/**
	 * the fret can be set
	 */
	@Test
	public void fret_can_be_set() {
		FingeredNote note = uninitialised_fingered_note();
		note.setFret(12);
		assertEquals(12, note.fret());
	}

	/**
	 * the string can be set
	 */
	@Test
	public void string_can_be_set() {
		FingeredNote note = uninitialised_fingered_note();
		note.setString(GuitarString.B);
		assertEquals(GuitarString.B, note.string());
	}

	/**
	 * the start beat is correctly accessible
	 */
	@Test
	public void start_beat_access() {
		assertEquals(4f,new FingeredNote(new TimedNote(NoteFactory.getRandomNote(), 4f, 2f)).start_beat());
	}

	@Test
	public void duration_access() {
		assertEquals(2f,new FingeredNote(new TimedNote(NoteFactory.getRandomNote(), 4f, 2f)).duration());
	}

	@Test
	public void testRandomise_fingering() {
		FingeredNote initial = initialised_fingered_note();
		FingeredNote random = initial.clone();
		random.randomise_fingering();
		assertFalse(random.finger() == initial.finger());
		assertFalse(random.fret() == initial.fret());
		assertFalse(random.string().equals(initial.string()));
		assertFalse(random.duration() == initial.duration());
		assertFalse(random.start_beat() == initial.start_beat());
	}

	/**
	 * can be cloned and retain correct values
	 */
	@Test
	public void clone_supported_and_correct(){
		FingeredNote original = initialised_fingered_note();
		FingeredNote clone = original.clone();
		assertNotSame(original, clone);
		assertEquals(original, clone);
	}
	
	@Test
	public void testEquals() {
		FingeredNote a = initialised_fingered_note();
		assertEquals("same object",a,a);
		assertFalse("null object", a.equals(null));
		
		FingeredNote b = a.clone(); b.setFret(b.fret()+1);
		assertFalse("different fret",a.equals(b));
		assertFalse("different fret",b.equals(a));
		
		b = a.clone(); b.setFinger(b.finger()+1);
		assertFalse("different finger",a.equals(b));
		assertFalse("different finger",b.equals(a));
		
		b = a.clone(); b.setString(b.string().values()[b.string().ordinal()+1]);
		assertFalse("different finger",a.equals(b));
		assertFalse("different finger",b.equals(a));
		
		fail("add note checks");
		//TimedNote tests
		TimedNote note1, note2;
		note1 = new TimedNote(new Note(NamedNote.B, 1), 1f, 1f);
		note2 = new TimedNote(new Note(NamedNote.C, 2), 2f, 2f);
		
		a = new FingeredNote(note1);
		b = new FingeredNote(note2);
		
		
		//try equality on unint object to check for exception throwing
//		assertFalse(initialised_fingered_note().equals(uninitialised_fingered_note()));
	}
	
	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	private FingeredNote fixture;
	
	public static FingeredNote uninitialised_fingered_note(){
		return new FingeredNote(TimedNoteSpec.create_random_monophonic_arranged_notes(1)[0]);
	}
	public static FingeredNote initialised_fingered_note(){
		return new FingeredNote(1, 2, GuitarString.A, TimedNoteSpec.create_random_monophonic_arranged_notes(1)[0]);
	}
}

