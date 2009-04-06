package nruth.fingar.ga;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import javax.swing.event.ListSelectionEvent;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import nruth.fingar.domain.specs.ScoreSpec;
import nruth.fingar.ga.evolvers.Breeder;
import nruth.fingar.ga.evolvers.GeneticAlgorithmEvolver;
import nruth.fingar.ga.probability.GoldbergRouletteWheel;
import nruth.fingar.ga.probability.PdFactory;
import nruth.fingar.ga.specs.ArrangementSpec;
import nruth.fingar.ga.specs.PopulationSpec;

@RunWith(JMock.class)
public class BestResultSetSpec {
	private Mockery context = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
	
	
//	should maintain a fixed capacity
	@Test
	public void maintain_fixed_capacity(){
		BestResultSet rs = new BestResultSet(2);		
		final Arrangement arr1 = context.mock(Arrangement.class,"arr1");
		final Arrangement arr2 = context.mock(Arrangement.class,"arr2");
		final Arrangement arr3 = context.mock(Arrangement.class,"arr3");
		
		context.checking(new Expectations() {{
			allowing (arr1).cost(); will(returnValue(1));
			allowing (arr2).cost(); will(returnValue(2));
			allowing (arr3).cost(); will(returnValue(3));
		}});
		
		rs.add(arr1);
		rs.add(arr2);
		int oldsize = rs.size();
		assertTrue(oldsize > 0);
		
		rs.add(arr3);
		assertEquals(oldsize, rs.size());
	}

//	when pushing onto a full stack the worst result should be discarded if the new result is superior
	@Test
	public void keep_best_results_at_full_capacity(){
		BestResultSet rs = new BestResultSet(2);		
		final Arrangement arr1 = context.mock(Arrangement.class,"arr1");
		final Arrangement arr2 = context.mock(Arrangement.class,"arr2");
		final Arrangement arr3 = context.mock(Arrangement.class,"arr3");
		context.checking(new Expectations() {{
			allowing (arr1).cost(); will(returnValue(1));
			allowing (arr2).cost(); will(returnValue(2));
			allowing (arr3).cost(); will(returnValue(3));
		}});
		
		rs.add(arr3);
		rs.add(arr2);
		assertTrue(rs.containsAll(Arrays.asList(arr3, arr2)));
		assertEquals(2,rs.size());
		
		//now put the better result in and arr3 should be tossed away and arr1 put in
		rs.add(arr1);
		assertTrue(rs.containsAll(Arrays.asList(arr1, arr2)));
		assertFalse(rs.contains(arr3));
		assertEquals(2,rs.size());
	}
	
//	stored results must be unique, i.e. behave as a set
	@Test
	public void behave_as_a_set_no_duplicates(){
		BestResultSet rs = new BestResultSet(2);		
		final Arrangement arr1 = context.mock(Arrangement.class,"arr1");
		final Arrangement arr2 = context.mock(Arrangement.class,"arr2");
		final Arrangement arr3 = context.mock(Arrangement.class,"arr3");
		context.checking(new Expectations() {{
			allowing (arr1).cost(); will(returnValue(1));
			allowing (arr2).cost(); will(returnValue(2));
			allowing (arr3).cost(); will(returnValue(3));
		}});
		
		rs.add(arr1);
		rs.add(arr2);
		assertTrue(rs.containsAll(Arrays.asList(arr1, arr2)));
		assertEquals(2,rs.size());
		
		rs.add(arr1);
		assertEquals(2,rs.size());
		assertEquals(2, rs.toArray().length);
		rs.remove(arr1);
		
		assertEquals(1,rs.size());
		assertEquals(1, rs.toArray().length);
		rs.add(arr1);
		assertEquals(2,rs.size());
		assertEquals(2, rs.toArray().length);
		rs.add(arr1);
		assertEquals(2,rs.size());
		assertEquals(2, rs.toArray().length);	
	}

	//	displayed results should be ordered best first
	@Test
	public void results_are_ordered_best_first(){
		BestResultSet rs = new BestResultSet(3);		
		final Arrangement arr1 = context.mock(Arrangement.class,"arr1");
		final Arrangement arr2 = context.mock(Arrangement.class,"arr2");
		final Arrangement arr3 = context.mock(Arrangement.class,"arr3");
		context.checking(new Expectations() {{
			allowing (arr1).cost(); will(returnValue(1));
			allowing (arr2).cost(); will(returnValue(2));
			allowing (arr3).cost(); will(returnValue(3));
		}});
		
		rs.add(arr1);
		rs.add(arr3);
		rs.add(arr2);
		assertArrayEquals(new Arrangement[] {arr1, arr2, arr3},rs.toArray());
		Iterator<Arrangement> itr = rs.iterator();
		assertEquals(arr1, itr.next());
		assertEquals(arr2, itr.next());
		assertEquals(arr3, itr.next());
	}
}
