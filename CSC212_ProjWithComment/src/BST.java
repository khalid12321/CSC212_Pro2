public class BST<K extends Comparable<K>, T> implements Map<K, T> {
	
	
	
	//++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++
	class BSTNode<K extends Comparable<K>, T> {
		
		
		public K key;
		public T data;
		public BSTNode<K, T> left, right;

		/** Creates a new instance of BSTNode */
		public BSTNode(K k, T val) {
			key = k;
			data = val;
			left = right = null;
		}

		public BSTNode(K k, T val, BSTNode<K, T> l, BSTNode<K, T> r) {
			key = k;
			data = val;
			left = l;
			right = r;
		}
	}
     //++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++
	
	BSTNode<K, T> root;
	BSTNode<K, T> current;
	int counter;

	public BST() {
		root = current = null;
		counter = 0;
	}

	public int size() {
		return counter;
	}

	public boolean empty() {
		return root == null;
	}

	public void clear() {
		root = current = null;
		counter = 0;
	}

	public T retrieve() {
		T data = null;
		if (current != null)
			data = current.data;
		return data;
	}

	public void update(T e) {
		if (current != null)
			current.data = e;
	}

	// Search for element with key k and make it the current element if it exists.
	// If the element does not exist the current is unchanged and false is returned.
	// This method must be O(log(n)) in average.
	public boolean find(K key) { //This method must be O(log(n)) in average// worst case o(n)
		BSTNode<K, T> p = root;

		if (empty())
			return false;

		while (p != null) {
			if (p.key.compareTo(key) == 0) {
				current = p;
				return true;
			} else if (key.compareTo(p.key) < 0)
				p = p.left;
			else
				p = p.right;
		}
		return false;
	}

	// Return the number of key comparisons needed to find key.
	public int nbKeyComp(K key) {
		int nbKeycomp = 0;

		BSTNode<K, T> p = root;

		if (empty())
			return nbKeycomp;

		while (p != null) {
			nbKeycomp++;
			if (p.key.compareTo(key) == 0) {
				current = p;
				break;
			} else if (key.compareTo(p.key) < 0)
				p = p.left;
			else
				p = p.right;
		}
		return nbKeycomp;

	}

	// Insert a new element if does not exist and return true. The current points to
	// the new element. If the element already exists, current does not change and
	// false is returned. This method must be O(log(n)) in average.
	public boolean insert(K key, T data) {//O(log(n)) if the Binary Tree Balanced  // worst case O(n)

		if (empty()) {
			current = root = new BSTNode<K, T>(key, data);
			counter++;
			return true;
		}
		BSTNode<K, T> parent = null;
		BSTNode<K, T> child = root;

		while (child != null) {
			if (child.key.compareTo(key) == 0) {
				return false;
			} else if (key.compareTo(child.key) < 0) {
				parent = child;
				child = child.left;
			} else {
				parent = child;
				child = child.right;
			}
		}

		if (key.compareTo(parent.key) < 0) {
			parent.left = new BSTNode<K, T>(key, data);
			current = parent.left;
		}

		else {
			parent.right = new BSTNode<K, T>(key, data);
			current = parent.right;
		}
		counter++;
		return true;
	}

	// Remove the element with key k if it exists and return true. If the element
	// does not exist false is returned (the position of current is unspecified
	// after calling this method). This method must be O(log(n)) in average.
	public boolean remove(K key) {
		Boolean removed = false;
		BSTNode<K, T> p;

		p = remove_aux(key, root, removed);
		root = p;

		if (current.key.compareTo(key) == 0)
			current = root;

		return removed;
	}

	private BSTNode<K, T> remove_aux(K key, BSTNode<K, T> p, boolean flag) {// slide 
		BSTNode<K, T> q, child = null;
		if (p == null)
			return null;
		if (key.compareTo(p.key) < 0)
			p.left = remove_aux(key, p.left, flag);
		else if (key.compareTo(p.key) > 0)
			p.right = remove_aux(key, p.right, flag);
		else {
			flag = true;
			if (p.left != null && p.right != null) { // two children
				q = find_min(p.right);
				p.key = q.key;
				p.data = q.data;
				p.right = remove_aux(q.key, p.right, flag);
			} else {
				if (p.right == null) // one child
					child = p.left;
				else if (p.left == null) // one child
					child = p.right;
				return child;
			}
		}
		return p;
	}

	private BSTNode<K, T> find_min(BSTNode<K, T> p) {// from slide //o(n)
		if (p == null)
			return null;

		while (p.left != null) {
			p = p.left;
		}
		return p;
	}

	// Return all keys in the map as a list sorted in increasing order.
	public DLLComp<K> getKeys() {//BigO(n)
		DLLComp<K> dllcomp = new DLLCompImp<K>();
		inOrderTraversal(root, dllcomp);
		return dllcomp;
	}

	private void inOrderTraversal(BSTNode<K, T> node, DLLComp<K> dllcomp) {//BigO(n)
		if (node == null)
			return;
		inOrderTraversal(node.left, dllcomp);
		dllcomp.insert(node.key);
		inOrderTraversal(node.right, dllcomp);
	}
}