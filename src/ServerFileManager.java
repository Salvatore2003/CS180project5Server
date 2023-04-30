import java.io.*;
import java.util.ArrayList;

public class ServerFileManager {
    public synchronized static void storeUserData(ArrayList<User> users) {
        try {
            FileWriter fw = new FileWriter("userInfo.txt"); //file writer to write the  file
            BufferedWriter bfw = new BufferedWriter(fw); //buffer reader to read the file
            for (User user : users) {
                bfw.write(user.getUserName() + " " + user.getPassword() + " " +
                        user.getUserEmail() + " " + user.isBuyer() + " " +
                        user.isSeller() + "\n");
            }
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized static ArrayList<User> recoverUsers() {
        String line; //the line that is read
        String userName; //users username to be stored
        String password; //users passwords to be stored
        String email; //users email to be stored
        boolean isBuyer; //checks if user is a buyer
        boolean isSeller; //checks if the user is a seller
        ArrayList<User> users = new ArrayList<>();
        try {
            FileReader fr = new FileReader("userInfo.txt");
            BufferedReader bfr = new BufferedReader(fr);
            line = bfr.readLine();
            while (line != null) {
                userName = line.substring(0, line.indexOf(" "));
                line = line.substring(line.indexOf(" ") + 1);
                password = line.substring(0, line.indexOf(" "));
                line = (line.substring(line.indexOf(" ") + 1));
                email = line.substring(0, line.indexOf(" "));
                line = (line.substring(line.indexOf(" ") + 1));
                isBuyer = Boolean.parseBoolean(line.substring(0, line.indexOf(" ")));
                line = line.substring(line.indexOf(" ") + 1);

                isSeller = Boolean.parseBoolean(line);
                users.add(new User(userName, password, email, isBuyer, isSeller));
                line = bfr.readLine();
            }
            return users;
        } catch (FileNotFoundException e) {
            System.out.println("No users found.");

        } catch (Exception e) {
            System.out.println("Error reading the user info file.");

        }
        return null;
    }
}
