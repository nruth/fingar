/**
	 
 */
package nruth.gitfga.encoding;

/**
	@author nicholasrutherford
	
 */
public class AChromatic {
	public static enum NamedNote{
		A, Bb, B, C, Db, D, Eb, E, F, Gb, G, Ab;

		/**
        	last modified: 15 Nov 2008
        	@return the next note in the scale
         */
        public NamedNote getNext() {
        	int next = this.ordinal()+1;
        	if(next>=NamedNote.values().length){next=0;}
	        return getNote(next);
        }
        
        private NamedNote getNote(int ordinal){ return NamedNote.values()[ordinal]; }
	}
}
