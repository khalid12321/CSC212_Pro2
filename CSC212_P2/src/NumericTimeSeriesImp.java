
 
import java.util.Date;  
  
//this class you have to change it   
public class NumericTimeSeriesImp implements NumericTimeSeries {  
	
	public DLLComp<CompPair<DataPoint<Double>, Date>> DP_list;  
    
    
    public NumericTimeSeriesImp()  {  
    	
        DP_list = new DLLCompImp<CompPair<DataPoint<Double>, Date>>();  // first is value , second is date
    }   
    
    
    
    
    
    // Returns the number of elements in the time series.  
    public int size()   {  
    	
        return DP_list.size();  
        
        
    }  
  
    
    
    
    
    
    
    // Returns true if the time series is empty, false otherwise.  
    public boolean empty()  {  
    	
    	
        return DP_list.empty();  
        
    }  
  
    
    
    
    // Retrieves the data corresponding to a specific date. This method returns the  
    // data point for the specified date, or null if no such data point exists. 
    
    public Double getDataPoint(Date date)  { 
    	
    	Double object = null;  
        if (DP_list.empty())  
            return null;  
          
        DP_list.findFirst();  
        for ( int i = 0 ; i < DP_list.size() ; i++)  
        {  
            if ( DP_list.retrieve().second.compareTo(date) == 0)  
                return  DP_list.retrieve().first.value;  
              
            DP_list.findNext();  
        }      
        return null;  
    }  
  
      
    // Return all DP_list in increasing order.  
    public DLL<Date> getAllDates()  {  
    	
    	
    	DLL<Date> DoubleLL = new DLLImp<Date>();  // creat list to fill it with DP_list in increasing order
       DP_list.sort(true); // sort from DLLComp 
       
       
       
         
       DP_list.findFirst();  
       for ( int i = 0 ; i < DP_list.size() ; i++)  
       {  
    	   DoubleLL.insert(DP_list.retrieve().second);  // filling the list 
           DP_list.findNext();  
       }  
       return DoubleLL;  
    }  
  
    
    
    
    // Returns min date. Time series must not be empty.  
    public Date getMinDate()  
    {  
        DP_list.sort(true);  //first we should sort the elemnts 
        return DP_list.getMin().second;  
    }  
  
    // Returns max date. Time series must not be empty.  
    public Date getMaxDate()  
    {  
        DP_list.sort(true);  //first we should sort the elemnts 
        return DP_list.getMax().second;  
    }  
    
    
    
  
                             // Adds a new data point to the time series. If successful, the method returns  
                             // true. If date already exists, the method returns false.  
    public boolean addDataPoint(DataPoint<Double> dataPoint)   { 
    	
        CompPair<DataPoint<Double>, Date> Value = new CompPair<DataPoint<Double>, Date>(dataPoint, dataPoint.date);// parameter is a datapoint with its date  
        
              
        if (DP_list.empty())  {  
            DP_list.insert(Value);  
            return true;  
        }  
          
        DP_list.findFirst();  
        for ( int i = 0 ; i < DP_list.size() ; i++)   {  
        	
            if (DP_list.retrieve().compareTo(Value) == 0)  //check if it has same date if so return false
                return false;  
              
            DP_list.findNext();  
        } 
        
        
        DP_list.insert(Value);  
        return true;  
    }  
  
    
    
    
    
    // UpDP_list a data point. This method returns true if the date exists and the  
    // update was successful, false otherwise.  
    
    
    
    
    public boolean updateDataPoint(DataPoint<Double> dataPoint) {  
    	
            if (DP_list.empty())  
                return false;  
          
            DP_list.findFirst();  
            for ( int i = 0 ; i < DP_list.size() ; i++) {
            	
                if ( DP_list.retrieve().second.compareTo(dataPoint.date) == 0)  
                {  
                    CompPair<DataPoint<Double>, Date> val = new CompPair<DataPoint<Double>, Date>(dataPoint, dataPoint.date); 
                    
                    
                    val.first.date = DP_list.retrieve().second;  
                    val.second = DP_list.retrieve().second;  
                    
                    
                    DP_list.update(val);  
                    return true;  
                }  
                DP_list.findNext();  
            }      
  
        return false;  
    }  
  
