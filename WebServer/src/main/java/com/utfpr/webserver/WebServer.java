/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.utfpr.webserver;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author guedes
 */
public final class WebServer {

    public static void main(String[] args) throws Exception{
        System.out.println("Server is running...");
        
        // Set the port number.
        int port = 6789;
        // Establish the listen socket
        ServerSocket socket = new ServerSocket(port);
        
        //Process HTTP service requests in an infinite loop.
        while(true){
            // Listen for a TCP connection request.
            Socket connection = socket.accept();
            
            // Construct an object to process the HTTP request message.
            HttpRequest request = new HttpRequest(connection);
            Thread thread = new Thread(request);
            thread.start();
        }
    }
}

final class HttpRequest implements Runnable
{
    final static String CRLF = "\r\n";
    Socket socket;
    
    // Constructor
    public HttpRequest(Socket socket) throws Exception
    {
        this.socket = socket;
    }
    
    // Implement the run() method of the Runnable interface.
    public void run()
    {
        try {
            processRequest();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void processRequest() throws Exception
    {
        InputStream is = socket.getInputStream();
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        // Get the request line of the HTTP request message.
        String requestLine = br.readLine();
        
        // Display the request line.
        System.out.println();
        System.out.println(requestLine);
        
        // Get and display the header lines.
        String headerLine = null;
        while ((headerLine = br.readLine()).length()!=0) {
            System.out.println(headerLine);
        }

        // Close streams and socket.
        os.close();
        br.close();
        socket.close();
    }
}