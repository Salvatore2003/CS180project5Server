import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class runClient {
    public static void runClient() {
        try {
            Socket socket = new Socket("localhost", 4242);
            BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            LogInGUI.runLogInGUI(socket, bfr, writer, objectInputStream, objectOutputStream);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect", "Server connection",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
