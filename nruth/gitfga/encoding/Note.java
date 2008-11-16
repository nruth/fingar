/**
	 
 */
package nruth.gitfga.encoding;

import nruth.gitfga.encoding.AChromatic.NamedNote;

/**
	@author nicholasrutherford
	
 */
public final class Note {
	public Note(NamedNote named_note, int octave) {
	    this.octave = octave;
	    this.named_note = named_note;
    }

	private int octave;
	private AChromatic.NamedNote named_note;
	


	/**	@return the note's octave (in the piece's range) */
    public int getOctave() {
	    return octave;
    }

	/**	@return the note's name */
    public AChromatic.NamedNote getNote() {
	    return named_note;
    }
	
    public boolean equals(Object object_to_check){
    	if(object_to_check == null) return false;
    	if(object_to_check == this) return true;
    	Note note_to_check = (Note) object_to_check;
    	
    	if(note_to_check.getNote().equals(getNote())
    			&& (note_to_check.getOctave() == getOctave())
    	) return true;
    	else return false;    		
    }
    
    public String toString(){
    	return "["+named_note+octave+"]";
    }
}
