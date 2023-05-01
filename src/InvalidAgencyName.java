/**
 * InvalidAgencyName
 *
 * Exception if an invalid agency name is entered throughout the code.
 *
 * @author Dhruv Wadhwa, Lab 25
 *
 * @version 5/1/2023
 *
 */
public class InvalidAgencyName extends Exception{
    public InvalidAgencyName(String message) {
        super(message);
    }
}