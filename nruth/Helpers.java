/**
	 
 */
package nruth;

/**
	@author nicholasrutherford
	
 */
public final class Helpers {
	public static boolean between(int floor, int x,  int ceiling){
		return (floor < x) && (x < ceiling);
	}
	
	public static boolean in_range(int min, int x,  int max){
		return (min <= x) && (x <= max);
	}
}
