package nruth.fingar;

import nruth.fingar.domain.*;
import nruth.fingar.domain.Assumptions.STRINGS;

public final class FingeredNote {
	private int finger, fret;
	private Assumptions.STRINGS string;
	private final TimedNote note;
	
	public FingeredNote(int finger, int fret, STRINGS string, TimedNote note) {
		this(note);
		this.finger = finger;
		this.fret = fret;
		this.string = string;
	}
	
	public FingeredNote(TimedNote note) {	this.note = note;	}
	public Note note(){	return note.note();	}
	public int finger() {	return finger;  }
	public int fret() {	return fret; }
	public Assumptions.STRINGS string() { 	return string;	}
	
	public void setFret(int fret) {	this.fret = fret;	}	
	public void setFinger(int finger) {	this.finger = finger;	}
	public void setString(Assumptions.STRINGS string) {	this.string = string;	}
}
