package com.itismeucci.stefanelli.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private String username;
    private Socket server;
    private DataOutputStream output;
    private ReceiveThread receiveThread;

    public boolean connect(String ip, int port) {

        try {

            server = new Socket(ip, port);

        } catch (UnknownHostException e) {

            System.out.println("Errore durante la connessione al server, host sconosciuto");
            e.printStackTrace();
            return false;

        } catch (IOException e) {

            System.out.println("Errore durante la connessione al server");
            e.printStackTrace();
            return false;
        }

        try {

            output = new DataOutputStream(server.getOutputStream());

        } catch (IOException e) {

            System.out.println("Errore durante l'inizializzazione del canale di Output del Client");
            e.printStackTrace();
        }

        return true;
    }

    //TODO
    private boolean send(String user, String message) {

        return true;
    }

    //TODO
    private void sendBC(String message) {

        System.out.println();
        
    }
}