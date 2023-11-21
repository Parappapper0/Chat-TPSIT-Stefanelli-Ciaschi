package com.itismeucci.stefanelli.client;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.itismeucci.stefanelli.Utilities;

public class AppClient {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input;
        String ip;
        int port;

        ReceiveThread t;

        // connettiti al server
        while (true) {

            System.out.print("\nInserisci l'ip del server: ");
            ip = scanner.nextLine();

            System.out.print("Inserisci la porta del server: ");
            try {
                port = scanner.nextInt();
            } catch (InputMismatchException e) {

                System.out.println("Errore: la porta non è stata scritta correttamente");
                Utilities.emptyScanner(scanner);
                continue;
            }

            if (Client.connect(ip, port)) {

                System.out.println("Connessione effettuata con successo");
                break;
            }
        }

        t = new ReceiveThread(Client.server);

        // fai il login
        Utilities.emptyScanner(scanner);

        while (true) {

            System.out.print("Inserisci l'username desiderato: ");

            //Utilities.emptyScanner(scanner);
            String s = scanner.nextLine();
            Client.sendRaw(s + "\0");

            Client.username = t.receive();

            if (!Client.username.equals("-\0"))
                break;
            System.out.println("Username non disponibile");
        }

        t.start();

        // inizia a poter scrivere comandi
        while (true) {
            input = scanner.nextLine();

            switch (input.split(" ")[0]) {
                case "bc":
                case "broadcast":
                case "broad":
                    Client.sendBC(input.substring(input.indexOf(" ") + 1));
                    break;
                case "private":
                case "pvt":
                case "pv":
                case "priv":
                    int pos = -1;
                    for (int i = 0; 1 < input.length(); i++)
                        if (input.toCharArray()[i] == ' ')
                            if (pos == -1)
                                pos = i;
                            else {
                                pos = i;
                                break;
                            }

                    Client.send(input.split(" ")[1], input.substring(pos + 1));
                    break;
                case "list":
                case "userlist":
                case "ul":
                case "users":
                case "user":
                case "online":
                case "active":
                    Client.requestList();
                    break;
                case "close":
                case "exit":
                case "ex":
                case "stop":
                case "logout":
                    Client.logout();
                    scanner.close();
                    t.interrupt();
                    return;
                default:
                     System.out.println("Lista kayword:\n\n  1- broadcast\n  2- private\n  3- userlist\n  4- close\n\nes: private ciao!\n(si possono usare forme contratte, come 'priv' o 'pv')");
                     break;
            }
        }
    }
}
