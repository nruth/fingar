/**
	 
 */
package nruth;

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
}
