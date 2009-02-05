/**
	 
 */
package nruth.gitfga;

import nruth.gitfga.Notes.Note;


/**
	@author nicholasrutherford
	
 */
@Deprecated
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
    	
    	public int get_relative_fret_on_previous_string(){
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
    			fret += string.get_relative_fret_on_previous_string(); 
    			string=GuitarString.values()[string.ordinal()-1]; 
    		}
    		
    		return fret;
    	}
    }
}