    // Removes a data point with given date from the time series. This method  
    // returns true if the data point was successfully removed, false otherwise.  
    public boolean removeDataPoint(Date date) {  
        if (DP_list.empty())  
            return false;  
          
        DP_list.findFirst();  
        for ( int i = 0 ; i < DP_list.size() ; i++)  
        {  
            if ( DP_list.retrieve().second.compareTo(date) == 0) // if zero then comparsion is true 
            {  
                DP_list.remove();  
                return true;  
            }  
            DP_list.findNext();  
        }  
        return false;  
    }  
  
    // Retrieves all data points in the time series as a DLL that is sorted in  
    // increasing order of date.  
    public DLL<DataPoint<Double>> getAllDataPoints()  
    {  
        DLL<DataPoint<Double>> AllDataPoints_Sorted =  new DLLImp<DataPoint<Double>>();  
        if (! DP_list.empty())  
        {  
            DP_list.sort(true);  //sort by date 
              
            DP_list.findFirst();  
            for ( int i = 0 ; i < DP_list.size() ; i++)  {  
                AllDataPoints_Sorted.insert(DP_list.retrieve().first);  
                DP_list.findNext();  
            }  
        }  
        
        
        return AllDataPoints_Sorted;  
    }  
  
                      // Gets data points from startDate to endDate inclusive. If startDate is null,  
                      // fetches from the earliest date. If endDate is null, fetches to the latest  
                      // date. Returns sorted list in increasing date order.  
    public DLL<DataPoint<Double>> getDataPointsInRange(Date startDate, Date endDate)  
    {  
        DLL<DataPoint<Double>> PIR = getAllDataPoints();  
                                                                                               //PIR stands for point for range 
        if (! PIR.empty())  
        {  
        	PIR.findFirst();  
            // delete all DP_list from start less than start date  
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

    
    
    
    
    
    
    
    
    
    
    //============================================================== 
    
    
    
    
    
    public NumericTimeSeries calculateMovingAverage(int period) {
		int n = 0;
		
		NumericTimeSeries x = new NumericTimeSeriesImp();
		
		  if(DP_list.empty() || period <= 0)
			  return null;
		    Double[] DoubleAr = new Double[DP_list.size()];
		    Date[] DateAr = new Date [DP_list.size()];
		    
		    
		    
		    DP_list.sort(true);
		    DP_list.findFirst();
		    
		    
		   for(int i = 0; i<DP_list.size() ; i++) {
		    	DoubleAr[n] = DP_list.retrieve().first.value;
		    	DateAr[n] = DP_list.retrieve().second;
		    	DP_list.findNext(); n++;
		    }
		   
		   
		   for(int i = 0 ; i<DoubleAr.length-period+1 ; i++) {
			   double sum =0; ; int j = 0; int s = i;
			   while(j != period) {
				   sum += DoubleAr[s];
				   j++;s++;
			   }
			DataPoint<Double> f = new DataPoint<Double>(DateAr[s-1] , sum/period);
			  x.addDataPoint(f);
		   }
		   return x;
	}
    
    
    
    
 

    
	public DataPoint<Double> getMax() {
	 if(DP_list.empty())
		 return null;
       DataPoint<Double> DP = DP_list.retrieve().first;
		for(int i = 0; i<DP_list.size() ; i++) {
			if(DP_list.retrieve().first.value.compareTo(DP.value) > 0 ) {
				DP = DP_list.retrieve().first;
			}
			DP_list.findNext();
		}
		return DP;
	}

	@Override
	public DataPoint<Double> getMin() {
		if(DP_list.empty())
			 return null;
		
		
	       DataPoint<Double> DP = DP_list.retrieve().first;
	       
	       
			for(int i = 0; i<DP_list.size() ; i++) {
				
				if(DP_list.retrieve().first.value.compareTo(DP.value) < 0 ) {
					
					 DP = DP_list.retrieve().first;
				}
				DP_list.findNext();
			}
			return DP;
	}


  
}  
