/**
	 
 */
package nruth.gitfga;

import java.util.*;

import nruth.gitfga.Position.*;

/**
	@author nicholasrutherford
	
 */
public final class Notes { //TODO this needs changing to consider octaves too (tdd)
	public Notes(Note[] notes) {
	    this.notes = notes;
    }
	
	/**
		@return the notes
	 */
	public Note[] getNotes() { return this.notes; }
	
	public String toString(){
		StringBuilder str = new StringBuilder();
	 	for(Note note : notes){
	 		str.append(note+",");
	 	}
	 	str.deleteCharAt(str.length()-1);
	 	return str.toString();
	}
	
	/**
		last modified: 11 Nov 2008
		@return the number of Note objects in the Notes
	 */
	public int getLength(){ return notes.length; }
	
	private final Note[] notes;
	
	public static enum Note{
		C, Db, D, Eb, E, F, Gb, G, Ab, A, Bb, B; //although correct, typically Db = C#, Eb = D#, Ab = G#
		
		public int octave;
		
		public boolean isFoundAt(Position p){
			int octave = Note.values().length;
			
			//start at the open string
			int fret = 0;			
			Note note = p.getString().getOpenStringNote();
			
//			while(fret < p.getFret()){ //unoptimised loop
//				fret++;
//				
//				int scalenum = note.ordinal();
//				//at scale pos 7 move to 0, when there are 8 in scale
//				note = Note.values()[(scalenum<(octave-1))?scalenum+1:0];
//			}
			
			
			//skip a few iterations with this calc, note doesn't change
			while((p.getFret() - fret) >= octave){	fret += octave;	}
			
//			//loop around the enumeration until we find our fret
			//(optimisation) still needs a bit more optimisation, skips octaves but then loops until frets to advance = 0
//			while(fret < p.getFret()){ 	 								 
//				int scalenum = note.ordinal();
//				//at scale pos 7 move to 0, when there are 8 in scale
//				note = Note.values()[(scalenum < (octave-1)) ? scalenum+1 : 0]; //increase note position by the frets to advance
//				fret++;							
//			}
			
			//find the value directly
			//if (frets left to advance) > (octave length - current note ordinal)			
			//	wrap to start of octave, and frets remaining -= the steps needed to reach octave end
			int intervals_left_in_current_octave = octave - (note.ordinal()+1); //zero offset of array requires +1
//			System.out.print("\nlooking for "+p+". Currently at "+fret);
//			System.out.print("\n"+fret+" frets discounted. ");
//			System.out.print("remaining intervals in current octave: "+intervals_left_in_current_octave);
//			System.out.print(" from "+octave+" notes in octave "+" and "+note+" being note number "+(note.ordinal()+1));
			
			if((p.getFret() - fret) > intervals_left_in_current_octave){
				note = Note.values()[0];				
				//add the frets used to cross the interval to the end of the octave + 1 to loop back to the start of the next octave
				fret += (intervals_left_in_current_octave + 1);
//				System.out.print("\nAdvanced an octave, set note to "+note+" and fret is "+fret);
			}
			//note at position = notes[current scan note + frets to advance]
			note = Note.values()[note.ordinal()+(p.getFret()-fret)];
//			System.out.print("\nreturning evaluation of note "+note+" at "+fret+" for "+p+" against "+this);
			
			return this.equals(note);
		}
				
		public Position[] getPositionsForNote(){
			LinkedList<Position> positions = new LinkedList<Position>();
			
			for(GuitarString string : GuitarString.values()){
				
				//first find the note the string begins at, in the open position
				Note note = string.getOpenStringNote();
				
				//then go through the frets, producing a position at each match
				for(int fret = 0; fret<=Assumptions.FRETS; fret++){				
					//if the note matches the desired not produce a position for it
					if(note == this){
						positions.add(new Position(fret, string));
					}
					
					//and advance the note
					int ordinal = note.ordinal(); //what position in the enum is this note				
					note = Note.values()[ (++ordinal == Note.values().length) ? 0 : ordinal ]; //and move to the next, or wrap to 0 if at the end
				}				
			}
			return positions.toArray(new Position[positions.size()]);
		}

	}
}
