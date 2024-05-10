
import java.util.Date;  
  
  
public class DLLCompImp <T extends Comparable<T>>   implements DLLComp <T>  {  
	
	
	
	
    public DLL<T> Doublelist;   
    
    
    boolean increasing = true;  
      
    
    
    public DLLCompImp()  {                 // cunstructer   
        Doublelist = new DLLImp<T> (); 
        
        
        
    }  
      
    
    
    
     
    public void sort (boolean increasing )  
    {  
        this.increasing = increasing;  
        if (Doublelist.empty())  
            return;  
          
        T[] a;  
        a = (T[] )new Comparable [Doublelist.size()];  // make array with same size of DDL 
        this.findFirst();  
        int i = 0;  
        while (! Doublelist.empty())  
        {  
            a[i++]  = this.retrieve();  // make dll empty and add its contenet to the array 
            this.remove();  
        }  
          
        mergesort (a, 0, a.length-1);  
  
        if (increasing )  
            for (int index = 0 ; index < a.length ; index ++ )  // if incrasing add from array to dll from index zero th end 
                this.insert(a[index]);  
        else  
            for (int index = a.length-1 ; index >= 0 ; index -- )  //else add from the end to the beganing 
                this.insert(a[index]);  
          
          //
    }  
      
 
 
    // Return the maximum 
    public T getMax ()   
    {  
        if ( empty())  
            return null;  
          
        this.findFirst();                                  // change after recive call  ***************************(swap blocks) 
        if (!increasing )  
        {  
            while (!last())  
                findNext();  
        }  
        return this.retrieve();  
    }  
      
    // Return the minimum 
    public T getMin ()    {      // change after recive call  ***************************(swap blocks) 
  
        if ( empty())  
            return null;  
          
        findFirst();  
          
        if (increasing)  
              
        {  
            while ( !last() )  
                this.findNext();  
        }  
        return this.retrieve();  
    }                                               
    @Override  
    public int size() {  
    	
       return Doublelist.size();  
    }  
  
    @Override  
    public boolean empty() {  
        return Doublelist.empty();  
                  
    }  
  
    @Override  
    public boolean last() {  
        return Doublelist.last();  
    }  
  
    @Override  
    public boolean first() {  
         
        return Doublelist.first();  
    }  
  
    
    
    
    @Override  
    public void findFirst() {  
       
    	Doublelist.findFirst();  
    }  
  
    @Override  
    public void findNext() {  
       
    	
    	Doublelist.findNext();  
    }  
  
    @Override  
    public void findPrevious() {  
       
    	Doublelist.findPrevious();  
    }  
  
    @Override  
    public T retrieve() {  
       
    	
    	return Doublelist.retrieve();  
    }  
  
    @Override  
    public void update(T val) {  
       
    	Doublelist.update(val);  
    }  
  
    @Override  
    public void insert(T val) {  
    	
    	
       
    	Doublelist.insert(val);  
    }  
  
    @Override  
    public void remove() { 
    	
        Doublelist.remove();  
    }  
    
    
    
    
    
    
    
    
    private void mergesort (T[] a,  int l , int r )   //BigO(n log(n))
    {  
        if ( l >= r )  
            return;  
        int m = ( l + r ) / 2;  
        mergesort (a, l , m ) ;          
        mergesort (a, m + 1 , r ) ;     
        merge (a, l , m , r ) ;            
    }  
  
    private void merge ( T[] A, int l , int m , int r )   
    {  
        T [] B = (T[]) new Comparable [ r - l + 1];  
        int i = l , j = m + 1 , k = 0;  
      
        while ( i <= m && j <= r )  
        {  
            if ( A[i].compareTo(A[j]) <= 0)  
                B [ k ++] = A[ i ++];  
            else  
                B [ k ++] = A[ j ++];  
        }  
          
        if ( i > m )  
            while ( j <= r )  
                B [ k ++] = A[ j ++];  
        else  
            while ( i <= m )  
                B [ k ++] = A[ i ++];  //test
          
        for ( k = 0; k < B . length ; k ++)  
            A[ k + l ] = B [ k ];  
    }  
  
    
    
    
    
    
}  