package nruth.fingar.ga;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class BestResultSet extends TreeSet<Arrangement>{

	private int capacity;

	public BestResultSet(int capacity) {
		super(new Comparator<Arrangement>() {	
			public int compare(Arrangement o1, Arrangement o2) {
				return ((Integer)o1.cost()).compareTo(o2.cost());
			}
		});
		this.capacity = capacity;
	}

	private int worst_result_cost(){
		int maxcost = 0;
		for(Arrangement arr : this){
			int cost = arr.cost(); 
			if (maxcost < cost) {
				maxcost = cost;
			}
		}
		return maxcost;
	}
	
	private Arrangement worst_result(){
		Arrangement worst=null;
		for(Arrangement arr : this){
			if(worst==null) worst = arr;	
			if (arr.cost() > worst.cost()) { worst = arr;	}
		}
		return worst;
	}
	
	/**
	 * compare the given arrangement to the ones stored and add it if:
	 * 	an inferior arrangement is stored, in which case the worst is discarded
	 * 	the collection is below maximum capacity
	 * @param arr
	 */
	public boolean add(Arrangement arr) {
		if(this.size()<capacity){
			return super.add(arr);
		} else if (arr.cost() < worst_result_cost()){
			//only remove a record if it is added (i.e. not already present)
			if (super.add(arr)){
				remove(worst_result());
				return true;
			} else return false;
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
}
