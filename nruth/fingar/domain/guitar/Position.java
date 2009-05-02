package nruth.fingar.domain.guitar;

import nruth.fingar.domain.guitar.Guitar.GuitarString;

public final class Position {
	@Override
	public int hashCode() { return toString().hashCode(); }
	
	public Position(int fret, GuitarString string) {
	    this.fret = (byte)fret;
	    this.string = string;
    }

	private final byte fret;
	private final GuitarString string;
	/**
    	@return the fret
     */
    public int fret() {
    	return this.fret;
    }
	/**
    	@return the string
     */
    public GuitarString string() {
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
    	
    	return (comp.fret() == this.fret() 
    			&& comp.string().equals(this.string())
    	); 
    }
    
    public String toString(){
    	return string.toString() + "@"+fret;
    }
}