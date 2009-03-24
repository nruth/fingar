package nruth.fingar;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import sun.security.krb5.Realm;

import nruth.fingar.domain.music.Score;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.FINGAR;
import nruth.fingar.ga.evolvers.Evolver;
import nruth.fingar.ga.evolvers.SimpleHandPositionModelGAEvolver;
import nruth.fingar.specs.MonophonicScales;

public class Run {
	
	private static Score score(){
		return MonophonicScales.c_major_scale();
	}
	
	private static HashMap<String, String> parse_params(String[] args){
		params = new HashMap<String,String>();	
		for(String arg : args){	
			String[] pair = arg.split("=");
			if(pair.length != 2) throw new RuntimeException("invalid arguments "+args);
			if(valid_params.get(pair[0]) == null) throw new RuntimeException("illegal argument "+pair[0]); 
			else params.put(pair[0], pair[1]);	
		}
		return params;
	}
	
	private static HashMap<String, String> params;
	private static HashMap<String, Number> valid_params;
	private static final String ARGN_POPSIZE = "pop", ARGN_GENS="gens", ARGN_FARMSZ="farm_sz", ARGN_PCROSS="p_cross", ARGN_PMUT="p_mutate";
	static {
		valid_params = new HashMap<String, Number>();
		valid_params.put(ARGN_POPSIZE, 10000);
		valid_params.put(ARGN_GENS, 50);
		valid_params.put(ARGN_FARMSZ, 40);
		valid_params.put(ARGN_PCROSS, 0.16);
		valid_params.put(ARGN_PMUT, 0.02);
	}
	
	
	/**
	 
	 */
	public static void main(String[] args){
		if(args[0].equals("--help")){
			System.out.println(
					"* legal params: "                                                                                          +
					"\n * 	pop 		:: 	natural integer"                                                                +
					"\n * 		population size for each generation "                                                       +
					"\n * 	gens 		:: 	natural integer"                                                                +
					"\n * 		number of generations to produce before halting"                                            +
					"\n * 	farm_sz 	:: 	natural integer"                                                                          +
					"\n * 		size of best-result farm (set)"                                                                       +
					"\n * 	p_cross 	:: 	0<=real<=1"                                                                               +
					"\n * 		likelihood of crossover at locus (not likelihood of crossover for individual, see design chapter)"    +
					"\n * 	p_mutate	:: 	0<=real<=1"                                                                               +
					"\n * 		likelihood of allele mutation for all alleles  "                                                      +
					"\n * @param args in the form arg=val as appropriate"  );
			System.exit(0);
		}
		parse_params(args);
		
		String s_popsize = params.get(ARGN_POPSIZE);
		int popsize=  (s_popsize == null) ? (Integer)valid_params.get(ARGN_POPSIZE) : Integer.parseInt(s_popsize);
		
		String s_gens = params.get(ARGN_GENS);
		int generations =  (s_gens == null) ? (Integer)valid_params.get(ARGN_GENS) : Integer.parseInt(s_gens);
		
		String s_farm = params.get(ARGN_FARMSZ);
		int farm_sz =  (s_farm == null) ? (Integer)valid_params.get(ARGN_FARMSZ) : Integer.parseInt(s_farm);
		
		String s_cross = params.get(ARGN_PCROSS);
		double pcross =  (s_cross == null) ? (Double)valid_params.get(ARGN_PCROSS) : Double.parseDouble(s_cross);
		
		String s_mut = params.get(ARGN_PMUT);
		double pmut =  (s_mut == null) ? (Double)valid_params.get(ARGN_PMUT) : Double.parseDouble(s_mut);
		
		
		//process alternatives		
		//and check the list contains a known solution
		Evolver evolver = new SimpleHandPositionModelGAEvolver(popsize, generations, pcross, pmut); //TODO this needs to be the production evolver, whatever that ends up being
		System.out.println(evolver);
		FINGAR ga = new FINGAR(score(), evolver);
		List<Arrangement> results = ga.results();
		
		//print out results section, may be removed from the test
		HashSet<Arrangement> results_set = new HashSet<Arrangement>() ;
		results_set.addAll(results);
		Arrangement[] sorted_results_set =results_set.toArray(new Arrangement[results_set.size()]); 
		Arrays.sort(sorted_results_set, new Comparator<Arrangement>() {
			public int compare(Arrangement o1, Arrangement o2) {
				return ((Integer)o2.cost()).compareTo(o1.cost());
			}
		});
		
		for(Arrangement result : sorted_results_set){
			System.out.println(result+"Cost: "+result.cost()+"\n----\n\n");
		}
	}
}
