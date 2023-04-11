package za.co.ChatApp;
import java.io.*;
import java.util.*;
import java.net.*;

public class ClientHandler implements Runnable{
    private Socket s;
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private BufferedReader br;
    private BufferedWriter bw;

    private String clientUserName;
    
    public ClientHandler(Socket s) {
        try {
            this.s = s;
            this.bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            this.clientUserName = this.br.readLine().trim();
            clientHandlers.add(this);
            broadcastMessage("SERVER: "+this.clientUserName+" has entered the chat.");
        } catch (Exception e) {
            closeEverything(s, br, bw);
        }
    }

    private void closeEverything(Socket s2, BufferedReader br2, BufferedWriter bw2) {
    }
    public void broadcastMessage(String message) {
        for (ClientHandler c : clientHandlers) {
            try {
                if (!c.clientUserName.equals(this.clientUserName)) {
                    c.bw.write(message);
                    c.bw.newLine();
                    c.bw.flush();
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (s.isConnected()) {
            try {
                messageFromClient = br.readLine();
                broadcastMessage(messageFromClient);
            } catch (Exception e) {
                closeEverything(s, br, bw);
                break;
            }
        }
    }
}
