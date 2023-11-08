package com.itismeucci.stefanelli.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientConnectionThread extends Thread {
    
    private String username;
    private Socket clientSocket;
    private BufferedReader input;
    private DataOutputStream output;
    private Server server;

    public ClientThread(Server server, Socket clientSocket) {

        this.server = server;
        this.clientSocket = clientSocket;

        try {

            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new DataOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            
            System.out.println("Errore durante la creazione delle stream I/O");
            e.printStackTrace();
        }
    }

    private void close() {

        try {

            clientSocket.close();

        } catch (IOException e) {
            
            System.out.println("Errore durante la chiusura del Socket Client");
            e.printStackTrace();
        }
    }

    //TODO
    public boolean send(String user, String message) {
        
        return true;
    }

    //TODO
    private String receive() {

        return "";
    }

    //TODO
    private void login() {


    }

    //TODO
    public void run() {

        
    }
}
