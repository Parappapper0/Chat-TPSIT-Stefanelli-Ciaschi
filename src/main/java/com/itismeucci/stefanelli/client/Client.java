package com.itismeucci.stefanelli.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.itismeucci.stefanelli.Utilities;

public class Client {

    public static String username;
    public static Socket server;
    private static DataOutputStream output;

    public static boolean connect(String ip, int port) {

        try {

            server = new Socket(ip, port);

        } catch (UnknownHostException e) {

            System.out.println(Utilities.red + "Errore durante la connessione al server, host sconosciuto" + Utilities.reset);
            return false;

        } catch (IOException e) {

            System.out.println(Utilities.red + "Errore durante la connessione al server" + Utilities.reset);
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {

            System.out.println(Utilities.red + "Errore: la porta non è nel range consentito" + Utilities.reset);
            return false;
        }

        try {

            output = new DataOutputStream(server.getOutputStream());

        } catch (IOException e) {

            System.out.println(Utilities.red + "Errore durante l'inizializzazione del canale di Output del Client" + Utilities.reset);
            e.printStackTrace();
        }

        return true;
    }

    public static boolean send(String user, String message) {

        message = "1" + user + "-" + message + "\0";

        try {

            output.writeBytes(message);

        } catch (IOException e) {
            
            System.out.println(Utilities.red + "Errore durante l'invio di un messaggio (" + username + ")" + Utilities.reset);
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
            
            System.out.println(Utilities.red + "Errore durante l'invio di un messaggio (" + username + ")" + Utilities.reset);
            e.printStackTrace();
        }
    }

    public static void requestList() {

        try {

            output.writeBytes("2\0");

        } catch (IOException e) {
            
            System.out.println(Utilities.red + "Errore durante la richiesta della lista (" + username + ")" + Utilities.reset);
            e.printStackTrace();
        }
    }

    public static void logout() {

        try {

            output.writeBytes("3\0");

        } catch (IOException e) {
            
            System.out.println(Utilities.red + "Errore durante la richiesta della lista (" + username + ")" + Utilities.reset);
            e.printStackTrace();
        }
    }

    public static void sendRaw(String message) {

        try {

            output.writeBytes(message);

        } catch (IOException e) {
            
            System.out.println(Utilities.red + "Errore durante l'invio di un messaggio (sendRaw)" + Utilities.reset);
            e.printStackTrace();
        }
    }

    
}