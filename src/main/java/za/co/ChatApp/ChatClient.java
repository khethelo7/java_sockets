package za.co.ChatApp;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private Socket socket;
    private PrintStream out;
    private BufferedReader in;

    private Scanner scanner;
    private String userName;

    public ChatClient(String ipAddress) {
        try {
            this.socket = new Socket(ipAddress, 9806);
            this.out = new PrintStream(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.scanner = new Scanner(System.in);
            System.out.println("Enter preffered user name: ");
            this.userName = scanner.nextLine().trim();
            this.out.println(userName);
            this.out.flush();
        } catch (IOException e) {
            System.out.println("Cannot initialize client!");
        }
    }

    public void run() {
        try {
            while (socket.isConnected()) {
                System.out.print("Enter a message: ");
                String message = scanner.nextLine().trim();
                out.println(message);
                out.flush();
            }
        } catch (Exception e) {
            System.out.println("Server disconnected");
        }
    }

    public static void main(String[] args) {
        String ipAddress = "localhost";
        ChatClient client = new ChatClient(ipAddress);
        client.run();

    //     try {

    //         System.out.println("Client started");
    //         Socket soc = new Socket("localhost", 9806);

    //         BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
    //         System.out.print("Enter a number to get factorial: ");
    //         int number = Integer.parseInt(userInput.readLine());
    //         PrintWriter out = new PrintWriter(soc.getOutputStream(), true);
    //         out.println(number);

    //         BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
    //         System.out.println(in.readLine());

    //     }catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
    }
}