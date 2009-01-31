/**
	 
 */
package nruth.fingar.domain;

/**
	@author nicholasrutherford
	
 */
public final class Note implements Comparable<Note>{
	public Note(NamedNote named_note, int octave) {
		if(! nruth.Helpers.in_range(1, octave, Assumptions.OCTAVE_RANGE)){ throw new IndexOutOfBoundsException("octave :"+octave+" invalid, must be 1 to "+Assumptions.OCTAVE_RANGE);}
	    this.octave = octave;
	    this.named_note = named_note;
    }

	private int octave;
	private NamedNote named_note;
	


	/**	@return the note's octave (in the piece's range) */
    public int getOctave() {
	    return octave;
    }

	/**	@return the note's name */
    public NamedNote getNote() {
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

	public int compareTo(Note cmp) {
		if(cmp == null) throw new NullPointerException();
    	if(cmp == this) return 0;
    	
    	if(cmp.getOctave() < this.getOctave()){
    		//this note is in a higher octave
    		//interval difference = complete octaves * 12 + ordinal difference
    		int octave_diff = this.octave - cmp.octave;
    		return -((octave_diff*12) - get_ordinal_diff(cmp));
    	} else if (cmp.getOctave() > this.getOctave()){
    		//this note is in a lower octave
    		int octave_diff = cmp.octave - this.octave;
    		return +((octave_diff*12) + get_ordinal_diff(cmp));
    	} else {
    		//same octave
    		return  get_ordinal_diff(cmp);    		
    	}
	}
	
	private int get_ordinal_diff(Note cmp){
		return cmp.getNote().ordinal() - this.getNote().ordinal();
	}
}
