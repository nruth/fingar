package nruth.fingar.ga.evolvers.specs;

import static junit.framework.Assert.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import nruth.fingar.domain.guitar.FingeredNote;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import nruth.fingar.domain.music.Score;
import nruth.fingar.domain.music.TimedNote;
import nruth.fingar.domain.specs.ScoreSpec;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;
import nruth.fingar.ga.evolvers.Breeder;
import nruth.fingar.ga.evolvers.GeneticAlgorithmEvolver;
import nruth.fingar.ga.probability.GoldbergRouletteWheel;
import nruth.fingar.ga.probability.PdFactory;
import nruth.fingar.ga.probability.ProbabilityDistribution;
import nruth.fingar.ga.specs.ArrangementSpec;
import nruth.fingar.ga.specs.PopulationSpec;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.*;
import org.junit.runner.RunWith;
import org.jmock.lib.legacy.ClassImposteriser;


@RunWith(JMock.class)
public class GeneticAlgorithmEvolverSpec {
	private Mockery context = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
    
    @Test
    public void population_should_be_costed_on_creation(){
    	fail("...");
    }
    
    @Test
    public void test_selection_pipes_to_pd(){
    	final ProbabilityDistribution pd = context.mock(ProbabilityDistribution.class);
    	final Arrangement arr1 = context.mock(Arrangement.class, "arr1");
    	final Arrangement arr1clone = context.mock(Arrangement.class, "arr1clone");
		final Arrangement arr2 = context.mock(Arrangement.class, "arr2");
		final Arrangement arr2clone = context.mock(Arrangement.class, "arr2clone");
		
    	context.checking(new Expectations() {{
    		exactly(2).of (pd).next_individual(); will(onConsecutiveCalls(returnValue(arr1), returnValue(arr2)));
    		oneOf (arr1).clone(); will(returnValue(arr1clone));
    		oneOf (arr2).clone(); will(returnValue(arr2clone));
    	}});
    	
    	Arrangement[] returned = GeneticAlgorithmEvolver.pair_selection(pd);
    	assertEquals(arr1clone, returned[0]);
    	assertEquals(arr2clone, returned[1]);	
    }
    
    @Test
    public void correct_wheel_for_single_nonzero_costed_individual(){
    	final Population popl = context.mock(Population.class);
    	final Arrangement arr1 = context.mock(Arrangement.class, "arr1");
    	TreeMap<Double, String> summary = new TreeMap<Double, String>();
    	
    	context.checking(new Expectations() {{
    		allowing (popl).iterator(); will(returnIterator(arr1));
    		allowing (arr1).cost(); will(returnValue(1));
    	}});    	
    	summary.put(1.0, arr1.toString());
    	GoldbergRouletteWheel wheel = new GoldbergRouletteWheel(popl);
    	assertEquals(summary, wheel.view_pd());
    }
    
    @Test
    public void correct_wheel_for_two_nonzero_costed_individual(){
    	final Population popl = context.mock(Population.class);
    	final Arrangement arr1 = context.mock(Arrangement.class, "arr1");
    	final Arrangement arr2 = context.mock(Arrangement.class, "arr2");
    	
    	context.checking(new Expectations() {{
    		allowing (popl).iterator(); will(returnIterator(arr1, arr2));
    		allowing (arr1).cost(); will(returnValue(2));
    		allowing (arr2).cost(); will(returnValue(4));
    	}});

    	GoldbergRouletteWheel wheel = new GoldbergRouletteWheel(popl);
    	Iterator<Double> itr = wheel.view_pd().keySet().iterator();
    	assertEquals(2.0/3, itr.next(), 0.01);
    	assertEquals(1.0, itr.next(), 0.01);
    }
    
    @Test
    public void correct_wheel_for_single_zero_costed_individuals(){
    	final Population popl = context.mock(Population.class);
    	final Arrangement arr2 = context.mock(Arrangement.class, "arr2");
    	TreeMap<Double, String> summary = new TreeMap<Double, String>();
    	
    	context.checking(new Expectations() {{
    		allowing (popl).iterator(); will(returnIterator(arr2));
    		allowing (arr2).cost(); will(returnValue(0));
    	}});
    	
    	summary.put(1.0, arr2.toString());
    	GoldbergRouletteWheel wheel = new GoldbergRouletteWheel(popl);
    	assertEquals(summary, wheel.view_pd());
    }
    
