package com.itismeucci.stefanelli.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static String username;
    public static Socket server;
    private static DataOutputStream output;

    public static boolean connect(String ip, int port) {

        try {

            server = new Socket(ip, port);

        } catch (UnknownHostException e) {

            System.out.println("Errore durante la connessione al server, host sconosciuto");
            return false;

        } catch (IOException e) {

            System.out.println("Errore durante la connessione al server");
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {

            System.out.println("Errore: la porta non è nel range consentito");
        }

        try {

            output = new DataOutputStream(server.getOutputStream());

        } catch (IOException e) {

            System.out.println("Errore durante l'inizializzazione del canale di Output del Client");
            e.printStackTrace();
        }

        return true;
    }

    public static boolean send(String user, String message) {

        message = "1" + user + "-" + message + "\0";

        try {

            output.writeBytes(message);

        } catch (IOException e) {
            
            System.out.println("Errore durante l'invio di un messaggio (" + username + ")");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void sendBC(String message) {

        message = "0" + message + "\0";

        try {

            output.writeBytes(message);

        } catch (IOException e) {
            
            System.out.println("Errore durante l'invio di un messaggio (" + username + ")");
            e.printStackTrace();
        }
    }

    public static void requestList() {

        try {

            output.writeBytes("2\0");

        } catch (IOException e) {
            
            System.out.println("Errore durante la richiesta della lista (" + username + ")");
            e.printStackTrace();
        }
    }

    public static void logout() {

        try {

            output.writeBytes("3\0");
            //ReceiveThread.close();

        } catch (IOException e) {
            
            System.out.println("Errore durante la richiesta della lista (" + username + ")");
            e.printStackTrace();
        }
    }

    public static void sendRaw(String message) {

        try {

            output.writeBytes(message);

        } catch (IOException e) {
            
            System.out.println("Errore durante l'invio di un messaggio (sendRaw)");
            e.printStackTrace();
        }
    }
}