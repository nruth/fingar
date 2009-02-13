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
	 * this can be an expensive operation, intended for testing not operating code
	 * @param <N> the shared type of the collections, supporting a working hashCode method
	 * @param A 
	 * @param B
	 * @return whether A and B have the same elements and element frequencies.
	 */
	public static <N> boolean content_equality(Collection<N> A, Collection<N> B){
		if(A == B) return true;
		if(A == null || B == null) return false;
		if(A.size() != B.size()) return false;
		
		//find the elements from both collections
		Set<N> elements = new HashSet<N>();
		elements.addAll(A);
		elements.addAll(B);
		
		//check the element frequencies are the same in both collections, any disparity = fail
		for(N element : elements){
			if(Collections.frequency(A, element) != Collections.frequency(B,element)) {
				return false;
			}
		}
		
		return true;
	}
}
