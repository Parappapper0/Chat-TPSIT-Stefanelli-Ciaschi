package com.itismeucci.stefanelli.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ReceiveThread extends Thread {
    
    private BufferedReader input;
    
    public ReceiveThread(Socket server) {

        try {

            input = new BufferedReader(new InputStreamReader(server.getInputStream()));

        } catch (IOException e) {

            System.out.println("Errore durante l'apertura del canale di Input in ReceiveThread");
            e.printStackTrace();
        }
    }

    public String receive() {

        ArrayList<Character> inputMessage = new ArrayList<>(25); //inizialmente 25, poi può aumentare

        try {
             
            do {

                inputMessage.add(Character.valueOf((char)input.read()));

            } while(inputMessage.get(inputMessage.size() - 1).charValue() != '\0');

        } catch (IOException e) {
            
            e.printStackTrace();
            System.out.println("Errore durante la lettura di un messaggio");
            return null;
        }

        return String.valueOf(inputMessage.toArray());
    }

    @Override
    public void run() {
        
        
    }
}