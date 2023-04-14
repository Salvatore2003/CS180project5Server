import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class runClient {
    public static void runClient() {
        try {
            Socket socket = new Socket("localhost", 4242);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            LogInGUI.runLogInGUI();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect", "Server connection",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
