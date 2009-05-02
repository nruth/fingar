package nruth.fingar.ga.evolvers.specs;

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import nruth.fingar.domain.music.Score;
import nruth.fingar.ga.evolvers.NDeepRandomEvolver;
import nruth.fingar.ga.probability.GoldbergRouletteWheel;
import nruth.fingar.ga.Arrangement;
import nruth.fingar.ga.Population;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class GoldbergRouletteWheelTest {	
	private Mockery context = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
    
	@Test
	public void test_cpd_selection() {
		final Arrangement arr1 = context.mock(Arrangement.class, "arr1");
		final Arrangement arr2 = context.mock(Arrangement.class, "arr2");
		final Arrangement arr3 = context.mock(Arrangement.class, "arr3");
		final Score score = context.mock(Score.class);
		
    	context.checking(new Expectations() {{
    		allowing (arr1).cost(); will(returnValue(1)); //fitness 1/(1+1) = 1/2
    		allowing (arr2).cost(); will(returnValue(2));//fitness 1/(1+2) = 1/3
    		allowing (arr3).cost(); will(returnValue(3));//fitness 1/(1+3) = 1/4
    	}});

		//set up the population with them
		Population popl = new Population(new NDeepRandomEvolver(3), Arrays.asList(arr1, arr2, arr3), score);		
		GoldbergRouletteWheel wheel = new GoldbergRouletteWheel(popl);		
		
		//the pd will be
		//w_f = 1/2 + 1/3 + 1/4 = 6/12 + 4/12 + 3/12 = 13/12
		//P(i) = f(i) / w_f
		double p1 = (1.0/2.0)/(13.0/12.0);
		double p2 = (1.0/3.0)/(13.0/12.0);
		double p3 = (1.0/4.0)/(13.0/12.0); 
		
		//verify the values are as they should be
		Set<Double> pdview = wheel.view_pd().keySet();
		Iterator<Double> pdv_itr = pdview.iterator();
		assertEquals(p1, pdv_itr.next(), 0.01);
		assertEquals(p1+p2, pdv_itr.next(), 0.01);
		double b3 = pdv_itr.next();
		assertEquals(p1+p2+p3, b3, 0.01);
		assertEquals(1.0, b3, 0.01);
		
		
		//check the boundaries are working as described in the design
		assertEquals(arr1, wheel.get_individual_at_cpd(0.0));
		assertEquals(arr1, wheel.get_individual_at_cpd(p1));
		assertEquals(arr2, wheel.get_individual_at_cpd(p1+p2));
		assertEquals(arr3, wheel.get_individual_at_cpd(p1+p2+p3));
		assertEquals(arr3, wheel.get_individual_at_cpd(1.0));
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
    	assertEquals((1.0/3)/(8/15.0), itr.next(), 0.01);
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
    	
    	double f1 = 1/(2+1.0);
    	double f2 = 1/(4+1.0);
    	double f3 = 1/(0+1.0);
    	double f4 = 1/(0+1.0);
    	double w_f = f1+f2+f3+f4; 
    	double p1 = f1/w_f;
    	double p2 = f2/w_f;
    	double p3 = f3/w_f;
    	double p4 = f4/w_f;
    	GoldbergRouletteWheel wheel = new GoldbergRouletteWheel(popl);
    	Iterator<Double> itr = wheel.view_pd().keySet().iterator();
    	assertEquals(p1, itr.next(), 0.01);
    	assertEquals(p1+p2, itr.next(), 0.01);
    	assertEquals(p1+p2+p3, itr.next(), 0.01);
    	assertEquals(p1+p2+p3+p4, itr.next(), 0.01);
    }
}
