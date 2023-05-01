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
