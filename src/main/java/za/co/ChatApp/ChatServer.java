package za.co.ChatApp;
import java.io.*;
import java.net.*;

public class ChatServer {

    private ServerSocket serverSocket;

    public ChatServer(ServerSocket ss) {
        this.serverSocket = ss;
    }

    public void startServer() {
        System.out.println("Chat server has started...\nListening on port no.:"+serverSocket.getLocalPort());
        try {
            while (!this.serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                System.out.println("A new client has connected");

                Runnable client = new ClientHandler(socket);
                Thread connection = new Thread(client);
                connection.start();
            }
        } catch (Exception e) {
            closeServerSocket();
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9806);
        ChatServer server = new ChatServer(ss);

        server.startServer();
    }

}
