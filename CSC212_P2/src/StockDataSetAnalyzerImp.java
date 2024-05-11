
import java.util.Date;  
  
public class StockDataSetAnalyzerImp implements StockDataSetAnalyzer {  
  
        StockHistoryDataSet SHDA = new StockHistoryDataSetImp();  
          
        // Returns dataset.  
        public StockHistoryDataSet getStockHistoryDataSet()  
        {  
            return SHDA;  
        }  
  
        // Sets dataset.  
        public void setStockHistoryDataSet(StockHistoryDataSet stockHistoryDataSet)  
        {  
                DLLComp <String> companies_name = stockHistoryDataSet.getAllCompanyCodes();  
            if (! companies_name.empty())  
            {  
                companies_name.findFirst();  
                for ( int i=0; i< companies_name.size() ; i++)  
                {  
                      
                    StockHistory SH = new StockHistoryImp();  
                    SH.SetCompanyCode(companies_name.retrieve());  
                    StockHistory company = stockHistoryDataSet.getStockHistory(companies_name.retrieve());  
                    DLL <DataPoint<StockData>> data = company.getTimeSeries().getAllDataPoints();  
                    if (! data.empty())  
                    {  
                        data.findFirst();  
                        for ( int j=0; j< data.size() ; j++)  
                        {  
                            DataPoint sp_new = data.retrieve();  
                            StockData sd_new = (StockData) sp_new.value;  
                              
                            SH.addStockData(sp_new.date,   
                                    new StockData(sd_new.open, sd_new.close, sd_new.high, sd_new.low, sd_new.volume));  
                              
                            data.findNext();  
                        }  
                          
                    }  
                    else  
                        System.out.println("No Company Available");  
  
                     
                    SHDA.addStockHistory(SH);  
                    companies_name.findNext();  
                }  
           }  
            else  
                System.out.println("No Company Available");  
        }  
  
