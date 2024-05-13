import java.util.Date;

public class TimeSeriesImp<T> implements TimeSeries<T> {//date is the key 

	DLLComp<CompPair<DataPoint<T>, Date>> dates; //Date object used to sort or orgnized the list while DataPoint date still not changed
	
	public TimeSeriesImp() {//BigO(1)
		dates = new DLLCompImp<CompPair<DataPoint<T>, Date>>();
	}

	// Returns the number of elements in the time series.
	public int size() {//BigO(1)
		return dates.size();
	}

	// Returns true if the time series is empty, false otherwise.
	public boolean empty() {//BigO(1)
		return dates.empty();
	}

	// Retrieves the data corresponding to a specific date. This method returns the
	// data point for the specified date, or null if no such data point exists.
	public T getDataPoint(Date date) {//O(n)
		if (dates.empty())
			return null;

		dates.findFirst();
		for (int i = 0; i < dates.size(); i++) {//o(n)
			if (dates.retrieve().second.compareTo(date) == 0)
				return dates.retrieve().first.value;

			dates.findNext();
		}
		return null;
	}

	// Return all dates in increasing order.
	public DLL<Date> getAllDates() {//BigO(n^2)
		dates.sort(true);
		DLL<Date> dll = new DLLImp<Date>();

		dates.findFirst();
		for (int i = 0; i < dates.size(); i++) {
			dll.insert(dates.retrieve().second);// store sorted date  
			dates.findNext();
		}
		return dll;
	}

	// Returns min date. Time series must not be empty.
	public Date getMinDate() {//BigO(n^2) 
		dates.sort(true); 
		return dates.getMin().second;
	}

	// Returns max date. Time series must not be empty.
	public Date getMaxDate() {//BigO(n^2)
		dates.sort(true);
		return dates.getMax().second;
	}

	// Adds a new data point to the time series. If successful, the method returns
	// true. If date already exists, the method returns false.
	public boolean addDataPoint(DataPoint<T> dataPoint) {//BigO(n)
		CompPair<DataPoint<T>, Date> value = new CompPair<DataPoint<T>, Date>(dataPoint, dataPoint.date);

		if (dates.empty()) {
			dates.insert(value);
			return true;
		}
                            // if not empty
		dates.findFirst();
		for (int i = 0; i < dates.size(); i++) {//BigO(n)
			if (dates.retrieve().compareTo(value) == 0)
				return false;

			dates.findNext();
		}
		dates.insert(value);// now we can insert because there is no similirty in date
		return true;
	}

	// Updates a data point. This method returns true if the date exists and the
	// update was successful, false otherwise.
	public boolean updateDataPoint(DataPoint<T> dataPoint) {//BigO(n)
		if (dates.empty())
			return false;

		dates.findFirst();
		for (int i = 0; i < dates.size(); i++) {//BigO(n)
			if (dates.retrieve().second.compareTo(dataPoint.date) == 0) {
				
				CompPair<DataPoint<T>, Date> value = new CompPair<DataPoint<T>, Date>(dataPoint, dataPoint.date);// creat new DataPoint "Value"
				
				value.first.date = dates.retrieve().second;//update date of the object DataPoint
				value.second = dates.retrieve().second;//update Date object in CompPair
				
				dates.update(value);
				return true;
			}
			
			dates.findNext();
		}

		return false;
	}

	// Removes a data point with given date from the time series. This method
	// returns true if the data point was successfully removed, false otherwise.
	public boolean removeDataPoint(Date date) {//BigO(n)
		if (dates.empty())
			return false;

		dates.findFirst();
		for (int i = 0; i < dates.size(); i++) {
			if (dates.retrieve().second.compareTo(date) == 0) {//line: 74
				dates.remove();
				return true;
			}
			dates.findNext();
		}
		return false;
	}

	// Retrieves all data points in the time series as a DLL that is sorted in
	// increasing order of date.
	public DLL<DataPoint<T>> getAllDataPoints() {//BigO(n^2)
		DLL<DataPoint<T>> AllDataPoints = new DLLImp<DataPoint<T>>();
		if (!dates.empty()) {
			dates.sort(false);//decreas

			dates.findFirst();
			for (int i = 0; i < dates.size(); i++) {
				AllDataPoints.insert(dates.retrieve().first);// first because we want data points only 
				dates.findNext();
			}
		}
		
		return AllDataPoints;
	}

	// Gets data points from startDate to endDate inclusive. If startDate is null,
	// fetches from the earliest date. If endDate is null, fetches to the latest
	// date. Returns sorted list in increasing date order.
	public DLL<DataPoint<T>> getDataPointsInRange(Date startDate, Date endDate) {    //BigO(n^2)                                                   //we wil go back for it
		DLL<DataPoint<T>> PointsRange = getAllDataPoints();//creat new list that contain only dataPoint // //BigO(n^2)
		
		if (!PointsRange.empty()) {
			PointsRange.findFirst();
			
			if (startDate != null) {
				while ((!PointsRange.empty()) && (PointsRange.retrieve().date.compareTo(startDate) < 0))// delete any data less than the startDate from PointsRange
					PointsRange.remove();
			
			}
			if (endDate != null) {
				while ((!PointsRange.empty()) && !PointsRange.last()
						&& (PointsRange.retrieve().date.compareTo(endDate) <= 0))// move until you reach endDate
					PointsRange.findNext();

				while ((!PointsRange.empty()) && (PointsRange.retrieve().date.compareTo(endDate) > 0))// then delete all Datapoint that is greater than endDate
					PointsRange.remove();
			}
		}
		return PointsRange;
	}
}
