public class Customer {
    String customerName;
    String agencyName;
    String tutorName;
    int hoursBooked;
    double hourlyRate;

    public Customer(String customerName, String agencyName, String tutorName, int hoursBooked, Double hourlyRate){
        this.customerName = customerName;
        this.agencyName = agencyName;
        this.tutorName = tutorName;
        this.hoursBooked = hoursBooked;
        this.hourlyRate = hourlyRate;
    }
    public String getCustomerName(){
        return customerName;
    }

    public String getAgencyName(){
        return agencyName;
    }

    public String getTutorName(){
        return tutorName;
    }

    public int getHoursBooked(){
        return hoursBooked;
    }

    public Double getHourlyRate(){
        return hourlyRate;
    }

}
