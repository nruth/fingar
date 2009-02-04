package nruth.fingar.domain.tests;

import java.util.LinkedList;

import nruth.fingar.domain.ArrangedNote;

public class ArrangedNoteSpec {
	public static ArrangedNote[] create_random_monophonic_arranged_notes(int note_length){
		LinkedList<ArrangedNote> list = new LinkedList<ArrangedNote>();
		
		for(int start_beat=0; start_beat < note_length; start_beat++){
			list.add(new ArrangedNote(NoteSpec.NoteFactory.getRandomNote(), start_beat, 1f));
		}
		return list.toArray(new ArrangedNote[list.size()]);
	}
}
