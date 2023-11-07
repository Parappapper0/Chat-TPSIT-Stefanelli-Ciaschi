package com.itismeucci.stefanelli;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    
    protected Socket connectionSocket;
    protected BufferedReader input;
    protected DataOutputStream output;

    public void start(String ip, int port) {

        try {

            connectionSocket = new Socket(ip, port);

            input = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            output = new DataOutputStream(connectionSocket.getOutputStream());

        } catch (Exception e) {
            
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void send(String message) {

        try {

            output.writeBytes(message);
            
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    public String receive() {
        
        try {

            return input.readLine();

        } catch (IOException e) {
            
            e.printStackTrace();
            return "ERROR";
        }
    }

    public void close() {

        try {
            
            connectionSocket.close();

        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }
}