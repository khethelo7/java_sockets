package za.co.ChatApp;
import java.io.*;
import java.util.*;
import java.net.*;

public class ClientHandler implements Runnable{
    private Socket s;
    public static ArrayList<ClientHandler> clients = new ArrayList<>();

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String clientUserName;
    
    public ClientHandler(Socket s) {
        try {
            this.s = s;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            this.clientUserName = bufferedReader.readLine();
            
            clients.add(this);
        } catch (IOException e) {
            closeEverything(s, bufferedReader, bufferedWriter);
        }
    }

    private void closeEverything(Socket s2, BufferedReader bufferedReader2, BufferedWriter bufferedWriter2) {
        removeClient(clientUserName);
        try {
            if (bufferedReader2 != null) {
                bufferedReader2.close();
            }
            if (bufferedWriter2 != null) {
                bufferedWriter2.close();
            }
            if (s != null) {
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        String messageFromClient;

        while (s.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();

                broadcastMessage(messageFromClient, clientUserName);

            } catch (IOException e) {
                closeEverything(s, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public static void broadcastMessage(String message, String userSending) {
        for (ClientHandler c : clients) {
            if (c.getUserName().equals(userSending)) {}
            try {
                c.bufferedWriter.write(message);
                c.bufferedWriter.newLine();
                c.bufferedWriter.flush();
            } catch (Exception e) {
                System.out.println("Could not broadcast message to: "+c.getUserName());
                e.printStackTrace();
            }
        }
    }

    public static void removeClient(String clientName) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getUserName().equals(clientName)) {
                clients.remove(i);
            }
        }
    }

    public String getUserName() {
        return this.clientUserName;
    }
}
