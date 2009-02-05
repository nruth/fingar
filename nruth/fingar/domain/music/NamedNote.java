/**
	 
 */
package nruth.fingar.domain.music;

/**
	@author nicholasrutherford
	
 */
public enum NamedNote{
	E, F, Gb, G, Ab, A, Bb, B, C, Db, D, Eb; 

	/**
    	@return the next note in the scale
     */
    public NamedNote next() {
    	int next = this.ordinal()+1;
    	if(next>=NamedNote.values().length){next=0;}
        return getNote(next);
    }
    
    private NamedNote getNote(int ordinal){ return values()[ordinal]; }

	/**
    	last modified: 17 Dec 2008
    	@param interval how many semitones to increase scale position by.
    	@return the note at interval positions higher in the e-chromatic scale
     */
    public NamedNote advance(int interval) {
	    interval %= 12; //remove octaves
	    NamedNote note = this;
	    while(interval-- > 0){ note = note.next(); }
	    return note;
    }

	/**
    	@return the degree of the note in the e-chromatic scale
     */
    public int getDegree() {   return this.ordinal()+1;   }
}