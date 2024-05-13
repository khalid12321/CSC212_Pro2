import java.util.Date;

public class NumericTimeSeriesImp implements NumericTimeSeries {

	public DLLComp<CompPair<DataPoint<Double>, Date>> dates;

	public NumericTimeSeriesImp() {
		dates = new DLLCompImp<CompPair<DataPoint<Double>, Date>>();
	}

	@Override
	public int size() {
		return dates.size();
	}

	@Override
	public boolean empty() {
		return dates.empty();
	}

	@Override
	public Double getDataPoint(Date date) {
		if (dates.empty())
			return null;

		dates.findFirst();
		while (!dates.last()) {
			if (dates.retrieve().second.compareTo(date) == 0)
				return dates.retrieve().first.value;
			dates.findNext();
		}
		if (dates.retrieve().second.compareTo(date) == 0)
			return dates.retrieve().first.value;
		return null;
	}

	@Override
	public DLL<Date> getAllDates() {
		DLL<Date> L = new DLLImp<Date>();
		dates.sort(true);
		dates.findFirst();
		for (int i = 0; i < dates.size(); i++) {
			L.insert(dates.retrieve().second);
			dates.findNext();
		}
		return L;
	}

	@Override
	public Date getMinDate() {//BigO(n) , faster than the one in Time Series because we dont need Sort
		return dates.getMin().second;
	}

	@Override
	public Date getMaxDate() {//BigO(n) , faster than the one in Time Series because we dont need Sort
		return dates.getMax().second;
	}

	@Override
	public boolean addDataPoint(DataPoint<Double> dataPoint) {//BigO(n)
		CompPair<DataPoint<Double>, Date> dp = new CompPair<DataPoint<Double>, Date>(dataPoint, dataPoint.date);
		if (dates.empty()) {
			
			dates.insert(dp);
			return true;
		}
		dates.findFirst();
		while (!dates.last()) {
			if (dates.retrieve().second.compareTo(dataPoint.date) == 0)//chec if date exist 
				return false;
			dates.findNext();
		}
		if (dates.retrieve().second.compareTo(dataPoint.date) == 0)//last check
			return false;
		CompPair<DataPoint<Double>, Date> d = new CompPair<DataPoint<Double>, Date>(dataPoint, dataPoint.date);
		dates.insert(dp);
		return true;
	}

	@Override
	public boolean updateDataPoint(DataPoint<Double> dataPoint) {//BigO(n)
		if (dates.empty())
			return false;
		dates.findFirst();
		while (!dates.last()) {
			if (dates.retrieve().second.compareTo(dataPoint.date) == 0) {
				CompPair<DataPoint<Double>, Date> d = new CompPair<DataPoint<Double>, Date>(dataPoint, dataPoint.date);
				dates.update(d);
				return true;
			}
			dates.findNext();
		}
		if (dates.retrieve().second.compareTo(dataPoint.date) == 0) {
			CompPair<DataPoint<Double>, Date> d = new CompPair<DataPoint<Double>, Date>(dataPoint, dataPoint.date);
			dates.update(d);
			return true;
		}

		return false;
	}

	@Override
	public boolean removeDataPoint(Date date) {//BigO(n)
		if (dates.empty())
			return false;
		dates.findFirst();
		while (!dates.last()) {
			if (dates.retrieve().second.compareTo(date) == 0) {
				dates.remove();
				return true;
			}
			dates.findNext();
		}
		if (dates.retrieve().second.compareTo(date) == 0) {
			dates.remove();
			return true;
		}
		return false;
	}

	@Override
	public DLL<DataPoint<Double>> getAllDataPoints() {//BigO(n^2)
		if (dates.empty())
			new DLLImp<DataPoint<Double>>();
		dates.sort(true);
		dates.findFirst();
		DLL<DataPoint<Double>> d = new DLLImp<DataPoint<Double>>();
		for (int i = 0; i < dates.size(); i++) {
			d.insert(dates.retrieve().first);
			dates.findNext();
		}
		return d;
	}
	// the most accurate one is in TimeSeries method
	@Override
	public DLL<DataPoint<Double>> getDataPointsInRange(Date startDate, Date endDate) {//BigO(n^2)// the most accurate one is in TimeSeries method
		if (dates.empty())
			return null;

		DLL<DataPoint<Double>> d = new DLLImp<DataPoint<Double>>();
		dates.sort(true);//BigO(n^2)
		dates.findFirst();
		for (int i = 0; i < dates.size(); i++) {
			if (startDate != null) {
				if (endDate != null) {
					if ((startDate.compareTo(dates.retrieve().second) <= 0) && (endDate.compareTo(dates.retrieve().second) >= 0)) {
						d.insert(dates.retrieve().first);
					}
				} else {
					if (startDate.compareTo(dates.retrieve().second) <= 0) {
						d.insert(dates.retrieve().first);
					}
				}
			} else {
				if (endDate != null) {
					if (endDate.compareTo(dates.retrieve().second) >= 0) {
						d.insert(dates.retrieve().first);
					}
				} else {
					d.insert(dates.retrieve().first);
				}
			}
			dates.findNext();
		}
		return d;
	}

	@Override
	public NumericTimeSeries calculateMovingAverage(int period) {//BigO(n^2)
		int n = 0;

		NumericTimeSeries d = new NumericTimeSeriesImp();
		if (dates.empty() || period <= 0)
			return null;
		Double[] a = new Double[dates.size()];//array for values
		Date[] b = new Date[dates.size()];//array for Dates
		
		dates.sort(true);//BigO(n^2)
		
		dates.findFirst();
		for (int i = 0; i < dates.size(); i++) {// this for loop is to fill both arrays 
			a[n] = dates.retrieve().first.value;
			b[n] = dates.retrieve().second;
			dates.findNext();
			n++;
		}
		for (int i = 0; i < a.length - period + 1; i++) {// detrmine how many avrage we will get////BigO(n)
			double sum = 0;
			
			int j = 0;// for whle loop
			int k = i;
			while (j != period) {// iterate same tims as Period // //BigO(n)
				sum += a[k];
				j++;
				k++;
			}
			DataPoint<Double> f = new DataPoint<Double>(b[k - 1], sum / period);
			d.addDataPoint(f);//notice addDataPoint add ComPair<DataPoint<Double<T>,.....
		}
		return d;
	}

	@Override
	public DataPoint<Double> getMax() {////BigO(n)
		if (dates.empty())
			return null;
		DataPoint<Double> d = dates.retrieve().first;
		for (int i = 0; i < dates.size(); i++) {
			if (dates.retrieve().first.value.compareTo(d.value) > 0) {
				d = dates.retrieve().first;
			}
			dates.findNext();
		}
		return d;
	}

	@Override
	public DataPoint<Double> getMin() {//BigO(n)
		if (dates.empty())
			return null;
		DataPoint<Double> d = dates.retrieve().first;
		for (int i = 0; i < dates.size(); i++) {
			if (dates.retrieve().first.value.compareTo(d.value) < 0) {
				d = dates.retrieve().first;
			}
			dates.findNext();
		}
		return d;
	}

}
