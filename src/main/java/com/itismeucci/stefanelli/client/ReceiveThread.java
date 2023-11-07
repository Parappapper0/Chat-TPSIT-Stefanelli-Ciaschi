package com.itismeucci.stefanelli.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveThread extends Thread {
    
    private BufferedReader input;
    private Socket server;
    
    public ReceiveThread(Socket server) {

        this.server = server;

        try {

            input = new BufferedReader(new InputStreamReader(server.getInputStream()));

        } catch (IOException e) {

            System.out.println("Errore durante l'apertura del canale di Input in ReceiveThread");
            e.printStackTrace();
        }
    }

    //TODO
    public void run() {
        
        
    }

    //TODO
    private String receive() {

        return "";  
    }
}