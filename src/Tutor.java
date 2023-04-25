public class Tutor {
    String tutorName;
    String agencyName;
    String aboutMe;
    int hoursAvailable;
    double hourlyRate;
    int hoursPromised;
    //change

    /**
     * It is the object class for a tutor
     *
     * @version 2022-07-25
     * @author Purdue CS
     */
    public Tutor(String tutorName, String agencyName, String aboutMe, int hoursAvailable, double hourlyRate){
        this.tutorName = tutorName;
        this.agencyName = agencyName;
        this.aboutMe = aboutMe;
        this.hoursAvailable = hoursAvailable;
        this.hourlyRate = hourlyRate;
        hoursPromised = 0;
    }

    public Tutor(String tutorName, String agencyName, String aboutMe, int hoursAvailable, double hourlyRate, int hoursPromised){
        this.tutorName = tutorName;
        this.agencyName = agencyName;
        this.aboutMe = aboutMe;
        this.hoursAvailable = hoursAvailable;
        this.hourlyRate = hourlyRate;
        this.hoursPromised = hoursPromised;
    }

    public String getTutorName(){
        return tutorName;
    }

    public String getAgencyName(){
        return agencyName;
    }

    public String getAboutMe(){
        return aboutMe;
    }

    public int getHoursAvailable(){
        return hoursAvailable;
    }

    public double getHourlyRate(){
        return hourlyRate;
    }

    public int getHoursPromised(){
        return hoursPromised;
    }
}
