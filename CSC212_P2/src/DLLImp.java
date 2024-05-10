
public class DLLImp<T> implements DLL<T>{
	
	
	
	
	
	
	
	
	
	
	    public Node<T> head;  
	    public Node<T> current;  
	    private int counter;  
	      
	    public DLLImp() {  
	            head = current = null;  
	            counter = 0;  
	    }  
	  

	    public int size()       {  
	         return counter;  
	     }  
	  
	    
	    public boolean empty()   {  
	        return head == null;  
	    }  
	  
	    
	    public boolean last()   {  
	            return current.next == null;  
	    }  
	      
	    
	    public boolean first()   {  
	            return current.previous == null;  
	    }  
	  
	    
	    public void findFirst()   {  
	        current = head;  
	    }  
	  
	    
	    public void findNext()  {  
	        current = current.next;  
	    }  
	  
	    
	    public void findPrevious()   {  
	        current = current.previous;  
	    }  
	  
	    
	    
	    
	    public T retrieve()  {  
	        return current.data;  
	    }  
	  
	    
	    public void update(T val) {  
	        current.data = val;  
	    }  
	      
	  
	    
	    public void insert(T val)  
	    {  
	            Node<T> tmp = new Node<T>(val);  
	            if(empty()) {  
	                    current = head = tmp;  
	            }  
	            else {  
	                    if (current != null)  
	                    {  
	                        tmp.next = current;  
	                        tmp.previous = current.previous;  
	                          
	                        if ( current.previous != null )  
	                            current.previous.next = tmp;  
	                        else  
	                            head = tmp;  
	                          
	                        current.previous = tmp;  
	                        current = tmp;  
	                    }  
	                    else  
	                    {  
	                        tmp.next = head;  
	                        head.previous = tmp;  
	                        head = current = tmp;  
	                    }  
	            }  
	            counter++;  
	    }  
	  
	     
	    
	    public void remove()  
	    {  
	        if(current == head) {  
	                  head = head.next;  
	                  if(head != null)  
	                   head.previous = null;  
	            }  
	            else {  
	            current.previous.next = current.next;  
	            if(current.next != null)  
	               current.next.previous = current.previous;  
	            }  
	  
	            if(current.next == null)  
	                    current = head;  
	            else  
	          current = current.next;  
	           
		    
		    
		    counter --;  
	    } 
		
		
	
	
	

}
