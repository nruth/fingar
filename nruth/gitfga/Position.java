/**
	 
 */
package nruth.gitfga;

import nruth.gitfga.Notes.Note;


/**
	@author nicholasrutherford
	
 */
public final class Position {
	public Position(int fret, GuitarString string) {
	    this.fret = fret;
	    this.string = string;
    }

	private final int fret;
	private final GuitarString string;
	/**
    	@return the fret
     */
    public int getFret() {
    	return this.fret;
    }
	/**
    	@return the string
     */
    public GuitarString getString() {
    	return this.string;
    }
	/* (non-Javadoc)
    	@see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
    	if(obj == null) return false;
    	if(obj == this) return true;
    	if(obj.getClass() != getClass()) return false;
    	
    	Position comp = (Position) obj;
    	
    	return (comp.getFret() == this.getFret() 
    			&& comp.getString().equals(this.getString())
    	); 
    }
	
    public String toString(){
    	return string.toString() + "|"+fret+"|";
    }
    
    public static enum GuitarString{
    	LOW_E, A, D, G, B, HIGH_E;
    	
    	public Note getOpenStringNote(){
	    	switch(this){
				case LOW_E: return  Note.E; 
				case A:		return  Note.A; 
				case D:		return  Note.D; 
				case G:		return  Note.G; 
				case B:		return  Note.B; 
				case HIGH_E:return  Note.E; 
				default: throw new RuntimeException("undefined guitar string referenced, update Position class if changing number of strings");
			}
    	}
    }
}
