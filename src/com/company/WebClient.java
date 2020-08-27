package com.company;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class WebClient {

    public static void main(String[] args) {                  //HTTP Client that opens on a web page & connected to the HTTP web server & it sends request to the server & server will response will print

        String httpServer = args[0];
        int serverPort = Integer.parseInt(args[1]);
        int timeoutMillis = 10000;                            // set time in milli second to respond the server i.e not to wait from the server
        int countLimitInLine = 10;                            // for read 10 lines from the file logger
        String httpRequest = " Get / HTTP/1.1";
        String hostHeader = "host:" + httpServer;


        try (Socket socket = new Socket(httpServer, serverPort)) {                   // create the socket to connect the server with serverPort
            socket.setSoTimeout(timeoutMillis);
            PrintWriter toServer = new PrintWriter(socket.getOutputStream());        // get the output stream from the server i. e on printwriter to the variable toServer
            toServer.println(httpRequest);
            toServer.println(hostHeader);
            toServer.println();
            toServer.flush();                                                       // data which we send not to store on internal buffer


            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));          // data send to the server .getInputStram on buffferdReader

            String str;

            while ((str = fromServer.readLine()) != null) {


                for (int i = 0; i <= countLimitInLine; i++) {                                         // show 10 line of the file


                    System.out.println(str);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();


        }
    }}
