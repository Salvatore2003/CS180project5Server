import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * runClient
 * <p>
 * Creates a new thread so another client can be run. This allows for their to be multiple clients
 *
 * @author Bryce LaMarca, Lab 25
 * @version 4/20/2023
 */
public class runClient {
    /**
     * connects to the server and takes the client to the LOG in GUI
     */
    public static void runClient() {
        try {
            Socket socket = new Socket("localhost", 4242); //the socket for the  server
            BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream())); //buffer reader
            PrintWriter writer = new PrintWriter(socket.getOutputStream()); //the writer to write to files
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream()); //object input stream
            //object output stream to output objects
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            LogInGUI.runLogInGUI(socket, bfr, writer, objectInputStream, objectOutputStream);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect", "Server connection",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