    @Test
    public void correct_wheel_for_mixed_zero_nonzero_costed_individuals(){
    	final Population popl = context.mock(Population.class);
    	final Arrangement arr1 = context.mock(Arrangement.class, "arr1");
    	final Arrangement arr2 = context.mock(Arrangement.class, "arr2");
    	final Arrangement arr3 = context.mock(Arrangement.class, "arr3");
    	final Arrangement arr4 = context.mock(Arrangement.class, "arr4");
    	
    	context.checking(new Expectations() {{
    		allowing (popl).iterator(); will(returnIterator(arr1, arr2, arr3, arr4));
    		allowing (arr1).cost(); will(returnValue(2));
    		allowing (arr2).cost(); will(returnValue(4));
    		allowing (arr3).cost(); will(returnValue(0));
    		allowing (arr4).cost(); will(returnValue(0));
    	}});
    	
    	GoldbergRouletteWheel wheel = new GoldbergRouletteWheel(popl);
    	Iterator<Double> itr = wheel.view_pd().keySet().iterator();
    	assertEquals(2.0/3, itr.next(), 0.01);
    	assertEquals(1.0, itr.next(), 0.01);
    }
    
    @Test
    public void test_crossover_is_probabilistic(){
    	final Random mockrand = context.mock(Random.class);
    	final Score score = ScoreSpec.get_test_score();		
		final Arrangement arr1 = ArrangementSpec.test_arranger(score);		
		final Arrangement arr2 = ArrangementSpec.test_arranger(score);
		arr1.randomise();
		arr2.randomise();
		final Breeder mockbreeder = context.mock(Breeder.class);
		
		//first time it doesn't crossover
		context.checking(new Expectations() {{
			oneOf (mockrand).nextDouble(); will(returnValue(0.7));			
		}});
		
		
    	Arrangement[] ret = GeneticAlgorithmEvolver.crossover(new Arrangement[]{arr1, arr2}, 0.5, mockrand, mockbreeder);
    	assertEquals(arr1, ret[0]);
    	assertEquals(arr2, ret[1]);
    	 	
    	//second time it does crossover
		context.checking(new Expectations() {{
			oneOf (mockrand).nextDouble(); will(returnValue(0.3));
			oneOf (mockbreeder).twist_about_random_locus(arr1, arr2); will(returnValue(new Arrangement[]{arr2, arr1}));
		}});
		
		//it should switch the values round when twisting occurs via the mock
		ret = GeneticAlgorithmEvolver.crossover(new Arrangement[]{arr1, arr2}, 0.5, mockrand, mockbreeder);
		assertEquals(arr2, ret[0]);
    	assertEquals(arr1, ret[1]);
    }

    @Test
    public void mutation_called(){
    	final Breeder breeder = context.mock(Breeder.class);
    	final PdFactory pdfacmoc = context.mock(PdFactory.class);
    	
    	final Population popl = PopulationSpec.test_population();
    	for(Arrangement arr : popl){ arr.assign_cost(1); }
    	
    	final GeneticAlgorithmEvolver ga = new GeneticAlgorithmEvolver(10, 2, 0.5, 1, new Random(), pdfacmoc, breeder) {		
			@Override
			protected void assign_costs_to_population(Population pop) {
				for(Arrangement arr : pop){ arr.assign_cost(1);}
			}
		};
    	
    	context.checking(new Expectations() {{
    		allowing (breeder).twist_about_random_locus(with(any(Arrangement.class)), with(any(Arrangement.class)));
    		exactly(popl.size()).of (breeder).mutate(with(any(Arrangement.class)), with(any(Random.class)), with(any(Double.class)));    		
    		oneOf (pdfacmoc).probability_distribution(popl); will(returnValue(new GoldbergRouletteWheel(popl)));
    	}});	
    	
    	ga.create_successor_population(popl);
    }
}
