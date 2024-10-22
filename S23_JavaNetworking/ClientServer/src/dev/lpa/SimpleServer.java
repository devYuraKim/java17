package dev.lpa;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    public static void main(String[] args){
        try(ServerSocket serverSocket = new ServerSocket(6000)){
            //wait for a client connection.
            //ServerSocket.accept() will return a Socket instance when a client connects
            try(Socket socket = serverSocket.accept()) {
                System.out.println("Client has connected to the server");
                //way for the SERVER to receive INPUT from the CLIENT
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //way for the SERVER to send OUTPUT to the CLIENT
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            while(true){
                String echoString = input.readLine();
                //printed on the server side
                System.out.println("[server] String from client to server(request): " + echoString);
                if(echoString.equals("exit")) break;
                //printed on the client side
                output.println("[server] String echoed from server to client(response): " + echoString);
            }
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }

    }
}
