package za.co.ChatApp;
import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {

        try {

            System.out.println("Client started");
            Socket soc = new Socket("localhost", 9806);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter a number to get factorial: ");
            int number = Integer.parseInt(userInput.readLine());
            PrintWriter out = new PrintWriter(soc.getOutputStream(), true);
            out.println(number);

            BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            System.out.println(in.readLine());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}