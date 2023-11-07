package com.itismeucci.stefanelli.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class Server {
    
   private int port;
   private ServerSocket serverSocket;
   private HashMap<String, ClientThread> clientConnessi;

   public Server(int port) {

        this.port = port;

        try {

            serverSocket = new ServerSocket(port);

        } catch (IOException e) {
            
            System.out.println("Errore durante l'avvio del ServerSocket");
            e.printStackTrace();
        }

        clientConnessi = new HashMap<String, ClientThread>();
   }

   public int getPort() {

        return port;
   }

   //TODO
   private void close() {

   
   }

   //TODO
   private void accept() {


   }

   //TODO
   public boolean disconnect(String client) {

        return true;
   }

   public int getClientAmount() {

        return clientConnessi.size();
   }

   public HashMap<String, ClientThread> getClientList() {

        return (HashMap<String, ClientThread>) clientConnessi.clone();
   }

   public ClientThread getClient(String username) {

        return clientConnessi.get(username);
   }

}