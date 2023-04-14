import java.io.*;
import java.net.*;
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4242);
        serverSocket.setReuseAddress(true);
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
                System.out.println("Client connected");
                BufferedReader reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(serverSocket.getOutputStream());
                String message = reader.readLine();
                System.out.printf("Received from client:\n%s\n", message);

                String response = message.replaceAll(" ", ",");
                writer.write(response);
                writer.println();
                writer.flush();
                System.out.printf("Sent to client: \n%s\n", response);

                writer.close();
                reader.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}