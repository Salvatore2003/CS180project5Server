import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

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

        @Override
        public void run() {
            try {
                boolean runThread = true;

                String action;
                BufferedReader reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(serverSocket.getOutputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(serverSocket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(serverSocket.getInputStream());
                do {
                    System.out.println("back to top");
                    action = reader.readLine();
                    System.out.println(action);
                    if (action.contains("New User")) {
                        synchronized (obj) {
                            try {
                                User newUser = makeAccount(reader);

                                if (newUser != null) {
                                    users.add(newUser);
                                    System.out.println("account good");
                                    writer.write("account created");
                                    writer.println();
                                    writer.flush();
                                    ServerFileManager.storeUserData(users);
                                } else {
                                    System.out.println("error 2");
                                    writer.write("error occur");
                                    writer.println();
                                    writer.flush();
                                }
                            } catch (UsernameExistError e) {
                                System.out.println("executing error");
                                writer.write("username exist error");
                                writer.println();
                                writer.flush();
                            } catch (Exception e) {
                                System.out.println("error 3");
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
    public static User makeAccount(BufferedReader reader) throws UsernameExistError{
        try {
            String line;
            String userName;
            String password;
            String email;
            boolean isBuyer;
            boolean isSeller;
            line = reader.readLine();
            userName = line.substring(0, line.indexOf(" "));
            if (!checkForExistingUsername(userName)) {
                System.out.println("executing this error");
                throw new UsernameExistError("The username already exist.");
            }
            line = line.substring(line.indexOf(" ") + 1);
            password = line.substring(0, line.indexOf(" "));
            line = line.substring(line.indexOf(" ") + 1);
            email = line.substring(0, line.indexOf(" "));
            System.out.println(line);
            line = line.substring(line.indexOf(" ") + 1);
            isBuyer = Boolean.parseBoolean(line.substring(0, line.indexOf(" ")));
            System.out.println(line);
            line = line.substring(line.indexOf(" ") + 1);
            System.out.println(line);
            isSeller = Boolean.parseBoolean(line);
            System.out.println("returning");
            return new User(userName, password, email, isBuyer, isSeller);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
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
    public static void logInAttempt(BufferedReader bfr, PrintWriter writer, ObjectOutputStream objectOutputStream) {
        String userNameAttempt;
        String passwordAttempt;
        int indexOfUser = 0;
        boolean userNameExist = false;
        boolean logInSuccess = false;
        System.out.println("logging");

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
        System.out.println("end of log in");

    }
    public static void checkNewUsername(BufferedReader bfr, PrintWriter writer) {
        try {
            String newUsername = bfr.readLine();
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
    public static void changeUserInfo(BufferedReader bfr, PrintWriter writer, ObjectInputStream objectInputStream) {
        String infoChange = "";
        User user;
        int indexOfUser = -1;
        String newUserInfo;
        try {
            infoChange = bfr.readLine();
            System.out.println("Execute 2");
            user = (User) objectInputStream.readObject();
            System.out.println("Execute 3");
            newUserInfo = bfr.readLine();
            System.out.println("passed in username " + user.getUserName());

            for (int i = 0; i < users.size(); i++) {
                System.out.println(users.get(i).getUserName());
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