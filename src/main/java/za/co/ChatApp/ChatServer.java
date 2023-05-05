package za.co.ChatApp;
import java.io.*;
import java.net.*;

public class ChatServer {

    private ServerSocket ss;

    public ChatServer(ServerSocket ss) {
        this.ss = ss;
    }

    public void startServer() {
        try {
            while (!ss.isClosed()) {
                Socket socket = ss.accept();
                System.out.println("A new client has connected!");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    public void closeServerSocket() {
        try {
            if (ss != null) {
                ss.close();
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
