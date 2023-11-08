package com.itismeucci.stefanelli.server;

public class AppServer {
    public static void main( String[] args )
    {   
        
        Server.setPort(42069);
        Server.openServer();

        for(;;)
            Server.accept();
    }
}
