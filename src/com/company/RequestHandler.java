package com.company;


import java.io.*;
import java.net.Socket;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final String NOT_IMPL_HTML = " <HTML> <HEAD> <TITLE> Not implemented </TITLE></HEAD> <BODY> <H1> HTTP Error 501: Not Implemented</H1></BODY></HTML>";
    private static final String SERVER_ID_HEADER = " Server: Http 1.0";
    private static final String HTTP_GET_METHOD ="Get";
    private static final String HTTP_OK_RESPONSE = " HTTP/1.0 200 OK";
    private static final String HTTP_NOT_IMPL_RESPONSE = " HTTP/1.0 501 Not Implemented";
    private static final String NOT_FOUND_RESPONSE = " HTTP/1.0 404 Not Found";
    private static final String NOT_FOUND_HTML = " <HTML> <HEAD> <TITLE> File Not Found </TITLE></HEAD> <BODY> <H1> HTTP Error 404: File Not Found</H1></BODY></HTML>";
    private final Socket clientSocket;
    private final File logger;


    RequestHandler(Socket clientSocket, File logger) {               // requesthandler handle the Connection of the thread using the file logger and clientSocket
        this.clientSocket = clientSocket;
        this.logger = logger;
    }

    public void run(){
        try {
            HttpRequest request = readRequest();                    // read the request
            if (request== null){
                return;}
            if (request.httpMethod.equals(HTTP_GET_METHOD)){        // request has http_get then it handle the request otherwise send error
                handleGetRequest(request);

            }else{
                sendErrorMessage(HTTP_NOT_IMPL_RESPONSE,NOT_IMPL_HTML,request.httpVersion);
            }

        }catch (IOException  ioe){
            ioe.printStackTrace();

        }finally {
            try {
                clientSocket.close();
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
    }

      private HttpRequest readRequest() throws IOException{
          BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));           // clientSocket sends the http request and InputStreamReader converts the byte API to character API Reader
          String requestLine = fromClient.readLine();

          if (requestLine== null){
              return null;
          }
          String[] requestTokens = requestLine.split(" ");
          HttpRequest request = new HttpRequest(requestTokens[0],requestTokens[1],requestTokens[2]);

             while ((requestLine = fromClient.readLine()) != null && !requestLine.trim().equals(" ")){

                  request.addHeader(requestLine);

             }
             return request;
      }

      private void handleGetRequest(HttpRequest request) throws IOException{
          OutputStream toClient = clientSocket.getOutputStream();                                                       // Output from the root node and wrapped in PrintWriter
          if (request.path.endsWith("/")){
              request.path = request.path + "index.html";
              try {

                  byte[] fileContent = readFile(removeIntialSlash(request.path));
                  if (request.httpVersion.startsWith("HTTP/")){
                      PrintWriter pw = new PrintWriter(toClient);
                      pw.println(HTTP_OK_RESPONSE);
                      pw.println("Date:" + LocalDateTime.now());
                      pw.println(SERVER_ID_HEADER);
                      pw.println("Content.length: " + fileContent.length);
                      pw.println("Content-type: " + getMimeFromExtension(request.path));
                      pw.println();
                      pw.flush();

                  }
                  toClient.write(fileContent);

              }
              catch (IOException ioe){
                  sendErrorMessage(NOT_FOUND_RESPONSE,NOT_FOUND_HTML,request.httpVersion);
                  ioe.printStackTrace();
              }
          }

      }
        private String removeIntialSlash(String source){
        return source.substring(1,source.length());

        }

        private byte[] readFile(String filePathRelativeToLogger) throws IOException {                                 // relative to file logger i.e www

        File file = new File(logger,filePathRelativeToLogger);

        try (FileInputStream fromFile = new FileInputStream(file)){
            byte[] buf = new byte[(int) file.length()];
            fromFile.read(buf);
            return buf;

        }

        }



        private String getMimeFromExtension(String name){                                                            // for file extension


        if (name.endsWith(".html") || name.endsWith(".htm")){
            return "text/html";}
        else if (name.endsWith(".txt")  || name.endsWith(".java")){
            return "text/plain";

        }else if (name.endsWith(".gif")){
            return "image/gif";

        } else if (name.endsWith(".class")){
            return "application/octet-stream";
        }
        else if (name.endsWith(".jpg") || name.endsWith(".jpeg")){
            return "image/jpeg";
        } else{
            return "text/plain";
        }

        }


        private void sendErrorMessage(String code, String html,String version) throws IOException{                          // for sending the error message

        PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());

         if (version.startsWith("HTTP/")){
             pw.println(code);
             pw.println("Date: " + (new Date()));
             pw.println(SERVER_ID_HEADER);
             pw.println("Content-type: text/html");
             pw.println();
         }
             pw.println(html);
             pw.flush();

        }


        private static class HttpRequest{



        private String httpMethod;
        private String path;
        private String httpVersion;

        private List<String> headers =  new ArrayList<>();



        private HttpRequest(String httpMethod,String path,String httpVersion){


            this.httpMethod = httpMethod;
            this.path = path;
            this.httpVersion  = httpVersion;
        }



           private void addHeader(String header){
            headers.add(header);
           }



        }

   }
