import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);
            Socket socket = new Socket("localhost", 4242);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            System.out.println("What do you want to send to the server");
            String response = scan.nextLine();
            writer.write(response);
            writer.println();
            writer.flush();
            System.out.printf("Sent to server:\n%s\n", response);
            String s1 = reader.readLine();
            System.out.printf("Received from server:\n%s\n", s1);
            writer.close();
            reader.close();
        } catch (UnknownHostException e) {
            System.out.println("failed connect");
        } catch (IOException e) {
            System.out.println("failed connection");
        }
    }
}
