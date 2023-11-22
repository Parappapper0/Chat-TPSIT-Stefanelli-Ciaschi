package com.itismeucci.stefanelli.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import com.itismeucci.stefanelli.Utilities;

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
            
            System.out.println(Utilities.red + "Errore durante la creazione delle stream I/O" + Utilities.reset);
            e.printStackTrace();
        }
    }

    public void close() {

        try {

            clientSocket.close();
            

        } catch (IOException e) {
            
            System.out.println(Utilities.red + "Errore durante la chiusura del Socket Client" + Utilities.reset);
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
                    System.out.println(Utilities.cyan + "invio messaggio bc da " + Utilities.yellow + username + Utilities.reset + ": " + message);
                    break;
                case '1':  //1trgt-msg\0  -->  1user-msg\0
                    String target = message.split("-")[0].substring(1);
                    message = "1" + username + "-" + message.substring(message.indexOf("-") + 1);
                    Server.getClient(target).write(message);
                    System.out.println(Utilities.cyan + "invio messaggio privato da " + Utilities.yellow + username + Utilities.reset + ": " + message);
                    break;
                case '2':
                    message = "1Server-Utenti: (" + Server.getClientAmount() + ")";
                    for(String currentUsername : Server.getClientList().keySet())
                        message = message.concat("\n\t\t" + currentUsername);
                    message += '\0';
                    this.write(message);
                    System.out.println(Utilities.cyan + "invio lista a "  + Utilities.yellow + username + Utilities.reset);
                    break;
                case '3':
                    Server.disconnect(username);
                    close();
                    disconnected = true;
                    System.out.println(Utilities.cyan + "disconnessione"  + Utilities.yellow + username + Utilities.reset);
                    break;
                default:
                    System.out.println(Utilities.red + "Errore: codice del messaggio non corretto" + Utilities.reset);
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
            System.out.println(Utilities.red + "Errore durante la lettura di un messaggio da"  + Utilities.yellow + username + Utilities.reset);
            return null;
        }

        return (String)inputMessage.stream().map(e->e.toString()).reduce((acc, e) -> acc  + e).get(); //ArrayList<Character> --> String
    }

    //riceve il messaggio inoltrato da un altro ClientThread e lo invia tramite il socket al proprio Client
    //scrivi su socket
    public void write(String message) {

        try {

            output.writeBytes(message);

        } catch (IOException e) {

            System.out.println(Utilities.red + "Errore durante la scrittura di un messaggio da"  + Utilities.yellow + username + Utilities.reset);
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
        System.out.println(Utilities.cyan+ "Username inserito: " + Utilities.yellow + username + Utilities.reset);

        while(!disconnected) { 
            send(read());
        }
    }
}
