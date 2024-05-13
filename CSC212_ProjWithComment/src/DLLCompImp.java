//
public class DLLCompImp<T extends Comparable<T>> extends DLLImp implements DLLComp {


	//this class is exactly like DLL but here i can compare between them 
	
    @Override// sort second element 
    public void sort(boolean increasing) {//BigO(n^2) , Type: Selection sort
        DLLCompImp<T> tmp = new DLLCompImp<T>();// creat new list to sort the list 
        int n = size();//store this.size in the Variable s
        for (int i = 0; i < n; i++) {//o(n)
            T val = increasing ? getMin() : getMax();//o(n) // if increas make "Val" = getMin, else "Val"= getMax
            search(val);//make current point at Val(could minimum or maximum)
            remove();//remove this value from this.list
            findFirst();//make current point at first element
            tmp.insert(val);//insert value to new list 
        }
       //after we filled the new sorted list now we will store it back into  this.list
        tmp.findFirst();
        while (!tmp.last()) {//o(n)
            insert(tmp.retrieve()); //Append all elements except last element
            tmp.findNext();
        }
        insert((T) tmp.retrieve()); //Insert last element
    }

    private DLLCompImp<T> insertEIntoTmp(DLL<T> l) {//Not used (For test) 
        DLLCompImp<T> tmp = new DLLCompImp<T>();

        findFirst();
        tmp.findFirst();

        while (!last()) {
            tmp.insert(l.retrieve()); //Append all elements except last element
            findNext();
        }
        tmp.insert((T) retrieve()); //Insert last element

        //Return current to first element
        findFirst();
        tmp.findFirst();
        return tmp;
    }

    //Take current to val element
    private void search(T val) { //to make current point at the same value of parameter //BigO(n)
        findFirst();
        while (!last()) {
            if (val.equals(retrieve())) {
                return;
            }
            findNext();
        }
    }

    @Override
    public T getMax() {//o(n)
        findFirst();
        T max = (T) retrieve();//began with the Value of the first element of the list.
        while (!last()) {
            if (max.compareTo((T)retrieve()) <= 0) {
                max = (T) retrieve();
            }
            findNext();
        }
        
        if (max.compareTo((T)retrieve()) <= 0) {//check Last element 
            max = (T) retrieve();
        }
        return max;
    }

    
    
    @Override
    public T getMin() {//o(n)
        findFirst();
        T min = (T) retrieve();
        while (!last()) {
            if (min.compareTo((T)retrieve()) >= 0) { //if retrive less than min it will enter the block 
                min = (T) retrieve();
            }
            findNext();
        }
        //Last element check
        
        if (min.compareTo((T)retrieve()) >= 0) {
            min = (T) retrieve();
        }
        return min;
    }

}
