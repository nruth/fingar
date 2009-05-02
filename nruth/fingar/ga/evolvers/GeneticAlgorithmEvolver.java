package nruth.fingar.ga.evolvers;

import java.util.LinkedList;
import java.util.Random;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;
import nruth.fingar.ga.cost_functions.CostFunction;
import nruth.fingar.ga.probability.PdFactory;
import nruth.fingar.ga.probability.ProbabilityDistribution;

public class GeneticAlgorithmEvolver extends Evolver {		
	@Override
	public GeneticAlgorithmEvolver clone() {
		//all data here is primitive pertaining to generation counting, so shallow clone is sufficient
		return (GeneticAlgorithmEvolver) super.clone();
	}
	
	/**
	 * 
	 * @param target_generations
	 * @param p_crossover the likelihood of two parents having crossover applied to their offspring
	 * @param p_mutate  the likelihood of each chromosome being mutated
	 * @param rand Random number generator. Decoupled for testing & sharing if desired
	 */
	public GeneticAlgorithmEvolver(int population_size, int target_generations, double p_crossover, double p_mutate, Random rand, PdFactory pdfac, Breeder breeder, CostFunction cost_function) {
		super();
		this.set_population_size(population_size);
		this.p_crossover = p_crossover;
		this.p_mutate = p_mutate;
		this.current_generation = 1; //incremented elsewhere
		this.rand = rand;
		this.pdfac = pdfac;
		this.target_generations= target_generations;
		this.breeder = breeder;
		this.cost_function = cost_function;
	}
	
	public String toString(){
		return 		"\npopulation size: "+population_size
				+ 	"\nlocus crossover likelihood: "+p_crossover
				+	"\nallele mutation likelihood: "+p_mutate
				+	"\ngeneration "+current_generation+" of "+target_generations;
	}
	
	/**
	 * the population is the nth generation, starting from 1
	 */
	@Override
	public int generation() { return current_generation; }
	
	@Override
	public Population create_successor_population(Population forebears) {
		if(current_generation >= target_generations){ throw new RuntimeException("too many generations produced: current "+current_generation+" target "+target_generations); }
		
		//1. allocate cost to each individual for first generation
		if(current_generation==1){	cost_function.assign_cost(forebears); }
		
		//2. form a probability distribution for selection over the parent population
		ProbabilityDistribution pd = pdfac.probability_distribution(forebears);		
		
		/*	
		 3. produce successor population by:
		while successors < forebears 
			3.1. select from pd twice to get clones of parents X and Y 
			3.2. randomly crossover (X_clone, Y_clone) producing (M, N) according to p_crossover
			3.3. randomly mutate M and N according to p_mutate
			3.3  add M and N to the successor population
		*/
		LinkedList<Arrangement> successors = new LinkedList<Arrangement>();
		while(successors.size() < forebears.size()){
			Arrangement[] parent_clones = pair_selection(pd);
			for(Arrangement successor : crossover(parent_clones, p_crossover, rand, breeder)){
				successors.add(breeder.mutate(successor, rand, p_mutate));
			}
		}
		
		//4. if size(sucessor_population) > fixed population size then drop 1 individual at random from successor_population
		if(successors.size() > forebears.size() ){ successors.remove(new Random().nextInt(successors.size())); }		
		
		
		GeneticAlgorithmEvolver ev;
		ev = this.clone();
		if(++ev.current_generation >= target_generations){ ev.set_has_finished(); }
		Population successor_pop = new Population(ev, successors, forebears.score());
		
		//every pop is assigned costs at creation doing it this way, except the first one handled earlier
		cost_function.assign_cost(successor_pop);
		
		return successor_pop;
	}
	
	/**
	 * select a pair of individuals (with replacement, i.e. multiple selections of each individual allowed) from a pd over a population
	 * @param pd A probability distribution over a data set to use in selecting the individuals to clone and return 
	 * @return cloned individuals selected randomly from the given p, according to its internal probability distribution
	 */
	public static Arrangement[] pair_selection(ProbabilityDistribution pd){
		Arrangement x = pd.next_individual().clone();
		Arrangement y = pd.next_individual().clone();	
		return new Arrangement[] {x,y};
	}
	
	/**
	 * randomly crossover the genetic data of two given individuals and return the result. Where p_crossover is satisfied simple crossover about a random locus is performed.
	 * Clones are *not* created internally, the objects passed in are mutated and returned.
	 * @param cloned_parents
	 * @param p_crossover
	 * @return
	 */
	public static Arrangement[] crossover(Arrangement[] cloned_parents, double p_crossover, Random rand, Breeder breeder){
		if(cloned_parents.length!=2) throw new IndexOutOfBoundsException("should be 2 parents, not "+cloned_parents.length);
		return (rand.nextDouble() < p_crossover) ? breeder.twist_about_random_locus(cloned_parents[0], cloned_parents[1]) : cloned_parents;
	}
	
	private Breeder breeder;
	private Random rand;
	private PdFactory pdfac;
	private int current_generation;
	private int target_generations;
	private double p_crossover;
	private double p_mutate;
	private CostFunction cost_function;
}