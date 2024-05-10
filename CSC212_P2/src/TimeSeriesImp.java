import java.util.Date;  
  
public class TimeSeriesImp<T> implements  TimeSeries<T>  
{  
    DLLComp<CompPair<DataPoint<T>, Date>> DateList;  
      
      
    public TimeSeriesImp()  {  
    	
        DateList = new DLLCompImp<CompPair<DataPoint<T>, Date>>();  // first is value , second is date
    }   
    
    
    
    
    
    // Returns the number of elements in the time series.  
    public int size()   {  
    	
        return DateList.size();  
        
        
    }  
  
    
    
    
    
    
    
    // Returns true if the time series is empty, false otherwise.  
    public boolean empty()  {  
    	
    	
        return DateList.empty();  
        
    }  
  
    
    
    
    // Retrieves the data corresponding to a specific date. This method returns the  
    // data point for the specified date, or null if no such data point exists. 
    
    public T getDataPoint(Date date)  { 
    	
        T object = null;  
        if (DateList.empty())  
            return null;  
          
        DateList.findFirst();  
        for ( int i = 0 ; i < DateList.size() ; i++)  
        {  
            if ( DateList.retrieve().second.compareTo(date) == 0)  
                return  DateList.retrieve().first.value;  
              
            DateList.findNext();  
        }      
        return null;  
    }  
  
      
    // Return all DateList in increasing order.  
    public DLL<Date> getAllDates()  
    {  
       DateList.sort(true); // sort from DLLComp 
       
       
       DLL<Date> DoubleLL = new DLLImp<Date>();  // creat list to fill it with DateList in increasing order 
         
       DateList.findFirst();  
       for ( int i = 0 ; i < DateList.size() ; i++)  
       {  
    	   DoubleLL.insert(DateList.retrieve().second);  // filling the list 
           DateList.findNext();  
       }  
       return DoubleLL;  
    }  
  
    
    
    
    // Returns min date. Time series must not be empty.  
    public Date getMinDate()  
    {  
        DateList.sort(true);  //first we should sort the elemnts 
        return DateList.getMin().second;  
    }  
  
    // Returns max date. Time series must not be empty.  
    public Date getMaxDate()  
    {  
        DateList.sort(true);  //first we should sort the elemnts 
        return DateList.getMax().second;  
    }  
    
    
    
  
                             // Adds a new data point to the time series. If successful, the method returns  
                             // true. If date already exists, the method returns false.  
    public boolean addDataPoint(DataPoint<T> dataPoint)   { 
    	
        CompPair<DataPoint<T>, Date> Value = new CompPair<DataPoint<T>, Date>(dataPoint, dataPoint.date);// parameter is a datapoint with its date  
        
              
        if (DateList.empty())  {  
            DateList.insert(Value);  
            return true;  
        }  
          
        DateList.findFirst();  
        for ( int i = 0 ; i < DateList.size() ; i++)   {  
        	
            if (DateList.retrieve().compareTo(Value) == 0)  //check if it has same date if so return false
                return false;  
              
            DateList.findNext();  
        } 
        
        
        DateList.insert(Value);  
        return true;  
    }  
  
    
    
    
    
    // UpDateList a data point. This method returns true if the date exists and the  
    // update was successful, false otherwise.  
    
    
    
    
    public boolean updateDataPoint(DataPoint<T> dataPoint) {  
    	
            if (DateList.empty())  
                return false;  
          
            DateList.findFirst();  
            for ( int i = 0 ; i < DateList.size() ; i++)  
            {  
                if ( DateList.retrieve().second.compareTo(dataPoint.date) == 0)  
                {  
                    CompPair<DataPoint<T>, Date> val = new CompPair<DataPoint<T>, Date>(dataPoint, dataPoint.date); 
                    
                    
                    val.first.date = DateList.retrieve().second;  
                    val.second = DateList.retrieve().second;  
                    
                    
                    DateList.update(val);  
                    return true;  
                }  
                DateList.findNext();  
            }      
  
        return false;  
    }  
  
    // Removes a data point with given date from the time series. This method  
    // returns true if the data point was successfully removed, false otherwise.  
    public boolean removeDataPoint(Date date) {  
        if (DateList.empty())  
            return false;  
          
        DateList.findFirst();  
        for ( int i = 0 ; i < DateList.size() ; i++)  
        {  
            if ( DateList.retrieve().second.compareTo(date) == 0) // if zero then comparsion is true 
            {  
                DateList.remove();  
                return true;  
            }  
            DateList.findNext();  
        }  
        return false;  
    }  
  
    // Retrieves all data points in the time series as a DLL that is sorted in  
    // increasing order of date.  
    public DLL<DataPoint<T>> getAllDataPoints()  
    {  
        DLL<DataPoint<T>> AllDataPoints_Sorted =  new DLLImp<DataPoint<T>>();  
        if (! DateList.empty())  
        {  
            DateList.sort(true);  //sort by date 
              
            DateList.findFirst();  
            for ( int i = 0 ; i < DateList.size() ; i++)  {  
                AllDataPoints_Sorted.insert(DateList.retrieve().first);  
                DateList.findNext();  
            }  
        }  
        
        
        return AllDataPoints_Sorted;  
    }  
  
                      // Gets data points from startDate to endDate inclusive. If startDate is null,  
                      // fetches from the earliest date. If endDate is null, fetches to the latest  
                      // date. Returns sorted list in increasing date order.  
    public DLL<DataPoint<T>> getDataPointsInRange(Date startDate, Date endDate)  
    {  
        DLL<DataPoint<T>> PIR = getAllDataPoints();  
                                                                                               //PIR stands for point for range 
        if (! PIR.empty())  
        {  
        	PIR.findFirst();  
            // delete all DateList from start less than start date  
            if ( startDate != null)  
            {  
                while ((! PIR.empty() )&&  (PIR.retrieve().date.compareTo(startDate) < 0))  
                	PIR.remove();  
            }  
            if ( endDate != null)  
            {  
                while ((! PIR.empty() )&& ! PIR.last()   
                        && (PIR.retrieve().date.compareTo(endDate) <= 0))  
                	PIR.findNext();  
                  
                while ((! PIR.empty() )&& (PIR.retrieve().date.compareTo(endDate) > 0))  
                	PIR.remove();  
            }  
        }  
        return PIR;  
    }









	
	
      
   
      
      
}  