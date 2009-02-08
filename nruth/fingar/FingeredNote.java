package nruth.fingar;

import java.util.LinkedList;
import java.util.Random;

import nruth.fingar.domain.*;
import nruth.fingar.domain.guitar.Guitar;
import nruth.fingar.domain.guitar.Position;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.Note;
import nruth.fingar.domain.music.TimedNote;

public final class FingeredNote {
	private int finger=-1, fret=-1;
	private Guitar.GuitarString string = null;
	private final TimedNote note;
	
	public FingeredNote(int finger, int fret, GuitarString string, TimedNote note) {
		this(note);
		this.finger = finger;
		this.fret = fret;
		this.string = string;
	}
	
	public FingeredNote(TimedNote note) {	this.note = note;	}
	
	public Note note(){	return note.note();	}
	public int finger() {	return finger;  }
	public int fret() {	return fret; }
	public Guitar.GuitarString string() { 	return string;	}
	public float start_beat() {	return note.start_beat(); }
	public float duration() {	return note.duration(); }
	
	public void setFret(int fret) {	this.fret = fret;	}	
	public void setFinger(int finger) {	this.finger = finger;	}
	public void setString(Guitar.GuitarString string) {	this.string = string;	}

	/**
	 * sets valid fret, string and finger values randomly
	 */
	public void randomise_fingering() {
		LinkedList<Position> ps = Guitar.get_positions_for_note(note());
		Position p = ps.get(seed.nextInt(ps.size()));
		setString(p.string());
		setFret(p.fret());
		setFinger(Guitar.FINGERS[seed.nextInt(Guitar.FINGERS.length)]);
	}
	
	public String toString(){
		return note.toString() + " at fi"+finger+";fr"+fret+"; str:"+string;
	}
	
	static final Random seed = new Random();
}
