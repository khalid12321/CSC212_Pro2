
import java.util.Date;  
  
public class StockHistoryImp  implements StockHistory{  
  
	
	
	
	   public TimeSeries<StockData> TimeSer;  //creat OBJECT from timeSeries that deals with StockData 
       
       
       
       public String CompCode;   
          
        public StockHistoryImp()  { //cunstructer
        	
        	TimeSer = new TimeSeriesImp<StockData> ();  
        }  
  
        
        public int size()  {   // Returns the number of elements in the history. 
        	
        	
            return TimeSer.size();  
        }  
  
        
        public boolean empty()    { // Returns true if the history is empty, false otherwise.  
        	
        	
            return TimeSer.empty();  
        }  
  
        
        
        
        public void clear()  {  
        	TimeSer = new TimeSeriesImp<StockData> ();  // Clears all data from the storage.  
        }  
  
        // Returns company code.  
        public String getCompanyCode()  {  
            return CompCode;  
        }  
  
       
        public void SetCompanyCode(String companyCode){   // Sets company code  
        	CompCode = companyCode;  
        }  
  
        
        public TimeSeries<StockData> getTimeSeries()   {  // Returns stock history as a time series.  
            return TimeSer;  
        }  
  
        // Retrieves StockData for a specific date, or null if no data is found.  
        public StockData getStockData(Date date)  {  
            return TimeSer.getDataPoint(date);  
        }  
  
        
        
        
        
        
        
        
        
                                                                              // Adds a new StockData and returns true if the operation is successful, false  
                                                                              // otherwise.  
        public boolean addStockData(Date date, StockData stockData)   {  
        	
        	
            DataPoint<StockData> Data_Point = new DataPoint<StockData>(date,stockData); //stockData could be close price open price , etc...
            
            return TimeSer.addDataPoint(Data_Point);  
        }  
  
          
        public boolean removeStockData(Date date)  {  // Remove the StockData of a given date, and returns true if the operation is  
                                                                     // successful, false otherwise.
        	
        	
            return TimeSer.removeDataPoint(date);  
        }  
  
  
}  
