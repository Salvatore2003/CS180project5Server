/**
 * InvalidTutor
 *
 * Exception if an invalid tutor name is entered throughout the code.
 *
 * @author Dhruv Wadhwa, Lab 25
 *
 * @version 5/1/2023
 *
 */
public class InvalidTutor extends Exception{
    public InvalidTutor(String message) {
        super(message);
    }
}