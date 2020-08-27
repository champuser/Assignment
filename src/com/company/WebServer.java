package com.company;

import java.io.IOException;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;


public class WebServer {


    private static final int  LINGER_TIME=5000;
    private File logger = new File("www");
    private int portNo = 8080;

    private void Serve(){
        try {
            ServerSocket listeningSocket = new ServerSocket(portNo);                               // ServerSocket listening on portNo 8080
            while (true){


                Socket clientSocket = listeningSocket.accept();                                    //  connection request accept by the server until the connection will be established
                clientSocket.setSoLinger(true,LINGER_TIME);
                Thread handler = new Thread((Runnable) new RequestHandler(clientSocket,logger));   // Thread handler for handling the connection
                handler.setPriority(Thread.MAX_PRIORITY);
                handler.start();

            }

        }catch (IOException e){
            System.err.println("Server Failure");


        }
    }

    public static void main(String[] args) {

        WebServer server = new WebServer();                              // create server
        server.parseArguments(args);                                     // pass the arguments to server &
        server.Serve();                                                  // go to serve method






    }
    private void parseArguments(String[] arguments){
        if (arguments.length>0){

            System.out.println("server is running");
        }
    }
}
