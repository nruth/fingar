/**
	 
 */
package nruth.gitfga;

import static nruth.Helpers.*;
import nruth.gitfga.Notes.Note;
import nruth.gitfga.Position.GuitarString;

/** coupling to Notes class is avoided by shifting logic involving it into Population, however composited classes are coupled anyway so this may be fallacious.
 * @author nicholasrutherford
 */
@Deprecated
public final class Gene implements Cloneable {
	
	//constructor only provides for known values, random population initialisation is externalised and handled elsewhere
	public Gene(int finger, Position position){
		this.position = position;
		this.finger = finger;
		
		if(!this.is_valid()) throw new RuntimeException("validation failed on creating Gene");
	}
	
	//convenience wrapper
	public Gene(int finger, GuitarString string, int fret){
		this(finger, new Position(fret, string));
	}
	
	/**
	 * are the current values in valid ranges for this guitar fingering Gene
		last modified: 4 Nov 2008
		@return is this gene valid
	 */
	public boolean is_valid() {
		return validateFinger(this.finger) 
			&& validatePosition(this.position);
	}
	
	/**
	 * @return the finger to use
	 */
	public int getFinger() {return this.finger; }
	
	/**
	 * @return the fret to play at
	 */
	public int getFret() { return this.position.getFret();}
	 
	/**
	 * @return the guitar string to play on
	 */
	public Position.GuitarString getString() { return this.position.getString(); }
	
	/**
	@return the position
	 */
	public Position getPosition() {
		return this.position;
	}
	
	/* (non-Javadoc)
		@see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
//		Algorithm from Core Java 2 7th Ed.
		if(obj==this)	return true;
		if(obj==null)	return false;
		if(obj.getClass()!=getClass()) return false;
	    Gene compare = (Gene) obj;
	    	    
	    return (compare.getFinger() == this.getFinger())
	    		&& (compare.getFret() == this.getFret())
	    		&& (compare.getString() == this.getString());
	}
	
	/* (non-Javadoc)
	@see java.lang.Object#clone()
	 */
	@Override
	public Gene clone() throws CloneNotSupportedException {
		//shallow copies should be ok as finger is a primitive type, and Position is immutable so can be shared safely
	    return (Gene)super.clone();
	}
	
	private static boolean validateFinger(int finger){ return in_range(1, finger, Assumptions.FINGERS);}
	private static boolean validatePosition(Position position){ return in_range(0, position.getFret(), Assumptions.FRETS) && (position.getString() != null);}
	
	private final int finger;
	private final Position position;

	/* (non-Javadoc)
    	@see java.lang.Object#toString()
     */
    @Override
    public String toString() {	    
	    return "["+finger+"@"+position+"]";
    }


}
