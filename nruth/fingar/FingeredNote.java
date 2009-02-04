package nruth.fingar;

import nruth.fingar.domain.ArrangedNote;
import nruth.fingar.domain.Assumptions;
import nruth.fingar.domain.Note;
import nruth.fingar.domain.Assumptions.STRINGS;

public final class FingeredNote {
	private int finger, fret;
	private Assumptions.STRINGS string;
	private final ArrangedNote note;
	
	public FingeredNote(int finger, int fret, STRINGS string, ArrangedNote note) {
		this(note);
		this.finger = finger;
		this.fret = fret;
		this.string = string;
	}

	public FingeredNote(ArrangedNote note) {
		this.note = note;
	}

	public int getFinger() {
		return finger;
	}

	public void setFinger(int finger) {
		this.finger = finger;
	}

	public int getFret() {
		return fret;
	}

	public void setFret(int fret) {
		this.fret = fret;
	}

	public Assumptions.STRINGS getString() {
		return string;
	}

	public void setString(Assumptions.STRINGS string) {
		this.string = string;
	}

	public ArrangedNote getNote() {
		return note;
	}
}
