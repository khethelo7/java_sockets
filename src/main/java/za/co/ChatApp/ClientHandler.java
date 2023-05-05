package za.co.ChatApp;
import java.io.*;
import java.util.*;
import java.net.*;

public class ClientHandler implements Runnable{
    private Socket s;
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public PrintStream out;
    public BufferedReader in;

    private String clientUserName;
    
    public ClientHandler(Socket s) {
        try {
            this.s = s;
            this.out = new PrintStream(s.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            this.clientUserName = this.in.readLine().trim();
            clientHandlers.add(this);
        } catch (Exception e) {
            closeEverything(s, out, in);
        }
    }

    private void closeEverything(Socket s2, PrintStream out, BufferedReader in) {
    }

    @Override
    public void run() {
        String messageFromClient;

        while (s.isConnected()) {
            try {
                messageFromClient = in.readLine();
            } catch (Exception e) {
                closeEverything(s, out, in);
                break;
            }
        }
    }

    public String getUserName() {
        return this.clientUserName;
    }
}
