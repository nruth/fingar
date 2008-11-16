/**
	 
 */
package nruth.gitfga.tests;

import static org.junit.Assert.*;

import nruth.gitfga.*;
import nruth.gitfga.tests.GeneTest.GeneFactory;
import nruth.gitfga.tests.NotesTest.NotesFactory;

import org.junit.*;

/**
	@author nicholasrutherford
	
 */
@Deprecated
public class GenotypeTest {
	//fixtures
	private Gene gene1, gene2;
	private Genotype genotype;
	
	/**
		last modified: 4 Nov 2008
		@throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gene1 = GeneFactory.createRandomGene();
		gene2 = GeneFactory.createRandomGene();
		
		genotype = new Genotype(new Gene[]{gene1, gene2});
	}
	

	
	public void genotype_represents_Notes(){
		fail("not implemented");
	}
	
	@Test
	public void test_ConstructionFromGene_and_GeneAccess(){
		Gene[] chromosome = genotype.getChromosome();
		//this is valid, since the ordering does matter, and construction was done during setup
		assertTrue(chromosome.toString()+" "+gene1,chromosome[0].equals(gene1));
		assertTrue(chromosome.toString()+" "+gene2,chromosome[1].equals(gene2));
	}
	
	@Test //prove equals is working properly
	public void testEqualsObject() {
		assertFalse(genotype.equals(null));
		assertTrue(genotype.equals(genotype));
		
		try {
	        assertTrue(genotype.equals(genotype.clone()));
        } catch (CloneNotSupportedException e) {
	        fail("clone failed");
        }
		
		Genotype reversed = new Genotype(new Gene[]{gene2, gene1});
		assertFalse(genotype.equals(reversed));
		assertFalse(reversed.equals(genotype));
		
	}
	
	@Test //test cloning Genotype
	public void testCloneGenotype(){
		try {
	        Genotype clone = genotype.clone();
	        assertTrue(genotype.equals(clone));
	        assertTrue(clone.equals(genotype));
	        assertFalse("clone was copied by reference", clone == genotype);
        } catch (CloneNotSupportedException e) {
	        fail(e.getMessage());
        }
	}
	
//	public static class GenotypeFactory{
//		public static Genotype createRandomGenotype(int gene_count){
//		
//		Gene[] genes = new Gene[gene_count];
//		for(int idx=0; idx<gene_count; idx++){ genes[idx] = GeneFactory.createRandomGene(); }
//		
//		genotype = new Genotype();
//		}
//	}
}
