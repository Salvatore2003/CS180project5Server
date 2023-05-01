/**
 * Customer
 *
 * This class defines the object of a customer with all its attributes using fields.
 * Class used mainly in the customerGUI to track purchases and save an arrayList as a file.
 *
 * @author Dhruv Wadhwa, Lab 25
 *
 * @version 5/1/2023
 *
 */

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
