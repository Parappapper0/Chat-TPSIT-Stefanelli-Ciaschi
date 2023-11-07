package com.itismeucci.stefanelli;

import java.util.Scanner;

public class AppClient {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Client client = new Client();

        String ip;
        int port;

        String input = "";
        String serverInput;

        int clientID;

        System.out.println("Inserisci l'ip");
        ip = scanner.nextLine();
        System.out.println("Inserisci la porta");
        port = scanner.nextInt();

        client.start(ip, port);

        serverInput = client.receive();
        System.out.println(serverInput);

        clientID = Integer.parseInt(serverInput.split("#")[1]);

        client.send("Connessione riuscita con il Client #" + clientID + "\n");

        while(!serverInput.equals("Connessione terminata, numero indovinato")) {

            input = scanner.nextLine();

            if(input.isBlank()) continue;
        
            client.send(input + '\n');

            serverInput = client.receive();
            System.out.println(serverInput);
        }

        client.close();
        scanner.close();
    }
}
