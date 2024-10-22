package dev.lpa;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
                System.out.println("[client] " + echoString);
                if(echoString.equals("exit")) break;
                //printed on the client side
                //output.println("[server] String echoed from server to client(response): " + echoString);
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter a string to be sent to client(response): ");
                output.println("[server] "+ scanner.nextLine());
                System.out.println("Please wait for the client's request");
            }
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }

    }
}
