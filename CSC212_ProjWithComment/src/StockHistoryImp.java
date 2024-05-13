import java.util.Date;  
  
public class StockHistoryImp  implements StockHistory{  
  
       TimeSeries<StockData> timeSeries;  // its just TimeSeries list that contain StockData Objects 
       String CompanyCode;   
          
        public StockHistoryImp() //cunstructer //BigO(1)
        {  
            timeSeries = new TimeSeriesImp<StockData> ();  
        }  
  
        // Returns the number of elements in the history.  
        public int size()  //BigO(1)
        {  
            return timeSeries.size();  
        }  
  
        // Returns true if the history is empty, false otherwise.  
        public boolean empty(){ //BigO(1)
        	
            return timeSeries.empty();  
        }  
  
        // Clears all data from the storage.  
        public void clear()   {  //BigO(1)
        	
            timeSeries = new TimeSeriesImp<StockData> ();  
        }  
  
        // Returns company code.  
        public String getCompanyCode()   { //BigO(1) 
        	
            return CompanyCode;  
        }  
  
        // Sets company code  
        public void SetCompanyCode(String companyCode) {  //BigO(1)
        	
            CompanyCode = companyCode;  
        }  
  
        // Returns stock history as a time series.  
        public TimeSeries<StockData> getTimeSeries()  {  //BigO(1)
        	
            return timeSeries;  
        }  
  
        // Retrieves StockData for a specific date, or null if no data is found.  
        public StockData getStockData(Date date)   {  //BigO(n)
        	
            return timeSeries.getDataPoint(date);  //BigO(n)
        }  
  
        // Adds a new StockData and returns true if the operation is successful, false  
        // otherwise.  
        public boolean addStockData(Date date, StockData stockData){  //BigO(n)
        	
            return timeSeries.addDataPoint(new DataPoint<StockData>(date, stockData));  
        }  

        // Remove the StockData of a given date, and returns true if the operation is  
        // successful, false otherwise.  
        public boolean removeStockData(Date date){  //BigO(n)
        	
            return timeSeries.removeDataPoint(date);  
        }  
  
  
}  