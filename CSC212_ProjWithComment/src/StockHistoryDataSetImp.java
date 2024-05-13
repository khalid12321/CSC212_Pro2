import java.lang.String;
import java.util.Date;

public class StockHistoryDataSetImp implements StockHistoryDataSet {

	Map<String, StockHistory> Companies;

	public StockHistoryDataSetImp() {
		Companies = new BST<String, StockHistory>();
	}

	public int size() {
		return Companies.size();
	}

	public boolean empty() {
		return Companies.empty();
	}

	public void clear() {
		Companies.clear();
	}

	public Map<String, StockHistory> getStockHistoryMap() {
		return Companies;
	}

	public DLLComp<String> getAllCompanyCodes() {
		return Companies.getKeys();
	}

	public StockHistory getStockHistory(String companyCode) {
		if (Companies.find(companyCode))
			return Companies.retrieve();
		return null;
	}

	public boolean addStockHistory(StockHistory stockHistory) {// add stockHistory that its CompnyCode not exist 
		if (!Companies.find(stockHistory.getCompanyCode())) {
			Companies.insert(stockHistory.getCompanyCode(), stockHistory);
			return true;
		}
		return false;
	}

	public boolean removeStockHistory(String companyCode) {// remove by company Code 
		if (Companies.find(companyCode)) {
			Companies.remove(companyCode);
			return true;
		}
		return false;
	}

}