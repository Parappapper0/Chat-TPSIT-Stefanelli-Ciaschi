package com.itismeucci.stefanelli.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnectionThread extends Thread {
    
    private String username;
    private Socket clientSocket;
    private BufferedReader input;
    private DataOutputStream output;


    private boolean disconnected = false;

    public ClientConnectionThread(Socket clientSocket) {

        
        
        this.clientSocket = clientSocket;

        try {

            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new DataOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            
            System.out.println("Errore durante la creazione delle stream I/O");
            e.printStackTrace();
        }
    }

    public void close() {

        try {

            clientSocket.close();
            

        } catch (IOException e) {
            
            System.out.println("Errore durante la chiusura del Socket Client");
            e.printStackTrace();
        }
    }

    //send inoltra il messaggio ad un altro ClientThread (invia ad un altro client da lato server)
    public void send(String message) {
        
            switch (message.charAt(0)) {

                case '0': //0msg\0  -->  0usr-msg\0
                    message = "0" + username + "-" + message.substring(1);
                    for(String currentUsername : Server.getClientList().keySet())
                        if (currentUsername != username)
                            Server.getClient(currentUsername).write(message);
                    System.out.println("invio messaggio bc da " + username);
                    break;
                case '1':  //1trgt-msg\0  -->  1user-msg\0
                    String target = message.split("-")[0].substring(1);
                    message = "1" + username + "-" + message.substring(message.indexOf("-") + 1);
                    Server.getClient(target).write(message);
                    System.out.println("invio messaggio privato da " + username);
                    break;
                case '2':
                    message = "1Server-Utenti: (" + Server.getClientAmount() + ")";
                    for(String currentUsername : Server.getClientList().keySet())
                        message.concat("\n" + currentUsername);
                    this.write(message);
                    System.out.println("invio lista a " + username);
                    break;
                case '3':
                    Server.disconnect(username);
                    close();
                    disconnected = true;
                    System.out.println("disconnessione" + username);
                    break;
                default:
                    System.out.println("Errore: codice del messaggio non corretto");
                    break;
            }
    }

    //leggi da socket
    public String read() {

        ArrayList<Character> inputMessage = new ArrayList<>(25); //inizialmente 25, poi può aumentare

        try {
             
            do {

                inputMessage.add(Character.valueOf((char)input.read()));

            } while(inputMessage.get(inputMessage.size() - 1).charValue() != '\0');

        } catch (IOException e) {
            
            e.printStackTrace();
            System.out.println("Errore durante la lettura di un messaggio (" + username + ")");
            return null;
        }

        return (String)inputMessage.stream().map(e->e.toString()).reduce((acc, e) -> acc  + e).get(); //arabo
    }

    //riceve il messaggio inoltrato da un altro ClientThread e lo invia tramite il socket al proprio Client
    //scrivi su socket
    public void write(String message) {

        try {

            output.writeBytes(message);

        } catch (IOException e) {

            System.out.println("Errore durante la scrittura di un messaggio (" + username + ")");
            e.printStackTrace();
        }
    }

    //controlla se il nome è disponibile, se lo è aggiunge alla lista (l'username è la variabile di classe, va settata prima)
    private boolean login() {

        if (Server.getClient(username) == null) {

            Server.addClient(username, this);
            return true;
        }
        write("-\0");
        return false;
    }

    @Override
    public void run() {

        do{
            username = read();
            username = username.substring(0, username.length() - 1);
        }while(!login());

        write(username + "\0");
        System.out.println("Username inserito: " + username);

        while(!disconnected) { 
            send(read());
        }
    }
}
