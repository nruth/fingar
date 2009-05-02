/**
	 
 */
package nruth.fingar.domain.guitar;

import java.util.LinkedList;

import nruth.Helpers;
import nruth.fingar.domain.music.NamedNote;
import nruth.fingar.domain.music.Note;


/**
	@author nicholasrutherford
	
 */
public final class Guitar {
	public static final byte[] FINGERS = {1,2,3,4};
	
	public static final byte FRETS = 24;
	public static final byte OCTAVE_RANGE = 4;
	
	public static enum GuitarString {
		LOW_E, A, D, G, B, HIGH_E;
	
		public Note open_string_note(){
			switch(this){
				case LOW_E: return new Note(NamedNote.E, 1);
				case A: return new Note(NamedNote.A, 1);
				case D: return new Note(NamedNote.D, 1);
				case G: return new Note(NamedNote.G, 2);
				case B: return new Note(NamedNote.B, 2);
				case HIGH_E: return new Note(NamedNote.E, 3);
				default: throw new RuntimeException("undefined guitar string referenced, update Position class if changing number of strings");
			}
		}
		
		public int relative_fret_on_previous_string(){
	    	switch(this){
				case LOW_E: return  0; 
				case A:		return  5; 
				case D:		return  5; 
				case G:		return  4; 
				case B:		return  5; 
				case HIGH_E:return  5; 
				default: throw new RuntimeException("undefined guitar string referenced, update Position class if changing number of strings");
			}
    	}
		
		/**
    	 * get the distance in frets from this open string to the same frequency on another string
    		last modified: 11 Nov 2008
    		@param s the other string to find +/- frets of
    		@return
    	 */
    	public int get_relative_fret_gap(GuitarString	s){
    		int string_gap = s.get_relative_fret_to_bottom_e() - this.get_relative_fret_to_bottom_e();
    		return string_gap;
    	}
    	
    	/**
    	 * the fret played on open E to play at the same frequency as this string
    		last modified: 11 Nov 2008
    		@return relative fret (e.g. A returns 5, D returns 10, G returns 14)
    	 */
    	public int get_relative_fret_to_bottom_e(){
    		GuitarString string = this;
    		int fret = 0;
    		while(string != LOW_E) { 
    			fret += string.relative_fret_on_previous_string(); 
    			string=GuitarString.values()[string.ordinal()-1]; 
    		}
    		return fret;
    	}
	}

	public static LinkedList<Position> get_positions_for_note(Note note){
		LinkedList<Position> positions = new LinkedList<Position>();
		
		for(GuitarString string : GuitarString.values()){
			Position pos = note_on_string(note, string);
			if(pos != null) positions.add(pos);
		}
		return positions;
	}

	public static boolean note_is_found_at(Note note, Position pos_f) {
		Position note_on_string_at = note_on_string(note, pos_f.string());
		if(note_on_string_at == null) return false;
		else return note_on_string_at.fret() == pos_f.fret();
	}
	
	public static Position note_on_string(Note note, GuitarString string){
		Note open_string = string.open_string_note();			
		int fret = open_string.compareTo(note);
		if (Helpers.in_range(0, fret, FRETS)){ return new Position(fret, string); }
		else return null;
	}
}