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
        String textColor = Utilities.white;

        ReceiveThread t;

        // connettiti al server
        while (true) {

            System.out.print(Utilities.cyan + "\nInserisci l'ip del server: " + Utilities.reset);
            ip = scanner.nextLine();

            System.out.print(Utilities.cyan + "Inserisci la porta del server: " + Utilities.reset);
            try {
                port = scanner.nextInt();
            } catch (InputMismatchException e) {

                System.out.println(Utilities.red + "Errore: la porta non è stata scritta correttamente" + Utilities.reset);
                Utilities.emptyScanner(scanner);
                continue;
            }

            if (Client.connect(ip, port)) {

                System.out.println(Utilities.green + "Connessione effettuata con successo" + Utilities.reset);
                break;
            }
        }

        t = new ReceiveThread(Client.server);

        // fai il login
        Utilities.emptyScanner(scanner);

        while (true) {

            System.out.print(Utilities.cyan + "Inserisci l'username desiderato: " + Utilities.reset);

            //Utilities.emptyScanner(scanner);
            String s = scanner.nextLine();
            Client.sendRaw(s + "\0");

            Client.username = t.receive();

            if (!Client.username.equals("-\0"))
                break;
            System.out.println(Utilities.red + "Username non disponibile" + Utilities.reset);
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

                case "color":
                case "textcolor":
                    
                    break;
                
                case "help":
                case "?":
                default:
                     System.out.println(
                        Utilities.cyan + "Lista comandi:\n" +
                        Utilities.yellow + "\t[broadcast | broad | bc]" + Utilities.reset + " : invio di un messaggio pubblico\n" +
                        Utilities.yellow + "\t[private | pvt | priv | pv]" + Utilities.reset + " : invio di un messaggio ad un solo utente (privato)\n" +
                        Utilities.yellow + "\t[userlist | users | list | user | ul | online | active]" + Utilities.reset + " : richiesta della lista degli utenti online\n" +
                        Utilities.yellow + "\t[exit | close | stop | logout | ex]" + Utilities.reset + " : uscita dalla chat e dal programma\n" +
                        Utilities.yellow + "\t[help | ?]" + Utilities.reset + " : richiesta di questa lista di comandi");
                     break;

                case "": 
                    break;
            }
        }
    }
}
