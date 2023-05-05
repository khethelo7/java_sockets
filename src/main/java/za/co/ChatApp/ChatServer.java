package za.co.ChatApp;
import java.io.*;
import java.net.*;

public class ChatServer {

    private ServerSocket serverSocket;

    public ChatServer(ServerSocket ss) {
        this.serverSocket = ss;
    }

    public void startServer() {
        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Awaiting connections...\n");
                    while (!serverSocket.isClosed()) {
                        Socket socket = serverSocket.accept();
                        ClientHandler clientHandler = new ClientHandler(socket);

                        System.out.println(clientHandler.getUserName()+ " has joined the chat");
                        broadcastMessage(clientHandler.getUserName()+ " has joined the chat");
                        
                        Thread thread = new Thread(clientHandler);
                        thread.start();
                    }
                } catch (IOException e) {
                    System.out.println("Cannot start server!");
                }
            }
        }).start();
        
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

    public void broadcastMessage(String message) {
        for (ClientHandler c : ClientHandler.clientHandlers) {
            try {
                    c.out.println(message);
                    c.out.flush();
            } catch (Exception e) {
                System.out.println("Could not send to: "+c.getUserName());
            }
        }
    }
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9806);
        ChatServer server = new ChatServer(ss);

        server.startServer();
    }

}
