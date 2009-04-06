package nruth.fingar.ga;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BestResultSet implements Set<Arrangement>{

	private int capacity;

	public BestResultSet(int capacity) {
		this.capacity = capacity;
	}

	private static int worst_result_cost(Collection<Arrangement> results){
		int maxcost = 0;
		for(Arrangement arr : results){
			int cost = arr.cost(); 
			if (maxcost < cost) {
				maxcost = cost;
			}
		}
		return maxcost;
	}
	
	private static Arrangement worst_result(Collection<Arrangement> results){
		Arrangement worst=null;
		for(Arrangement arr : results){
			if(worst==null) worst = arr;	
			if (arr.cost() > worst.cost()) { worst = arr;	}
		}
		return worst;
	}
	
	public int size() {
		return results.size();
	}

	private LinkedList<Arrangement> results = new LinkedList<Arrangement>();

	
	/**
	 * compare the given arrangement to the ones stored and add it if:
	 * 	an inferior arrangement is stored, in which case the worst is discarded
	 * 	the collection is below maximum capacity
	 * @param arr
	 */
	public boolean add(Arrangement arr) {
		if(results.size()<capacity){
			results.add(arr);
			return true;
		} else if (arr.cost() < worst_result_cost(results)){
			results.remove(worst_result(results));
			results.add(arr);
			return true;
		}
		else return false;
	}

	public boolean addAll(Collection<? extends Arrangement> c) {
		boolean changed = false;
		for(Arrangement arr : c){
			changed |= add(arr);
		}
		return changed;
	}

	public void clear() { results.clear();	}

	public boolean contains(Object o) {
		return results.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return results.containsAll(c);
	}

	public boolean isEmpty() {	return results.isEmpty(); }

	public Iterator<Arrangement> iterator() {
		return Collections.unmodifiableList(results).iterator();
	}

	public boolean remove(Object arr) {
		return results.remove(arr);
	}

	public boolean removeAll(Collection<?> c) {
		return results.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		boolean changed = false;
		for(Arrangement arr : results){
			if(!c.contains(arr)){
				results.remove(arr);
				changed=true;
			}
		}
		return changed;
	}

	public Arrangement[] toArray() {
		sort_results();
		return results.toArray(new Arrangement[results.size()]);
	}

	public <T> T[] toArray(T[] a) {
		sort_results();
		return (results).toArray(a);
	}
	
	private void sort_results(){
		Collections.sort(results, new Comparator<Arrangement>() {	
			public int compare(Arrangement o1, Arrangement o2) {
				return ((Integer)o1.cost()).compareTo(o2.cost());
			}
		});
	}
}
