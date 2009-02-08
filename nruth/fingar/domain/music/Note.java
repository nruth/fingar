/**
	 
 */
package nruth.fingar.domain.music;

import nruth.fingar.domain.guitar.Guitar;

/**
	@author nicholasrutherford
	
 */
public final class Note implements Comparable<Note>, Cloneable{
	public Note(NamedNote named_note, int octave) {
		if(! nruth.Helpers.in_range(1, octave, Guitar.OCTAVE_RANGE)){ throw new IndexOutOfBoundsException("octave :"+octave+" invalid, must be 1 to "+Guitar.OCTAVE_RANGE);}
	    this.octave = octave;
	    this.named_note = named_note;
    }
	
	public Note clone(){
		//final class so can catch the error
		try {	return (Note) super.clone();
		} catch (CloneNotSupportedException e) { return null; }
	}

	/**	@return the note's octave (in the piece's range) */
    public int octave() {
	    return octave;
    }

	/**	@return the note's name */
    public NamedNote named_note() {
	    return named_note;
    }
	
    public boolean equals(Object object_to_check){
    	if(object_to_check == null) return false;
    	if(object_to_check == this) return true;
    	Note note_to_check = (Note) object_to_check;
    	
    	if(note_to_check.named_note().equals(named_note())
    			&& (note_to_check.octave() == octave())
    	) return true;
    	else return false;    		
    }
    
    public String toString(){
    	return "["+named_note+octave+"]";
    }

	@Override
	public int hashCode() {	return this.toString().hashCode(); }

	public int compareTo(Note cmp) {
		if(cmp == null) throw new NullPointerException();
    	if(cmp == this) return 0;
    	
    	if(cmp.octave() < this.octave()){
    		//this note is in a higher octave
    		//interval difference = complete octaves * 12 + ordinal difference
    		int octave_diff = this.octave - cmp.octave;
    		return -((octave_diff*12) - get_ordinal_diff(cmp));
    	} else if (cmp.octave() > this.octave()){
    		//this note is in a lower octave
    		int octave_diff = cmp.octave - this.octave;
    		return +((octave_diff*12) + get_ordinal_diff(cmp));
    	} else {
    		//same octave
    		return  get_ordinal_diff(cmp);    		
    	}
	}
	
	private int get_ordinal_diff(Note cmp){
		return cmp.named_note().ordinal() - this.named_note().ordinal();
	}
	
	private final int octave;
	private final NamedNote named_note;
}
