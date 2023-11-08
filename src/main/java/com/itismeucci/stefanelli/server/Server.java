package com.itismeucci.stefanelli.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class Server {

     static private int port;
     static private ServerSocket serverSocket;
     static private HashMap<String, ClientConnectionThread> clientConnessi = new HashMap<String, ClientConnectionThread>();

     public static int getPort() {

          return port;
     }

     public static void setPort(int port) {
          Server.port = port;
     }

     public static void openServer() {

          try {

               serverSocket = new ServerSocket(port);

          } catch (IOException e) {

               System.out.println("Errore durante l'avvio del ServerSocket");
               e.printStackTrace();
          }
     }

     public static void close() {

          for (ClientConnectionThread clientThread : clientConnessi.values()) {

               clientThread.close();
          }
          
          try {

               serverSocket.close();

          } catch (IOException e) {

               System.out.println("Errore durante la chiusura del ServerSocket");
               e.printStackTrace();
          }
     }

     public static void accept() {

          ClientConnectionThread t;

          try {

               t = new ClientConnectionThread(serverSocket.accept());

          } catch (IOException e) {

               System.out.println("Errore durante la creazione del ClientThread");
               e.printStackTrace();
               return;
          }

          t.start();
     }

     public static boolean disconnect(String client) {

          try {
           
               clientConnessi.get(client).close();

          } catch (NullPointerException e) {
               
               System.out.println("Errore: client non presente");
               e.printStackTrace();
               return false;
          }
          clientConnessi.remove(client);
          return true;
     }

     public static int getClientAmount() {

          return clientConnessi.size();
     }

     public static HashMap<String, ClientConnectionThread> getClientList() {

          return new HashMap<>(clientConnessi);
     }

     public static ClientConnectionThread getClient(String username) {

          return clientConnessi.get(username);
     }

     public static void addClient(String username, ClientConnectionThread clientThread) {
          clientConnessi.put(username, clientThread);
     }
}