/**
 * SellerStatistics
 *
 * Class defines the object for seller statistics and its attributes as a field which is used in
 * the SellerStatisticsGUI.
 *
 * @author Rakshit Pradhan, Lab 25
 *
 * @version 5/1/2023
 *
 */
public class SellerStatistics {
    String agencyName;
    String customerName;
    int transactionCount;

    public SellerStatistics(String agencyName, String customerName,int transactionCount) {
        this.customerName =customerName;
        this.agencyName = agencyName;
        this.transactionCount = transactionCount;
    }

    public String getCustomerName(){
        return customerName;
    }

    public String getAgencyName(){
        return agencyName;
    }

    public int getTransactionCount(){
        return transactionCount;
    }
}
