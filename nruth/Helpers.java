/**
	 
 */
package nruth;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
	@author nicholasrutherford
	
 */
public final class Helpers {
	/**
	 * @param floor non-inclusive low value
	 * @param x to test 
	 * @param ceiling non-inclusive high value
	 * @return (floor < x < ceiling)
	 */
	public static boolean between(int floor, int x,  int ceiling){
		return (floor < x) && (x < ceiling);
	}
	
	/** 
	 * @param min inclusive low value
	 * @param x to test 
	 * @param max inclusive high value
	 * @return (min <= x <= max)
	 */
	public static boolean in_range(int min, int x,  int max){
		return (min <= x) && (x <= max);
	}
	
	/**
	 * this can be an expensive operation, it checks every element in a against elements in b until finding a match, for each element in a
	 * @param <N>
	 * @param a
	 * @param b
	 * @return
	 */
	public static <N> boolean content_equality(Collection<N> A, Collection<N> B){
		if(A == null || B == null) return false;
		if(A.size() != B.size()) return false;
		
		
		Set<N> elements = new HashSet<N>();
		elements.addAll(A);
		for(N e : elements){ System.out.print(e.toString()); }
		
		System.out.println();
		
		elements.addAll(B);
		for(N e : elements){ System.out.print(e.toString()); }
		
		for(N element : elements){
			if(Collections.frequency(A, element) != Collections.frequency(B,element)) return false;
		}
		
		return true;
	}
}
