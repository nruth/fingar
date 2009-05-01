package nruth.fingar.domain.specs;

import static org.junit.Assert.*;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.domain.guitar.FingeredNote.FingerAssignmentException;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.domain.specs.NoteSpec.NoteFactory;

import org.junit.Before;
import org.junit.Test;

public class FingeredNoteTest {
	@Test
	public void testFingeredNoteIntIntGuitarStringTimedNote() { //test filled-in fingered note constructor
		int finger = 3;
		int fret = 5;
		GuitarString string = GuitarString.D;
		Note note = NoteFactory.getRandomNote();
		float notestart=3.0f;
		float notedur=2.0f;
		TimedNote tnote = new TimedNote(note, notestart, notedur);
		
		FingeredNote testfn = new FingeredNote(finger, fret, string, tnote);
		
		//check values are correct
		assertEquals(finger, testfn.finger());
		assertEquals(fret, testfn.fret());
		assertEquals(string, testfn.string());
		assertEquals(note, testfn.note());
		assertEquals(notestart, testfn.start_beat(),0.01f);
		assertEquals(notedur, testfn.duration(),0.01f);
	}

	private FingeredNote fixture = null;
	private int finger;
	private int fret;
	private GuitarString string;
	private Note note;
	private float notestart;
	private float notedur;
	private TimedNote tnote = null;
	
	@Before
	public void test_fixture(){ //reliant on testFingeredNoteIntIntGuitarStringTimedNote
		finger = 3;
		fret = 5;
		string = GuitarString.A;
		note = NoteFactory.getRandomNote();
		notestart=1.0f;
		notedur=1.5f;
		tnote = new TimedNote(note, notestart, notedur);	
		fixture = new FingeredNote(finger, fret, string, tnote);
	}

	@Test
	public void testNote() {
		assertEquals("accessor failure, or Note equality broken", note, fixture.note());
	}

	@Test
	public void testFinger() {
		assertEquals("accessor failure", finger, fixture.finger());
	}

	@Test
	public void testFret() {
		assertEquals("accessor failure", fret, fixture.fret());
	}

	@Test
	public void testString() {
		assertEquals("accessor failure, or GuitarString equality broken", string, fixture.string());
	}

	@Test
	public void testStart_beat() {
		assertEquals("accessor failure, or Note equality broken", notestart, fixture.start_beat(), 0.01f);
	}

	@Test
	public void testDuration() {
		assertEquals("accessor failure, or Note equality broken", notedur, fixture.duration(), 0.01f);
	}

	@Test
	public void testClone() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testEqualsObject() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public void testFingeredNoteTimedNote() {
		
	}
	
	
	@Test
	public void testSetFret() {
		fixture.setFret(fret+1);
		assertEquals(fret+1, fixture.fret());
		
		//fret 0 must set finger to 0
		fixture.setFret(0);
		assertEquals(0, fixture.fret());
		assertEquals(0, fixture.finger());
	}

	@Test
	public void testSetFinger() {
		fixture.setFinger(finger+1);
		assertEquals(finger+1, fixture.finger());
	}
	
	@Test(expected=FingerAssignmentException.class)
	public void error_when_setting_open_string_finger(){
		//reject fret 0 finger not 0
		fixture.setFret(0);
		fixture.setFinger(1);
	}
	
	@Test(expected=FingerAssignmentException.class)
	public void error_when_setting_no_finger_to_position(){
		//reject nonzero fret, no finger
		fixture.setFret(1);
		fixture.setFinger(0);
	}

	@Test
	public void testSetString() {
		fixture.setString(GuitarString.LOW_E);
		assertEquals(GuitarString.LOW_E, fixture.string());
	}

	@Test
	public void testRandomise_fingering() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testHashCode() {
		fail("Not yet implemented"); // TODO
	}
	
	public static class FingeredNoteTestFactory{
		
	}
}
