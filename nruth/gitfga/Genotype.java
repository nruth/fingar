package nruth.gitfga;

import nruth.gitfga.Notes.Note;

/**
	@author nicholasrutherford
	
 */
@Deprecated
public final class Genotype implements Cloneable {
	private final Gene[] chromosome;
	
	/**
		last modified: 4 Nov 2008
		@return immutable Gene[] array of fingering data
	 */
	public Gene[] getChromosome(){
		return chromosome; //it is immutable
	}
	
	/**
	 * This is the constructor to be used when creating successor populations
	 * It will use the provided chromosome
		@param provided
	 */
	public Genotype(Gene[] provided_chromosome){
		this.chromosome = provided_chromosome;
	}

	/* (non-Javadoc)
    	@see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object otherObject) {
    	//Algorithm from Core Java 2 7th Ed.
    	if(this == otherObject) return true; 
    	if(otherObject == null) return false; 
    	
	    if(otherObject.getClass() != getClass()) return false; 
	    Genotype other = (Genotype) otherObject; //checked, now cast
	    
	    //now deep compare
	    return this.chromosome.equals(other.chromosome); 
    }

	/* (non-Javadoc)
    	@see java.lang.Object#clone()
     */
    @Override
    public Genotype clone() throws CloneNotSupportedException {
	    return (Genotype)super.clone();
    }

	/** indexed from position 1, unlike an array. Returns reference, not copy
    	last modified: 8 Nov 2008
    	@param locus
    	@return the gene found at given chromosome locus
     */
    public Gene getGeneAtLocus(int locus) { //TODO test this method, as it can be broken easily due to 0/1 array vs locus start position index
	    return chromosome[locus-1];
    }
    
    public String toString(){
		StringBuilder str = new StringBuilder();
	 	for(Gene gene : chromosome){
	 		str.append(gene+",");
	 	}
	 	str.deleteCharAt(str.length()-1);
	 	return super.toString() + ": "+str.toString();
	}
}
