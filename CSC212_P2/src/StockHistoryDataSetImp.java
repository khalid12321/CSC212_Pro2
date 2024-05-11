import java.lang.String;  
import java.util.Date;  
  
public class StockHistoryDataSetImp  implements StockHistoryDataSet {  
          
        Map <String , StockHistory> Comp;  // each node has company code  and stock hostory
          
                                            // IMPORTANT : the key to find company its by using its code 
        public StockHistoryDataSetImp()  
        {  
        	Comp = new BST<String , StockHistory>();  
        }  
          
        
        public int size()  {   // Returns the number of companies for which data is stored. 
        	
            return Comp.size();  
        }  
  
       
        public boolean empty()  {   // Returns true if there are no records, false otherwise.  
        	
            return Comp.empty();  
        }  
  
        
        public void clear()  {   // Clears all data from the storage.  
        	Comp.clear();  
        	
        }  
  
         
        public Map<String, StockHistory> getStockHistoryMap() {  // Returns the map of stock histories, where the key is the company code. 
         
            return Comp;  
        }  
  
        
        public DLLComp<String> getAllCompanyCodes()  {  // Returns the list of all company codes stored in the dataset sorted in  
                                                         // increasing order.  
        
            return Comp.getKeys();  
        }  
  
         
       
        public StockHistory getStockHistory(String companyCode)   {  // Retrieves the stock history for a specific company code. This method returns 
            if ( Comp.find(companyCode))                             // null if no data is found.  
                return Comp.retrieve();  
            return null;  
        }  
  
        
         
        public boolean addStockHistory(StockHistory stockHistory)    { // Adds the stock history of a specific company. This method returns true if the 
                                                                       // operation is successful, false otherwise (company code already exists). 
            if ( ! Comp.find(stockHistory.getCompanyCode())) { //check if the company already exist 
              
            	Comp.insert(stockHistory.getCompanyCode(), stockHistory); // if not, add stock history and its code 
               
            	
            
            	return true;  
            }  
            return false;  
        }  
  
      
        public boolean removeStockHistory(String companyCode)  { 
                                                                        // Removes the stock history of a specific company from the storage. This method 
        	                                                           // returns true if the operation is successful and false if the company code does not exist.  
        
            if ( Comp.find(companyCode))  
            {  
            	Comp.remove(companyCode);  
                return true;  
            }  
            return false;  
        }  
          
}  