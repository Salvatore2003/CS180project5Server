import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Server
 * <p>
 * The server that stores the user info and allows the users  to edit their user info
 *
 * @author Bryce LaMarca, Lab 25
 * @version 4/30/2023
 */

public class Server {
    static ArrayList <User> users;
    final static Object obj = new Object();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4242);
        serverSocket.setReuseAddress(true);
        if (users == null || users.size() == 0){
            users = ServerFileManager.recoverUsers();
        }

        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler clientSock = new ClientHandler(socket);
            new Thread(clientSock).start();
        }
    }



    private static class ClientHandler implements Runnable {
        private final Socket serverSocket;

        private ClientHandler(Socket serverSocket) {

            this.serverSocket = serverSocket;
        }

        /**
         * makes a new thread to run multiple clients
         */
        @Override
        public void run() {
            try {
                boolean runThread = true;

                String action; //the action the user enters
                BufferedReader reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream())); //the
                //buffered reader
                PrintWriter writer = new PrintWriter(serverSocket.getOutputStream()); //the writer
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(serverSocket.getOutputStream()); // the
                //object output stream to output objects
                ObjectInputStream objectInputStream = new ObjectInputStream(serverSocket.getInputStream()); //receives
                //objects from the client
                do {
                    action = reader.readLine();
                    if (action.contains("New User")) {
                        synchronized (obj) {
                            try {
                                User newUser = makeAccount(reader);

                                if (newUser != null) {
                                    users.add(newUser);
                                    writer.write("account created");
                                    writer.println();
                                    writer.flush();
                                    ServerFileManager.storeUserData(users);
                                } else {
                                    writer.write("error occur");
                                    writer.println();
                                    writer.flush();
                                }
                            } catch (UsernameExistError e) {
                                writer.write("username exist error");
                                writer.println();
                                writer.flush();
                            } catch (Exception e) {
                                writer.write("error");
                                writer.println();
                                writer.flush();
                            }
                        }
                    }
                    if (action.contains("Logging in")) {
                        synchronized (obj) {
                            logInAttempt(reader, writer, objectOutputStream);
                        }
                    }
                    if (action.contains("check for existing usernames")) {
                        checkNewUsername(reader, writer);
                    }
                    if (action.contains("changing user info")) {
                        changeUserInfo(reader, writer, objectInputStream);
                    }
                } while (runThread);
                writer.close();
                reader.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * makes a new account for the user
     * @param reader the reader that reads the info
     * @return the user that is made
     * @throws UsernameExistError if the username already exist an error is thrown
     */
    public static User makeAccount(BufferedReader reader) throws UsernameExistError{
        try {
            String line; //the line that is read from the client
            String userName; //the username
            String password; //the password for the account
            String email; //the email for the account
            boolean isBuyer; //if the role is buyer
            boolean isSeller; //if the role is seller
            line = reader.readLine();
            userName = line.substring(0, line.indexOf(" "));
            if (!checkForExistingUsername(userName)) {
                throw new UsernameExistError("The username already exist.");
            }
            line = line.substring(line.indexOf(" ") + 1);
            password = line.substring(0, line.indexOf(" "));
            line = line.substring(line.indexOf(" ") + 1);
            email = line.substring(0, line.indexOf(" "));
            line = line.substring(line.indexOf(" ") + 1);
            isBuyer = Boolean.parseBoolean(line.substring(0, line.indexOf(" ")));
            line = line.substring(line.indexOf(" ") + 1);
            isSeller = Boolean.parseBoolean(line);
            return new User(userName, password, email, isBuyer, isSeller);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * checks if there is already an existing username
     * @param userName the potential username
     * @return true if it is a new username false if not
     */
    public static boolean checkForExistingUsername(String userName) {
        if (users!= null) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserName().equals(userName)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * the attempted log in that the user has
     * @param bfr the buffer reader that reads from the client
     * @param writer the writer that writes to the client
     * @param objectOutputStream the object output stream that the program uses
     */
    public static void logInAttempt(BufferedReader bfr, PrintWriter writer, ObjectOutputStream objectOutputStream) {
        String userNameAttempt; //the attempted username that is entered
        String passwordAttempt; //the attempted password that is entered
        int indexOfUser = 0; //the index that the user is located in users
        boolean userNameExist = false; //if the username exist
        boolean logInSuccess = false; //if the login is successful

        try {
            userNameAttempt = bfr.readLine();
            passwordAttempt = bfr.readLine();
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserName().equals(userNameAttempt)) {
                    indexOfUser = i;
                    userNameExist = true;
                    break;
                }
            }
            if (userNameExist) {
                if(users.get(indexOfUser).getPassword().equals(passwordAttempt)) {
                    logInSuccess = true;
                }
            }
            if (logInSuccess) {
                writer.write("log in success");
                writer.println();
                writer.flush();
                objectOutputStream.writeObject(users.get(indexOfUser));
                objectOutputStream.reset();
            } else {
                writer.write("log in fail");
                writer.println();
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * checks to see if the new username already exist
     * @param bfr reads from the client
     * @param writer writes to the client
     */
    public static void checkNewUsername(BufferedReader bfr, PrintWriter writer) {
        try {
            String newUsername = bfr.readLine(); //the new username that is entered
            if (checkForExistingUsername(newUsername)) {
                writer.write("valid new username");
            } else {
                writer.write("username exist");
            }
            writer.println();
            writer.flush();
        } catch (IOException e) {
            writer.write("error");
            writer.println();
            writer.flush();
            e.printStackTrace();
        }
    }

    /**
     * changes the userinfo
     * @param bfr reads from the client
     * @param writer writes to the client
     * @param objectInputStream reads object from the client
     */
    public static void changeUserInfo(BufferedReader bfr, PrintWriter writer, ObjectInputStream objectInputStream) {
        String infoChange = ""; //info being changed
        User user; //the user that is being changed
        int indexOfUser = -1; //the location that user is in array list users
        String newUserInfo; //the new info
        try {
            infoChange = bfr.readLine();
            user = (User) objectInputStream.readObject();
            newUserInfo = bfr.readLine();

            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserName().equals(user.getUserName())) {
                    indexOfUser = i;
                    break;
                }
            }
            if (infoChange.equals("username")) {
                users.get(indexOfUser).setUserName(newUserInfo);
            } else if (infoChange.equals("password")) {
                users.get(indexOfUser).setPassword(newUserInfo);
            } else if (infoChange.equals("email")) {
                users.get(indexOfUser).setUserEmail(newUserInfo);
            } else if (infoChange.equals("role")) {
                if (users.get(indexOfUser).isBuyer()) {
                    users.get(indexOfUser).setSeller(true);
                    users.get(indexOfUser).setBuyer(false);
                } else {
                    users.get(indexOfUser).setBuyer(true);
                    users.get(indexOfUser).setSeller(false);
                }
            }
            if (infoChange.equals("delete account")) {
                users.remove(indexOfUser);
            }
            ServerFileManager.storeUserData(users);
            users = ServerFileManager.recoverUsers();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}