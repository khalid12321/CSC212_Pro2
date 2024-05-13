import java.util.Date;

public class StockDataSetAnalyzerImp implements StockDataSetAnalyzer {

	StockHistoryDataSet SHDA = new StockHistoryDataSetImp();

	// Returns dataset.
	public StockHistoryDataSet getStockHistoryDataSet() {
		return SHDA;
	}

	// Sets dataset.
	public void setStockHistoryDataSet(StockHistoryDataSet stockHistoryDataSet) {
		DLLComp<String> companies_name = stockHistoryDataSet.getAllCompanyCodes();
		if (!companies_name.empty()) {
			companies_name.findFirst();
			for (int i = 0; i < companies_name.size(); i++) {

				StockHistory SH = new StockHistoryImp();
				SH.SetCompanyCode(companies_name.retrieve());
				StockHistory company = stockHistoryDataSet.getStockHistory(companies_name.retrieve());
				DLL<DataPoint<StockData>> data = company.getTimeSeries().getAllDataPoints();
				if (!data.empty()) {
					data.findFirst();
					for (int j = 0; j < data.size(); j++) {
						DataPoint dpNew = data.retrieve();
						StockData sdNew = (StockData) dpNew.value;

						SH.addStockData(dpNew.date,
								new StockData(sdNew.open, sdNew.close, sdNew.high, sdNew.low, sdNew.volume));

						data.findNext();
					}

				} else
					System.out.println("There is No Company Available");

				SHDA.addStockHistory(SH);
				companies_name.findNext();
			}
		} else
			System.out.println("There is No Company Available");
	}

	// Returns the list of company codes sorted according to their stock performance
	// between startDate and endDate. It returns an empty list if either dates is
	// null.
	public DLLComp<CompPair<String, Double>> getSortedByPerformance(Date startDate, Date endDate) {
		DLLComp<CompPair<String, Double>> dllcomp = new DLLCompImp<CompPair<String, Double>>();
		DLLComp<String> companies_name = SHDA.getAllCompanyCodes();

		if (!(startDate == null && endDate == null) && (!companies_name.empty())) {
			companies_name.findFirst();
			for (int i = 0; i < companies_name.size(); i++) {

				String CompanyName = companies_name.retrieve();
				DLL<DataPoint<StockData>> data = SHDA.getStockHistory(CompanyName).getTimeSeries()
						.getDataPointsInRange(startDate, endDate);

				Double performance = new Double(0);

				if (!data.empty()) {
					data.findFirst();
					double day1 = data.retrieve().value.close;
					while (!data.last())
						data.findNext();
					double day2 = data.retrieve().value.close;

					performance = (day2 - day1) / day1;
				}

				CompPair<String, Double> val = new CompPair<String, Double>(CompanyName, performance);
				dllcomp.insert(val);

				companies_name.findNext();
			}
			dllcomp.sort(false);
		}
		return dllcomp;
	}

	// Returns the list of company codes sorted according to their total volume
	// between startDate and endDate inclusive. If startDate is null, fetches from
	// the earliest date. If endDate is null, fetches to the latest
	// date.
	public DLLComp<CompPair<String, Long>> getSortedByVolume(Date startDate, Date endDate) {
		DLLComp<CompPair<String, Long>> dllcomp = new DLLCompImp<CompPair<String, Long>>();
		DLLComp<String> companies_name = SHDA.getAllCompanyCodes();

		if (!(startDate == null && endDate == null) && (!companies_name.empty())) {
			companies_name.findFirst();
			for (int i = 0; i < companies_name.size(); i++) {

				String CompanyName = companies_name.retrieve();
				DLL<DataPoint<StockData>> data = SHDA.getStockHistory(CompanyName).getTimeSeries().getDataPointsInRange(startDate, endDate);
				
				Long vol = new Long(0);

				if (!data.empty()) {
					data.findFirst();
					for (int j = 0; j < data.size(); j++) {
						vol += data.retrieve().value.volume;
						data.findNext();
					}
				}

				CompPair<String, Long> val = new CompPair<String, Long>(CompanyName, vol);
				dllcomp.insert(val);

				companies_name.findNext();
			}
			dllcomp.sort(false);
		}
		return dllcomp;
	}

	// Returns the list of company codes sorted by the maximum single day price
	// increase between startDate and endDate inclusive. If startDate is null,
	// fetches from the earliest date. If endDate is null, fetches to the latest
	// date.
	public DLLComp<CompPair<Pair<String, Date>, Double>> getSortedByMSDPI(Date startDate, Date endDate) {
		DLLComp<CompPair<Pair<String, Date>, Double>> dllcomp = new DLLCompImp<CompPair<Pair<String, Date>, Double>>();
		DLLComp<String> companies_name = SHDA.getAllCompanyCodes();

		if (!(startDate == null && endDate == null) && (!companies_name.empty())) {
			companies_name.findFirst();
			for (int i = 0; i < companies_name.size(); i++) {

				String CompanyName = companies_name.retrieve();
				Pair<String, Date> company_data = null;
				Double maxSPDI = new Double(0);
				Date maxDay = endDate;

				DLL<DataPoint<StockData>> data = SHDA.getStockHistory(CompanyName).getTimeSeries()
						.getDataPointsInRange(startDate, endDate);

				if (!data.empty()) {
					DLLComp<CompPair<Date, Double>> allDays = new DLLCompImp<CompPair<Date, Double>>();
					data.findFirst();
					while (!data.last()) {
						double SDPI = ((data.retrieve().value.close - data.retrieve().value.open)
								/ (data.retrieve().value.open));
						CompPair<Date, Double> val = new CompPair<Date, Double>(data.retrieve().date, SDPI);
						allDays.insert(val);
						data.findNext();
					}
					double SDPI = ((data.retrieve().value.close - data.retrieve().value.open)
							/ (data.retrieve().value.open));
					CompPair<Date, Double> val = new CompPair<Date, Double>(data.retrieve().date, SDPI);
					allDays.insert(val);

					allDays.sort(true);
					maxDay = allDays.getMax().first;
					maxSPDI = new Double(allDays.getMax().second);
				}

				company_data = new Pair<String, Date>(CompanyName, maxDay);
				CompPair<Pair<String, Date>, Double> val = new CompPair<Pair<String, Date>, Double>(company_data,
						maxSPDI);
				dllcomp.insert(val);

				companies_name.findNext();
			}
			dllcomp.sort(false);
		}
		return dllcomp;
	}
}
