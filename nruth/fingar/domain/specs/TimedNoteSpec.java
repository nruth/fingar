package nruth.fingar.domain.specs;

import java.util.LinkedList;

import org.junit.Test;

import static org.junit.Assert.*;

import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.TimedNote;

public class TimedNoteSpec {
	public static TimedNote[] create_random_monophonic_arranged_notes(int note_length){
		LinkedList<TimedNote> list = new LinkedList<TimedNote>();
		
		for(int start_beat=0; start_beat < note_length; start_beat++){
			list.add(new TimedNote(NoteSpec.NoteFactory.getRandomNote(), start_beat, 1f));
		}
		return list.toArray(new TimedNote[list.size()]);
	}
	
	/**
	 * determines equality
	 */
	@Test
	public void equality_test(){
		TimedNote note1 = new TimedNote(NoteSpec.NoteFactory.getRandomNote(), 0f, 1f);
		assertFalse("null object considered equal", note1.equals(null));
		assertTrue("same object not considered equal",note1.equals(note1));		
		assertTrue("same params not considered equal", note1.equals(new TimedNote(note1.note(), note1.start_beat(), note1.duration())));
		assertFalse("different timings not differentiated",note1.equals(new TimedNote(note1.note(), 1f, 1f)));
		
		Note note = new Note(note1.note().named_note().next(), note1.note().octave());
		assertFalse("different note not differentiated",note1.equals(new TimedNote(note, 1f, 1f)));
	}
}
