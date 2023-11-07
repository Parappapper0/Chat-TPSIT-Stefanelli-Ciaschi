package com.itismeucci.stefanelli;

import java.io.IOException;
import java.net.ServerSocket;

public class AppServer 
{
    public static void main( String[] args )
    {
        ServerSocket serverSocket;
        try {serverSocket = new ServerSocket(6789);} catch (IOException e) {e.printStackTrace(); return;}
        System.out.println("[SERVER] Server aperto su porta 6789");
        GuessThread.randomNumber = (int)Math.floor(Math.random() * 999);
        System.out.println("[SERVER] Il numero casuale è " + GuessThread.randomNumber);

        while(true) {

            Server server = new Server();
            server.accept(serverSocket);
            GuessThread thread = new GuessThread(server);
            thread.start();
        }
    }

    private static class GuessThread extends Thread {

        private Server server;
        protected static int randomNumber;
        protected String inputString;

        public GuessThread(Server server) {

            this.server = server;
        }

        @Override
        public void run() {

            server.send("Connessione con il server riuscita, sei il client #" + server.clientID + "\n");
            System.out.println("[SERVER] " + server.receive());

            while(true) {

                inputString = server.receive();
        
                System.out.println("[Client #" + server.clientID + "] Ricevuto: " + inputString);

    
                try {

                    if (Integer.parseInt(inputString) == randomNumber)
                    break;

                } catch (Exception e) {
                    
                    server.send("Non un numero\n");
                    continue;
                }
                
        
                server.send((Integer.parseInt(inputString) > randomNumber ? "Numero troppo grande" : "Numero troppo piccolo") + "\n");
            }

            server.send("Connessione terminata, numero indovinato\n");
            System.out.println("[SERVER] Connessione con il Client #" + server.clientID + " terminata");
            server.closeClient();
        }
    }
}
