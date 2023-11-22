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

        return (String)inputMessage.stream().map(e->e.toString()).reduce((acc, e) -> acc  + e).get(); //ArrayList<Character> --> String
    }

    @Override
    public void run() {

        while (true) {

            String input = receive();
            String user = input.split("-")[0].substring(1);
            /*
             * 0username-msg 
             *  [username] >> msg
             * 1username-msg
             *  {username} -> {Tu} >> msg
             */
            switch(input.charAt(0)){
                case '0':
                    System.out.println("[" + user + "] >> " + input.substring(input.indexOf("-") + 1));
                    break;

                case '1':
                    System.out.println("{" + user + "} -> {Tu} >> " + input.substring(input.indexOf("-") + 1));
                    break;

                default:
                    break;
            }
        }
    }
}