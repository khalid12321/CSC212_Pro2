// Class used to store a pair of elements that is comparable on the second element.
public class CompPair<U, V extends Comparable<V>> extends Pair<U, V> implements Comparable<CompPair<U, V>> {
	public CompPair(U first, V second) {// to store pair of element 
		super(first, second);
	}

	@Override //from Comparable interface , to compare second elemnt to the second elemnt of the other object
	public int compareTo(CompPair<U, V> other) {
		return this.second.compareTo(other.second);
	}
	 
	
	//pair1= khalid sarah ;
			
	//pair2= yousef bayan ;
			
			//pair1.compareTo(pair2);
			
	
	
}