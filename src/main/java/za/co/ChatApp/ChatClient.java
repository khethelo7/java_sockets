package za.co.ChatApp;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private Socket socket;

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private Scanner scanner;
    private String userName;

    public ChatClient(String ipAddress) {
        try {
            this.socket = new Socket(ipAddress, 9806);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            this.scanner = new Scanner(System.in);
            System.out.println("Enter preffered user name: ");
            this.userName = scanner.nextLine().trim();
            this.bufferedWriter.write(userName);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("Cannot initialize client!");
        }
    }

    public void sendMessage() {
        try {
            while (socket.isConnected()) {
                System.out.print("Enter a message: ");
                String message = scanner.nextLine().trim();
                this.bufferedWriter.write(message);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
            }
        } catch (Exception e) {
            System.out.println("Server disconnected");
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromServer;
                while (socket.isConnected()) {
                    try {
                        messageFromServer = bufferedReader.readLine();

                        if (messageFromServer == null) {
                            closeEverything(socket, bufferedReader, bufferedWriter);
                            System.exit(1);
                            break;
                        } else {
                            System.out.println(messageFromServer);
                        }

                    } catch (Exception e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

    }

    public static void main(String[] args) {
        String ipAddress = "localhost";
        ChatClient client = new ChatClient(ipAddress);
        client.sendMessage();
    }
}