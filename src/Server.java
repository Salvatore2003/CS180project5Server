import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    BufferedReader reader;
    PrintWriter writer;
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
                System.out.println("Client connected");
                BufferedReader reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(serverSocket.getOutputStream());
                do {
                    action = reader.readLine();
                    if (action.equals("New User")) {
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
}