        // Returns the list of company codes sorted according to their stock performance  
        // between startDate and endDate. It returns an empty list if either dates is  
        // null.  
        public DLLComp<CompPair<String, Double>> getSortedByPerformance(Date startDate, Date endDate)  
        {  
            DLLComp<CompPair<String, Double>> dllcomp = new DLLCompImp <CompPair<String, Double>>();  
            DLLComp <String> companies_name = SHDA.getAllCompanyCodes();  
              
            if (! (startDate == null && endDate == null) && (! companies_name.empty()))  
            {  
                companies_name.findFirst();  
                for ( int i=0; i< companies_name.size() ; i++)  
                {  
                      
                   String CompanyName = companies_name.retrieve();  
                    DLL <DataPoint<StockData>> data = SHDA.getStockHistory(CompanyName).getTimeSeries().getDataPointsInRange(startDate, endDate);  
  
                    Double performance = new Double(0);  
                      
                    if (! data.empty())  
                    {  
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
                dllcomp.sort(true);  
           }  
            return dllcomp;  
        }  
  
        // Returns the list of company codes sorted according to their total volume  
        // between startDate and endDate inclusive. If startDate is null, fetches from  
        // the earliest date. If endDate is null, fetches to the latest  
        // date.  
        public DLLComp<CompPair<String, Long>> getSortedByVolume(Date startDate, Date endDate)  
        {  
            DLLComp<CompPair<String, Long>> dllcomp = new DLLCompImp <CompPair<String, Long>>();  
            DLLComp <String> companies_name = SHDA.getAllCompanyCodes();  
              
            if (! (startDate == null && endDate == null) && (! companies_name.empty()))  
            {  
                companies_name.findFirst();  
                for ( int i=0; i< companies_name.size() ; i++)  
                {  
                      
                   String CompanyName = companies_name.retrieve();  
                    DLL <DataPoint<StockData>> data = SHDA.getStockHistory(CompanyName).getTimeSeries().getDataPointsInRange(startDate, endDate);  
  
                    Long vol = new Long(0);  
                      
                    if (! data.empty())  
                    {  
                        data.findFirst();  
                        for (int j = 0 ; j < data.size() ; j++)  
                        {  
                            vol += data.retrieve().value.volume;  
                            data.findNext();  
                        }  
                    }  
  
                    CompPair<String, Long> val = new CompPair<String, Long>(CompanyName, vol);  
                    dllcomp.insert(val);  
   
                    companies_name.findNext();  
                }  
                dllcomp.sort(true);  
           }  
            return dllcomp;  
        }  
  
        // Returns the list of company codes sorted by the maximum single day price  
        // increase between startDate and endDate inclusive. If startDate is null,  
        // fetches from the earliest date. If endDate is null, fetches to the latest  
        // date.  
        public DLLComp<CompPair<Pair<String, Date>, Double>> getSortedByMSDPI(Date startDate, Date endDate)  
        {  
            DLLComp<CompPair<Pair<String, Date>, Double>> dllcomp = new DLLCompImp<CompPair<Pair<String, Date>, Double>>();  
            DLLComp <String> companies_name = SHDA.getAllCompanyCodes();  
              
            if (! (startDate == null && endDate == null) && (! companies_name.empty()))  
            {  
                companies_name.findFirst();  
                for ( int i=0; i< companies_name.size() ; i++)  
                {  
                      
                   String CompanyName = companies_name.retrieve();  
                   Pair<String, Date> company_data = null;  
                   Double maxSPDI = new Double (0);  
                   Date maxDay = endDate;  
                     
                   DLL <DataPoint<StockData>> data = SHDA.getStockHistory(CompanyName).getTimeSeries().getDataPointsInRange(startDate, endDate);  
  
                    if (! data.empty())   
                    {  
                        DLLComp <CompPair<Date,Double>> allDays = new DLLCompImp <CompPair<Date,Double>> ();  
                        data.findFirst();  
                        while (!data.last())      
                        {  
                            double SDPI = data.retrieve().value.close - data.retrieve().value.open;  
                            CompPair<Date,Double> val = new CompPair<Date,Double>(data.retrieve().date, SDPI);  
                            allDays.insert(val);  
                            data.findNext();  
                        }  
                        allDays.sort(true);  
                        maxDay = allDays.getMax().first;  
                        maxSPDI = new Double (allDays.getMax().second);  
                    }  
  
                    company_data = new Pair<String, Date>(CompanyName, maxDay);  
                    CompPair<Pair<String, Date>, Double> val = new CompPair<Pair<String, Date>, Double>(company_data, maxSPDI);  
                    dllcomp.insert(val);  
   
                    companies_name.findNext();  
                }  
                dllcomp.sort(true);  
           }  
            return dllcomp;  
        }  
/*----------------------------------------------------------------------------- 
  should be removed 
*/  
      
    public static void main(String[] args) {  
      
        // TODO code application logic here  
          
        StockDataLoader SDL = new StockDataLoaderImp();  
        //write you path .......  
        String directoryPath = "C:\\Users\\Majdi\\Downloads\\Upload-To-Students\\Upload-To-Students\\Resources\\data\\examples" ;  
        StockHistoryDataSet AllCompanies = SDL.loadStockDataDir(directoryPath);  
          
        System.out.println("************* getAllCompanyCodes");  
        DLLComp <String> companies_name = AllCompanies.getAllCompanyCodes();  
        if (! companies_name.empty())  
        {  
              
            companies_name.findFirst();  
            for ( int i=0; i< companies_name.size() ; i++)  
            {  
                System.out.println(companies_name.retrieve());  
          
                System.out.println("************* getStockHistory");  
                StockHistory company = AllCompanies.getStockHistory(companies_name.retrieve());  
                System.out.println("Company name " + company.getCompanyCode());  
                DLL <DataPoint<StockData>> data = company.getTimeSeries().getAllDataPoints();  
                if (! data.empty())  
                {  
                    data.findFirst();  
                    for ( int j=0; j< data.size() ; j++)  
                    {  
                        System.out.println(data.retrieve());  
                        data.findNext();  
                    }  
                }  
                else  
                    System.out.println("No Company Available");  
  
                companies_name.findNext();  
            }  
       }  
        else  
            System.out.println("No Company Available");  
          
          
          
        ////////////////////////////////////  
        StockDataSetAnalyzer Analyzer = new StockDataSetAnalyzerImp();  
        Analyzer.setStockHistoryDataSet(AllCompanies);  
          
        DLLComp<CompPair<String, Double>> per = Analyzer.getSortedByPerformance(new Date("1/2/2024"), new Date("1/4/2024"));  
        if (! per.empty())  
        {  
            per.findFirst();  
            for ( int i = 0 ; i < per.size() ;i++)  
            {  
                System.out.println(per.retrieve());  
                per.findNext();  
            }  
        }  
        else  
            System.out.println("No Data Avialable");  
          
        DLLComp<CompPair<String, Long>>  vol = Analyzer.getSortedByVolume(new Date("1/2/2024"), new Date("1/4/2024"));  
        if (! vol.empty())  
        {  
            vol.findFirst();  
            for ( int i = 0 ; i < vol.size() ;i++)  
            {  
                System.out.println(vol.retrieve());  
                vol.findNext();  
            }  
        }  
        else  
            System.out.println("No Data Avialable");  
          
          
        DLLComp<CompPair<Pair<String, Date>, Double>> SDPI = Analyzer.getSortedByMSDPI(new Date("1/2/2024"), new Date("1/4/2024"));  
        if (! SDPI.empty())  
        {  
            SDPI.findFirst();  
            for ( int i = 0 ; i < SDPI.size() ;i++)  
            {  
                System.out.println(SDPI.retrieve());  
                SDPI.findNext();  
            }  
        }  
        else  
            System.out.println("No Data Avialable");  
    }  
}  